package com.dili.formation8.controller;

import com.dili.formation8.domain.BankCard;
import com.dili.formation8.service.BankCardService;
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
@RequestMapping("/bankCard")
public class BankCardController {
    @Autowired
    BankCardService bankCardService;

    @RequestMapping("/list")
    public String list(ModelMap modelMap, @ModelAttribute BankCard bankCard) {
        modelMap.put("list", bankCardService.list(bankCard));
        return "bankCard/list";
    }

    @RequestMapping("/insert")
    public String insert(ModelMap modelMap, @ModelAttribute BankCard bankCard) {
        bankCardService.insertSelective(bankCard);
        return "bankCard/insert";
    }

    @RequestMapping("/update")
    public String update(ModelMap modelMap, @ModelAttribute BankCard bankCard) {
        bankCardService.update(bankCard);
        return "bankCard/update";
    }

    @RequestMapping("/delete")
    public @ResponseBody BaseOutput delete(ModelMap modelMap, Long id) {
        bankCardService.delete(id);
        return BaseOutput.success();
    }
}