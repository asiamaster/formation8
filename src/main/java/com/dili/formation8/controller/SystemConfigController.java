package com.dili.formation8.controller;

import com.dili.formation8.domain.SystemConfig;
import com.dili.formation8.service.SystemConfigService;
import com.dili.utils.domain.BaseOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 由MyBatis Generator工具自动生成
 * This file was generated on 2017-04-27 10:56:44.
 */
@Controller
@RequestMapping("/systemConfig")
public class SystemConfigController {
    @Autowired
    SystemConfigService systemConfigService;

    @RequestMapping("/list")
    public String list(ModelMap modelMap, @ModelAttribute SystemConfig systemConfig) {
        modelMap.put("list", systemConfigService.list(systemConfig));
        return "systemConfig/list";
    }

    @RequestMapping("/insert")
    public String insert(ModelMap modelMap, @ModelAttribute SystemConfig systemConfig) {
        systemConfigService.insertSelective(systemConfig);
        return "systemConfig/insert";
    }

    @RequestMapping("/update")
    public String update(ModelMap modelMap, @ModelAttribute SystemConfig systemConfig) {
        systemConfigService.update(systemConfig);
        return "systemConfig/update";
    }

    @RequestMapping("/delete")
    public @ResponseBody
    BaseOutput delete(ModelMap modelMap, Long id) {
        systemConfigService.delete(id);
        return BaseOutput.success();
    }
}