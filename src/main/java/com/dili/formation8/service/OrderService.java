package com.dili.formation8.service;

import com.dili.formation8.domain.Order;
import com.dili.utils.base.BaseService;
import com.dili.utils.domain.BaseOutput;

import java.util.List;

/**
 * 由MyBatis Generator工具自动生成
 * This file was generated on 2017-04-28 21:56:36.
 */
public interface OrderService extends BaseService<Order, Long> {

    /**
     * 提交订单
     * @param order
     * @return
     */
    public BaseOutput<Order> submitOrder(Order order);

    /**
     * 查询已完结的订单
     * @return
     */
    public List<Order> selectEndOrder();

    /**
     * 更新已完结订单状态为2(众筹成功)
     * @return
     */
    public int updateEndingOrderStatus();
}