package com.dili.formation8.service.impl;

import com.dili.formation8.dao.WithdrawalMapper;
import com.dili.formation8.domain.Withdrawal;
import com.dili.formation8.service.WithdrawalService;
import com.dili.utils.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 由MyBatis Generator工具自动生成
 * This file was generated on 2017-04-27 10:56:44.
 */
@Service
public class WithdrawalServiceImpl extends BaseServiceImpl<Withdrawal, Long> implements WithdrawalService {

    public WithdrawalMapper getActualDao() {
        return (WithdrawalMapper)getDao();
    }
}