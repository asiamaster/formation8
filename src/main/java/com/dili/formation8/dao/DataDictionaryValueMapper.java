package com.dili.formation8.dao;

import com.dili.formation8.domain.DataDictionaryValue;
import com.dili.formation8.vo.DataDictionaryValueCondition;
import com.dili.utils.base.MyMapper;

import java.util.List;

public interface DataDictionaryValueMapper extends MyMapper<DataDictionaryValue> {

    public List<DataDictionaryValue> selectByCondition(DataDictionaryValueCondition condition);
}