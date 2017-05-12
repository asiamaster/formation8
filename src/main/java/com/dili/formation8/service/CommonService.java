package com.dili.formation8.service;

/**
 * 通用服务
 * Created by asiam on 2017/5/12 0012.
 */
public interface CommonService {
    /**
     * 根据ID查询指定表下的指定属性
     * @param table 表名
     * @param id 主键
     * @param attr 属性名
     * @return
     */
    public Object getById(String table, Long id, String attr);
}
