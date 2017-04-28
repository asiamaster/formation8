package com.dili.formation8.service.impl;

import com.dili.formation8.dao.OrderMapper;
import com.dili.formation8.domain.Order;
import com.dili.formation8.service.OrderService;
import com.dili.utils.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 由MyBatis Generator工具自动生成
 * This file was generated on 2017-04-28 10:20:40.
 */
@Service
public class OrderServiceImpl extends BaseServiceImpl<Order, Long> implements OrderService {

    public OrderMapper getActualDao() {
        return (OrderMapper)getDao();
    }
}