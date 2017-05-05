package com.dili.formation8.controller;

import com.dili.formation8.domain.Sku;
import com.dili.formation8.service.SkuService;
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
@RequestMapping("/sku")
public class SkuController {
    @Autowired
    SkuService skuService;

    @RequestMapping("/list")
    public String list(ModelMap modelMap, @ModelAttribute Sku sku) {
        modelMap.put("list", skuService.list(sku));
        return "sku/list";
    }

    @RequestMapping("/insert")
    public String insert(ModelMap modelMap, @ModelAttribute Sku sku) {
        skuService.insertSelective(sku);
        return "sku/insert";
    }

    @RequestMapping("/update")
    public String update(ModelMap modelMap, @ModelAttribute Sku sku) {
        skuService.update(sku);
        return "sku/update";
    }

    @RequestMapping("/delete")
    public @ResponseBody
    BaseOutput delete(ModelMap modelMap, Long id) {
        skuService.delete(id);
        return BaseOutput.success();
    }
}