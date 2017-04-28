package com.dili.formation8.service.impl;

import com.dili.formation8.dao.ProductMapper;
import com.dili.formation8.domain.Product;
import com.dili.formation8.service.ProductService;
import com.dili.utils.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 由MyBatis Generator工具自动生成
 * This file was generated on 2017-04-27 10:56:44.
 */
@Service
public class ProductServiceImpl extends BaseServiceImpl<Product, Long> implements ProductService {

    public ProductMapper getActualDao() {
        return (ProductMapper)getDao();
    }
}