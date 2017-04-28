package com.dili.formation8.service.impl;

import com.dili.formation8.dao.BankCardMapper;
import com.dili.formation8.domain.BankCard;
import com.dili.formation8.service.BankCardService;
import com.dili.utils.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 由MyBatis Generator工具自动生成
 * This file was generated on 2017-04-27 10:56:44.
 */
@Service
public class BankCardServiceImpl extends BaseServiceImpl<BankCard, Long> implements BankCardService {

    public BankCardMapper getActualDao() {
        return (BankCardMapper)getDao();
    }
}