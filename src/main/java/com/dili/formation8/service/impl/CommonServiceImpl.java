package com.dili.formation8.service.impl;

import com.dili.formation8.dao.CommonMapper;
import com.dili.formation8.service.CommonService;
import com.dili.utils.base.BaseServiceImpl;
import com.dili.utils.base.MyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by asiam on 2017/5/12 0012.
 */
@Service
public class CommonServiceImpl implements CommonService {

    protected static final Logger log = LoggerFactory.getLogger(CommonServiceImpl.class);
    @Autowired
    private CommonMapper mapper;

    @Override
    public Object getById(String table, Long id, String attr) {
        Map<String, Object> param = new HashMap<>();
        param.put("table", table);
        param.put("id", id);
        param.put("attr", attr);
        return mapper.getById(param);
    }
}
