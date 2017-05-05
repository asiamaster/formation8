package com.dili.formation8.controller;

import com.dili.formation8.domain.Deposit;
import com.dili.formation8.service.DepositService;
import com.dili.utils.domain.BaseOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 由MyBatis Generator工具自动生成
 * This file was generated on 2017-04-28 21:44:28.
 */
@Controller
@RequestMapping("/deposit")
public class DepositController {
    @Autowired
    DepositService depositService;

    @RequestMapping("/list")
    public String list(ModelMap modelMap, @ModelAttribute Deposit deposit) {
        modelMap.put("list", depositService.list(deposit));
        return "deposit/list";
    }

    @RequestMapping("/insert")
    public String insert(ModelMap modelMap, @ModelAttribute Deposit deposit) {
        depositService.insertSelective(deposit);
        return "deposit/insert";
    }

    @RequestMapping("/update")
    public String update(ModelMap modelMap, @ModelAttribute Deposit deposit) {
        depositService.update(deposit);
        return "deposit/update";
    }

    @RequestMapping("/delete")
    public @ResponseBody
    BaseOutput delete(ModelMap modelMap, Long id) {
        depositService.delete(id);
        return BaseOutput.success();
    }
}