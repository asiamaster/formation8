package com.dili.formation8.service.impl;

import com.dili.formation8.dao.OrderMapper;
import com.dili.formation8.domain.*;
import com.dili.formation8.service.*;
import com.dili.formation8.utils.DateUtils;
import com.dili.formation8.utils.Formation8Constants;
import com.dili.formation8.vo.UserVo;
import com.dili.utils.base.BaseServiceImpl;
import com.dili.utils.domain.BaseOutput;
import com.dili.utils.quartz.domain.ScheduleMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;

/**
 * 由MyBatis Generator工具自动生成
 * This file was generated on 2017-04-28 21:56:36.
 */
@Service
public class OrderServiceImpl extends BaseServiceImpl<Order, Long> implements OrderService {

    protected Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BizNumberService bizNumberService;
    @Autowired
    private SkuService skuService;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;
    @Autowired
    private SystemConfigService systemConfigService;
    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    public OrderMapper getActualDao() {
        return (OrderMapper)getDao();
    }

    /**
     * 下单流程描述:
     * 1. 根据skuId查询sku信息
     * 2. 判断sku是否达到sku总限额份数， 超过限额则不生成订单，没超过则增加sku总购买份数
     * 3. 比对SKU当前日期串是否和当前日期相等，不相等则更新当前日期串，并将当日购买份数清0
     * 4. 判断sku是否达到当日限额份数，超过限额则不生成订单,没超则增加当日购买份数
     * 5. 根据情况更新SKU的当前日期串、购买份数和当日购买份数
     * 6. 查询产品信息,判断产品是否已截止
     * 7. 更新产品的已筹金额
     * 8. 用户余额扣款
     * 9. 设置产品订单编号，设置订单状态为1:众筹中，设置订单产品id，设置下单时间，<br/>
     * 10. 数据库新增订单
     * 11.(烧伤机制)判断用户的引领人是否有投资，有的话根据木桶原则和系统配置表的引领费率，直接凭空增加对方的余额(引荐人在用户订单完成时才返款)
     * 12. 根据产品信息里的平台抽成比例，向平台帐户隐式转帐
     * 13.返回最新的BaseOutput订单信息
     * @param order
     * @return
     */
    @Transactional(propagation= Propagation.REQUIRED)
    @Override
    public BaseOutput<Order> submitOrder(Order order) {
        System.out.println(platformTransactionManager.getClass().getName());;

        BaseOutput<Order> output = null;
        try {
//        流程1-7
            output = preSubmitCheck(order);
            if (!output.isSuccess()) {
                return output;
            }
//        流程8:用户余额扣款
            Boolean result = userService.balanceAdjust(order.getUserId(), -order.getPrice());
            if(!result){
                return BaseOutput.failure("余额不足");
            }
//        流程9-10
            output.setData(saveOrder(order));
//        流程11:增加引领人余额
            referralBalanceAdjust(order);
//            if(true){
//                throw new RuntimeException("测试事务");
//            }
//        流程12:向平台用户转帐(凭空增加)
            transferToPlatform(order);
        }catch(Exception e){
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new RuntimeException(e.getMessage());
        }
        return output;
    }

    @Override
    public List<Order> selectEndOrder() {
        return getActualDao().selectEndOrder();
    }

    @Override
    public int updateEndingOrderStatus() {
        return getActualDao().updateEndingOrderStatus();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void orderComplete(ScheduleMessage scheduleMessage){
        try {
            //先找出结束的订单
            List<Order> endOrders = selectEndOrder();
            if(endOrders == null || endOrders.isEmpty()){
                log.debug( "[OrderScan]执行订单数：0条");
                return;
            }
            for(Order endOrder : endOrders){
                endOrder.setEndTime(new Date());
                endOrder.setStatus(Formation8Constants.OrderStatus.SUCCEED.getCode());
            }
            //再更新结束的订单状态为2,众筹成功，不一步更新的原因是需要查出订单做给引荐打款
            int updatedCount= batchUpdate(endOrders);
            //直接更新结束的订单状态为2,众筹成功
//            int updatedCount= orderService.updateEndingOrderStatus();
            //给引荐人打款
            for(Order endOrder : endOrders){
                referralBalanceAdjust(endOrder.getUserId(), endOrder.getPrice());
            }
            log.debug("[OrderScan]执行订单数：" + updatedCount + "条");
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
    }

    //============================  私有方法分割  ================================================

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
        List<Order> orders = list(orderCondition);
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

    //    向平台用户转帐
    private void transferToPlatform(Order order){
        //查询平台抽成比率
        Integer commissionRate = productService.get(order.getProductId()).getCommissionRate();
        userService.balanceAdjust(Formation8Constants.PLATFORM_USER_ID, order.getPrice() * commissionRate.longValue()/100);
    }

    /**
     * 增加引领人余额
     * @param order
     */
    private void referralBalanceAdjust(Order order){
        //查询引领人
        UserVo userVo = new UserVo();
        userVo.setLevel(1);
        userVo.setId(order.getUserId());
        Long referrerId = userService.getParentReferrer(userVo);
        if(referrerId == null || referrerId <=0) return;
        //查询引领人的所有正在投资中的订单
        Order orderCondition = new Order();
        orderCondition.setUserId(referrerId);
        orderCondition.setStatus(Formation8Constants.OrderStatus.PROGRESSING.getCode());
        List<Order> orders = getActualDao().select(orderCondition);
        //统计引领人的投资金额
        Long amount = 0l;
        for(Order o : orders){
            amount += o.getPrice();
        }
        //根据木桶原则获得可用于返款计算的金额
        amount = amount>order.getPrice() ? order.getPrice():amount;
        //如果引领人没投资，则直接返回了
        if(amount == 0l) return;
        SystemConfig systemConfigCondition = new SystemConfig();
        systemConfigCondition.setCode(Formation8Constants.SYSTEM_CONFIG_REFERRAL_RATE1);
        String referralRate1 = systemConfigService.list(systemConfigCondition).get(0).getValue();
        //计算出引领费
        Long referralPrice = amount*Long.parseLong(referralRate1)/100;
//        增加引领人的余额
        userService.balanceAdjust(referrerId, referralPrice);
    }

    /**
     * 保存订单
     * @param order
     * @return
     */
    private Order saveOrder(Order order){
        //        设置产品订单编号
        order.setOrderNumber(bizNumberService.getProductOrderCode());
//        设置订单状态为1:众筹中
        order.setStatus(Formation8Constants.OrderStatus.PROGRESSING.getCode());
        getActualDao().insert(order);
        return order;
    }

    /**
     * 订单提交前检查
     * @param order
     * @return
     */
    private BaseOutput preSubmitCheck(Order order){
        Assert.notNull(order, "入参Order为空");
        if(order.getSkuId() == null){
            return BaseOutput.failure("订单参数skuId为空");
        }
        if(order.getCount() == null){
            return BaseOutput.failure("订单参数count为空");
        }
        if(order.getPrice() == null){
            return BaseOutput.failure("订单参数price为空");
        }
        if(order.getUserId() == null){
            return BaseOutput.failure("订单参数userId为空");
        }
//        根据skuId查询sku信息
        Sku sku = skuService.get(order.getSkuId());
        Assert.notNull(sku,"订单SKU["+order.getSkuId()+"]不存在");
//        判断sku是否达到sku总限额份数， 超过限额则不生成订单，没超过则增加sku总购买份数
        if(sku.getQuantity()+order.getCount()>sku.getQuota()){
            return BaseOutput.failure("超出sku["+sku.getId()+"]购买总限额");
        }else{
            //设置SKU购买总量，一会儿判断完当时限额统一存储SKU
            sku.setQuantity(sku.getQuantity()+order.getCount());
        }
//        比对SKU当前日期串是否和当前日期相等，不相等则更新当前日期串，并将当日购买份数清0
        String currentDate = DateUtils.format(new Date(),"yyyy-MM-dd");
        if(sku.getCurrentDateStr()!= null && sku.getCurrentDateStr().equals(currentDate)){
            sku.setDailyQuantity(sku.getDailyQuantity()+order.getCount());
        }else{
            sku.setCurrentDateStr(currentDate);
            sku.setDailyQuantity(order.getCount());
        }
//        判断sku是否达到当日限额份数，超过限额则不生成订单,没超则增加当日购买份数
        if(sku.getDailyQuantity() > sku.getDailyQuota()){
            return BaseOutput.failure("订单sku["+sku.getId()+"]超出日限额"+(sku.getDailyQuantity()-sku.getDailyQuota())+"份");
        }
//        根据情况更新SKU的当前日期串、购买份数和当日购买份数
        skuService.update(sku);
//        查询产品信息,判断产品是否已截止
        Product product = productService.get(sku.getProductId());
        if(product.getCutoffTime().before(new Date())){
            return BaseOutput.failure("订单产品["+product.getId()+"]超过截止时间");
        }
//        更新产品的已筹金额
        product.setCurrentAmount(product.getCurrentAmount()+order.getPrice());
        productService.update(product);
        //设置订单产品id
        order.setProductId(sku.getProductId());
//        设置下单时间
        Long startTime = System.currentTimeMillis();
        order.setStartTime(new Date(startTime));
//        设置结束时间为:开始时间+产品周期(天)
        order.setEndTime(new Date(startTime+product.getPeriod()*86400000));
        return BaseOutput.success();
    }
}