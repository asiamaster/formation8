package com.dili.formation8.service.impl;

import com.dili.formation8.dao.DataDictionaryMapper;
import com.dili.formation8.domain.DataDictionary;
import com.dili.formation8.service.DataDictionaryService;
import com.dili.utils.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 由MyBatis Generator工具自动生成
 * This file was generated on 2017-04-27 10:56:44.
 */
@Service
public class DataDictionaryServiceImpl extends BaseServiceImpl<DataDictionary, Long> implements DataDictionaryService {

    public DataDictionaryMapper getActualDao() {
        return (DataDictionaryMapper)getDao();
    }
}