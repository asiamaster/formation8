package com.dili.formation8.controller;

import com.dili.formation8.domain.Product;
import com.dili.formation8.service.ProductService;
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
@RequestMapping("/product")
public class ProductController {
    @Autowired
    ProductService productService;

    @RequestMapping("/list")
    public String list(ModelMap modelMap, @ModelAttribute Product product) {
        modelMap.put("list", productService.list(product));
        return "product/list";
    }

    @RequestMapping("/insert")
    public String insert(ModelMap modelMap, @ModelAttribute Product product) {
        productService.insertSelective(product);
        return "product/insert";
    }

    @RequestMapping("/update")
    public String update(ModelMap modelMap, @ModelAttribute Product product) {
        productService.update(product);
        return "product/update";
    }

    @RequestMapping("/delete")
    public @ResponseBody
    BaseOutput delete(ModelMap modelMap, Long id) {
        productService.delete(id);
        return BaseOutput.success();
    }
}