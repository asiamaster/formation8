package com.dili.formation8.service;

import com.dili.formation8.domain.DataDictionaryValue;
import com.dili.formation8.vo.DataDictionaryValueCondition;
import com.dili.utils.base.BaseService;

import java.util.List;

/**
 * 由MyBatis Generator工具自动生成
 * This file was generated on 2017-04-27 10:56:44.
 */
public interface DataDictionaryValueService extends BaseService<DataDictionaryValue, Long> {

    /**
     * 根据数据字典编码查询编码值列表
     * @param code
     * @return
     */
    public List<DataDictionaryValue> selectByCondition(DataDictionaryValueCondition condition);
}