package com.dili.formation8.controller;

import com.dili.formation8.domain.Order;
import com.dili.formation8.service.OrderService;
import com.dili.utils.domain.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 由MyBatis Generator工具自动生成
 * This file was generated on 2017-04-28 10:20:40.
 */
@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    OrderService orderService;

    @RequestMapping("/list")
    public String list(ModelMap modelMap, @ModelAttribute Order order) {
        modelMap.put("list", orderService.list(order));
        return "order/list";
    }

    @RequestMapping("/insert")
    public String insert(ModelMap modelMap, @ModelAttribute Order order) {
        orderService.insertSelective(order);
        return "order/insert";
    }

    @RequestMapping("/update")
    public String update(ModelMap modelMap, @ModelAttribute Order order) {
        orderService.update(order);
        return "order/update";
    }

    @RequestMapping("/delete")
    public @ResponseBody ResponseMessage delete(ModelMap modelMap, Long id) {
        orderService.delete(id);
        return ResponseMessage.success();
    }
}