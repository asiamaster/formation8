package com.dili.formation8.controller;

import com.dili.formation8.domain.User;
import com.dili.formation8.service.UserService;
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
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping("/list")
    public String list(ModelMap modelMap, @ModelAttribute User user) {
        modelMap.put("list", userService.list(user));
        return "user/list";
    }

    @RequestMapping("/insert")
    public String insert(ModelMap modelMap, @ModelAttribute User user) {
        userService.insertSelective(user);
        return "user/insert";
    }

    @RequestMapping("/update")
    public String update(ModelMap modelMap, @ModelAttribute User user) {
        userService.update(user);
        return "user/update";
    }

    @RequestMapping("/delete")
    public @ResponseBody ResponseMessage delete(ModelMap modelMap, Long id) {
        userService.delete(id);
        return ResponseMessage.success();
    }
}