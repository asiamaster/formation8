package com.dili.formation8.dao;

import com.dili.formation8.domain.Order;
import com.dili.utils.base.MyMapper;

import java.util.List;

public interface OrderMapper extends MyMapper<Order> {
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