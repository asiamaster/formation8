package com.dili.formation8.service.impl;

import com.dili.formation8.dao.FinancialTransactionMapper;
import com.dili.formation8.domain.FinancialTransaction;
import com.dili.formation8.service.FinancialTransactionService;
import com.dili.utils.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 由MyBatis Generator工具自动生成
 * This file was generated on 2017-04-27 10:56:44.
 */
@Service
public class FinancialTransactionServiceImpl extends BaseServiceImpl<FinancialTransaction, Long> implements FinancialTransactionService {

    public FinancialTransactionMapper getActualDao() {
        return (FinancialTransactionMapper)getDao();
    }
}