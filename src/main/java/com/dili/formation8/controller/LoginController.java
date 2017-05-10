package com.dili.formation8.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by asiam on 2017/5/10 0010.
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    @RequestMapping("/login.html")
    public String login(){
        return "login";
    }
}
