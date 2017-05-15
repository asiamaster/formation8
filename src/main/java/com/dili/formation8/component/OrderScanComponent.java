package com.dili.formation8.component;

import com.beust.jcommander.internal.Lists;
import com.dili.formation8.dao.ScheduleJobMapper;
import com.dili.formation8.domain.Order;
import com.dili.formation8.domain.SystemConfig;
import com.dili.formation8.domain.User;
import com.dili.formation8.service.OrderService;
import com.dili.formation8.service.SystemConfigService;
import com.dili.formation8.service.UserService;
import com.dili.formation8.utils.Formation8Constants;
import com.dili.formation8.vo.UserVo;
import com.dili.utils.quartz.domain.ScheduleJob;
import com.dili.utils.quartz.domain.ScheduleMessage;
import com.dili.utils.quartz.service.JobTaskService;
import org.apache.commons.lang3.StringUtils;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.PostConstruct;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.Date;
import java.util.List;

/**
 * 订单后台扫描组件
 * Created by asiam on 2017/5/12 0012.
 */
@Component
public class OrderScanComponent implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger log = LoggerFactory.getLogger(OrderScanComponent.class);

    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;

    @Autowired
    private SystemConfigService systemConfigService;

    @Autowired
    private ScheduleJobMapper scheduleJobMapper;

    @Autowired
    private JobTaskService jobTaskService;

    /**
     * 运行PingServer机器地址
     */
    private String serverName;

    @PostConstruct
    public void init() {
        try {
            InetAddress inetAddress = Inet4Address.getLocalHost();
            if (StringUtils.isNotBlank(inetAddress.getHostAddress())) {
                serverName = "OrderScan[" + inetAddress.getHostAddress() + "]";
            }
        } catch (Exception e) {
            log.warn("[OrderScan]获取运行机器名失败，发生异常：" + e.getMessage());
            serverName = "";
        }
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (contextRefreshedEvent.getApplicationContext().getParent() == null) {
            List<ScheduleJob> scheduleJobs = scheduleJobMapper.selectAll();
            for(ScheduleJob job : scheduleJobs){
                try {
                    jobTaskService.addJob(job, true);
                } catch (SchedulerException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     *
     * 调度消息
     * @param scheduleMessage
     */
    //    @Scheduled(fixedRate = 5000)
    @Transactional(propagation = Propagation.REQUIRED)
    public void scan(ScheduleMessage scheduleMessage) {
        System.out.println("线程:"+Thread.currentThread().getName()+",当前调度Job:"+scheduleMessage.getJobGroup()+scheduleMessage.getJobName()+"运行第"+scheduleMessage.getSheduelTimes()+"次.");
        try {
            log.debug(serverName + "[OrderScan]扫描待执行消息...");
            //先找出结束的订单
            List<Order> endOrders = orderService.selectEndOrder();
            if(endOrders == null || endOrders.isEmpty()){
                log.debug(serverName + "[OrderScan]执行订单数：0条");
                return;
            }
            for(Order endOrder:endOrders){
                endOrder.setEndTime(new Date());
                endOrder.setStatus(Formation8Constants.OrderStatus.SUCCEED.getCode());
            }
            //再更新结束的订单状态为2,众筹成功，不一步更新的原因是需要查出订单做给引荐打款
            int updatedCount= orderService.batchUpdate(endOrders);
            //直接更新结束的订单状态为2,众筹成功
//            int updatedCount= orderService.updateEndingOrderStatus();
            //给引荐人打款
            for(Order endOrder : endOrders){
                referralBalanceAdjust(endOrder.getUserId(), endOrder.getPrice());
            }

            log.debug(serverName + "[OrderScan]执行订单数：" + updatedCount + "条");
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        } finally {
        }
    }

    /**
     * 根据推荐人id和订单金额增加引荐人余额
     * @param userId
     * @param price
     */
    private void referralBalanceAdjust(Long userId, Long price){
        //查询引荐人
        UserVo userVo = new UserVo();
        userVo.setLevel(2);
        userVo.setId(userId);
        Long referrerId = userService.getParentReferrer(userVo);
        if(referrerId == null || referrerId <=0) return;
        //查询引荐人的所有正在投资中的订单
        Order orderCondition = new Order();
        orderCondition.setUserId(referrerId);
        orderCondition.setStatus(Formation8Constants.OrderStatus.PROGRESSING.getCode());
        List<Order> orders = orderService.list(orderCondition);
        //统计引领人的投资金额
        Long amount = 0l;
        for(Order o : orders){
            amount += o.getPrice();
        }
        //根据木桶原则获得可用于返款计算的金额
        amount = amount>price ? price:amount;
        //如果引荐人没投资，则直接返回了
        if(amount == 0l) return;
        SystemConfig systemConfigCondition = new SystemConfig();
        systemConfigCondition.setCode(Formation8Constants.SYSTEM_CONFIG_REFERRAL_RATE2);
        String referralRate2 = systemConfigService.list(systemConfigCondition).get(0).getValue();
        //计算出引领费
        Long referralPrice = amount*Long.parseLong(referralRate2)/100;
//        增加引荐人的余额
        userService.balanceAdjust(referrerId, referralPrice);
    }
}
