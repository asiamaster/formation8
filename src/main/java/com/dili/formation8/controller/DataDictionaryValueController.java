package com.dili.formation8.controller;

import com.dili.formation8.domain.DataDictionaryValue;
import com.dili.formation8.service.DataDictionaryValueService;
import com.dili.formation8.vo.DataDictionaryValueCondition;
import com.dili.utils.domain.BaseOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 由MyBatis Generator工具自动生成
 * This file was generated on 2017-04-27 10:56:44.
 */
@Controller
@RequestMapping("/dataDictionaryValue")
public class DataDictionaryValueController {
    @Autowired
    DataDictionaryValueService dataDictionaryValueService;

    /**
     * 根据数据字典编码和数据字典值条件查询字典值列表
     * @param modelMap
     * @param condition
     * @return
     */
    @RequestMapping("/selectByCondition.aspx")
    public String selectByCondition(ModelMap modelMap, @ModelAttribute DataDictionaryValueCondition condition) {
        modelMap.put("list", dataDictionaryValueService.selectByCondition(condition));
        return "dataDictionaryValue/list";
    }


    @RequestMapping("/list")
    public String list(ModelMap modelMap, @ModelAttribute DataDictionaryValue dataDictionaryValue) {
        modelMap.put("list", dataDictionaryValueService.list(dataDictionaryValue));
        return "dataDictionaryValue/list";
    }

    @RequestMapping("/insert")
    public String insert(ModelMap modelMap, @ModelAttribute DataDictionaryValue dataDictionaryValue) {
        dataDictionaryValueService.insertSelective(dataDictionaryValue);
        return "dataDictionaryValue/insert";
    }

    @RequestMapping("/update")
    public String update(ModelMap modelMap, @ModelAttribute DataDictionaryValue dataDictionaryValue) {
        dataDictionaryValueService.update(dataDictionaryValue);
        return "dataDictionaryValue/update";
    }

    @RequestMapping("/delete")
    public @ResponseBody
    BaseOutput delete(ModelMap modelMap, Long id) {
        dataDictionaryValueService.delete(id);
        return BaseOutput.success();
    }
}