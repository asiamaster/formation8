package com.dili.formation8.service.impl;

import com.dili.formation8.dao.SkuMapper;
import com.dili.formation8.domain.Sku;
import com.dili.formation8.service.SkuService;
import com.dili.utils.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 由MyBatis Generator工具自动生成
 * This file was generated on 2017-04-27 10:56:44.
 */
@Service
public class SkuServiceImpl extends BaseServiceImpl<Sku, Long> implements SkuService {

    public SkuMapper getActualDao() {
        return (SkuMapper)getDao();
    }
}