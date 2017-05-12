package com.dili.formation8.controller;

import com.dili.formation8.passport.AuthTicket;
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
 * 主站首页控制器
 * Created by asiam on 2017/5/10 0010.
 */
@Controller
@RequestMapping("")
public class IndexController {

    /**
     * 跳转到首页
     * @param request
     * @return
     */
    @RequestMapping()
    public String index(HttpServletRequest request){
        //获取登录凭证
        AuthTicket ticket = AuthTicket.getTicket();
        request.setAttribute("ticket", ticket);
        return "index";
    }


}
