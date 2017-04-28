package com.dili.formation8.service.impl;

import com.dili.formation8.dao.DepositMapper;
import com.dili.formation8.domain.Deposit;
import com.dili.formation8.service.DepositService;
import com.dili.utils.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 由MyBatis Generator工具自动生成
 * This file was generated on 2017-04-28 21:44:28.
 */
@Service
public class DepositServiceImpl extends BaseServiceImpl<Deposit, Long> implements DepositService {

    public DepositMapper getActualDao() {
        return (DepositMapper)getDao();
    }
}