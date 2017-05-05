package com.dili.formation8.service;

import com.dili.formation8.domain.Order;
import com.dili.utils.base.BaseService;
import com.dili.utils.domain.BaseOutput;

/**
 * 由MyBatis Generator工具自动生成
 * This file was generated on 2017-04-28 21:56:36.
 */
public interface OrderService extends BaseService<Order, Long> {

    public BaseOutput<Order> submitOrder(Order order);
}