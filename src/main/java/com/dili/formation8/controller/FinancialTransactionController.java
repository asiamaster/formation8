package com.dili.formation8.controller;

import com.dili.formation8.domain.FinancialTransaction;
import com.dili.formation8.service.FinancialTransactionService;
import com.dili.utils.domain.ResponseMessage;
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
@RequestMapping("/financialTransaction")
public class FinancialTransactionController {
    @Autowired
    FinancialTransactionService financialTransactionService;

    @RequestMapping("/list")
    public String list(ModelMap modelMap, @ModelAttribute FinancialTransaction financialTransaction) {
        modelMap.put("list", financialTransactionService.list(financialTransaction));
        return "financialTransaction/list";
    }

    @RequestMapping("/insert")
    public String insert(ModelMap modelMap, @ModelAttribute FinancialTransaction financialTransaction) {
        financialTransactionService.insertSelective(financialTransaction);
        return "financialTransaction/insert";
    }

    @RequestMapping("/update")
    public String update(ModelMap modelMap, @ModelAttribute FinancialTransaction financialTransaction) {
        financialTransactionService.update(financialTransaction);
        return "financialTransaction/update";
    }

    @RequestMapping("/delete")
    public @ResponseBody ResponseMessage delete(ModelMap modelMap, Long id) {
        financialTransactionService.delete(id);
        return ResponseMessage.success();
    }
}