package com.dili.formation8.service.impl;

import com.dili.formation8.dao.SystemConfigMapper;
import com.dili.formation8.domain.SystemConfig;
import com.dili.formation8.service.SystemConfigService;
import com.dili.utils.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 由MyBatis Generator工具自动生成
 * This file was generated on 2017-04-27 10:56:44.
 */
@Service
public class SystemConfigServiceImpl extends BaseServiceImpl<SystemConfig, Long> implements SystemConfigService {

    public SystemConfigMapper getActualDao() {
        return (SystemConfigMapper)getDao();
    }
}