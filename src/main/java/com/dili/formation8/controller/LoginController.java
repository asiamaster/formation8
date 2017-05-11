package com.dili.formation8.controller;

import com.dili.formation8.service.UserService;
import com.dili.utils.domain.BaseOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by asiam on 2017/5/10 0010.
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    UserService userService;

    @RequestMapping("/login.html")
    public String login(){
        return "login";
    }

    /**
     * ajax登录接口，登录成功后在页面判断是否作sso，页面根据returnUrl自行决定跳转
     * @param request
     * @param response
     * @param name
     * @param password
     * @return
     */
    @RequestMapping(value = "/loginService.aspx", method = { RequestMethod.POST })
    public @ResponseBody
    BaseOutput doLogin(HttpServletRequest request, HttpServletResponse response,
                       @RequestParam(value="name", required=true) String name,
                       @RequestParam(value="password", required=true) String password,
                       @RequestParam(value="rememberMe", required=false) String rememberMe) {
        return userService.login(name, password, rememberMe, request, response);
    }
}
