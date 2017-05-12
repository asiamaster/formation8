package com.dili.formation8.component;

import com.dili.formation8.dao.ScheduleJobMapper;
import com.dili.formation8.service.OrderService;
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

import javax.annotation.PostConstruct;
import java.net.Inet4Address;
import java.net.InetAddress;
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
    public void scan(ScheduleMessage scheduleMessage) {
        System.out.println("线程:"+Thread.currentThread().getName()+",当前调度Job:"+scheduleMessage.getJobGroup()+scheduleMessage.getJobName()+"运行第"+scheduleMessage.getSheduelTimes()+"次.");
        try {
            log.debug(serverName + "[OrderScan]扫描待执行消息...");
            int updatedCount= orderService.updateEndingOrderStatus();
            log.debug(serverName + "[OrderScan]执行订单数：" + updatedCount + "条");
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        } finally {
        }
    }
}
