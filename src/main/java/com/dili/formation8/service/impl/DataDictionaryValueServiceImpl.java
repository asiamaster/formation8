package com.dili.formation8.service.impl;

import com.dili.formation8.dao.DataDictionaryValueMapper;
import com.dili.formation8.domain.DataDictionaryValue;
import com.dili.formation8.service.DataDictionaryValueService;
import com.dili.formation8.vo.DataDictionaryValueCondition;
import com.dili.utils.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 由MyBatis Generator工具自动生成
 * This file was generated on 2017-04-27 10:56:44.
 */
@Service
public class DataDictionaryValueServiceImpl extends BaseServiceImpl<DataDictionaryValue, Long> implements DataDictionaryValueService {

    public DataDictionaryValueMapper getActualDao() {
        return (DataDictionaryValueMapper)getDao();
    }

    @Override
    public List<DataDictionaryValue> selectByCondition(DataDictionaryValueCondition condition) {
        return getActualDao().selectByCondition(condition);
    }
}