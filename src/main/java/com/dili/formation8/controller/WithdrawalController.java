package com.dili.formation8.controller;

import com.dili.formation8.domain.Withdrawal;
import com.dili.formation8.service.WithdrawalService;
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
@RequestMapping("/withdrawal")
public class WithdrawalController {
    @Autowired
    WithdrawalService withdrawalService;

    /**
     * 提现
     * @param modelMap
     * @param withdrawal
     * @return
     */
    @RequestMapping("/withdrawal.aspx")
    public @ResponseBody BaseOutput<Withdrawal> withdrawal(ModelMap modelMap, @ModelAttribute Withdrawal withdrawal) {
        return withdrawalService.withdrawal(withdrawal);
    }

    @RequestMapping("/list")
    public String list(ModelMap modelMap, @ModelAttribute Withdrawal withdrawal) {
        modelMap.put("list", withdrawalService.list(withdrawal));
        return "withdrawal/list";
    }

    @RequestMapping("/insert")
    public String insert(ModelMap modelMap, @ModelAttribute Withdrawal withdrawal) {
        withdrawalService.insertSelective(withdrawal);
        return "withdrawal/insert";
    }

    @RequestMapping("/update")
    public String update(ModelMap modelMap, @ModelAttribute Withdrawal withdrawal) {
        withdrawalService.update(withdrawal);
        return "withdrawal/update";
    }

    @RequestMapping("/delete")
    public @ResponseBody
    BaseOutput delete(ModelMap modelMap, Long id) {
        withdrawalService.delete(id);
        return BaseOutput.success();
    }
}