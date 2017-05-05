package com.dili.formation8.controller;

import com.dili.formation8.domain.User;
import com.dili.formation8.service.UserService;
import com.dili.formation8.utils.ZxingUtils;
import com.dili.utils.domain.BaseOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 由MyBatis Generator工具自动生成
 * This file was generated on 2017-04-27 10:56:44.
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    /**
     * 登录接口
     * @param request
     * @param response
     * @param name
     * @param password
     * @return
     */
    @RequestMapping(value = "/loginService.aspx", method = { RequestMethod.POST })
    public @ResponseBody String doLogin(HttpServletRequest request, HttpServletResponse response,
                   @RequestParam(value="name", required=true) String name, @RequestParam(value="password", required=true) String password) {
        String checkResult = userService.loginPreCheck(name, password);
        if(checkResult != null){
            throw new RuntimeException("登录验证失败:"+checkResult);
        }
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        List<User> users =userService.list(user);
        if(users == null || users.isEmpty()){
            throw new RuntimeException("用户名或密码错误");
        }
        return "{\"result\":\"success\"}";
    }

    /**
     * 注册
     * @param modelMap
     * @param user
     * @return
     */
    @RequestMapping("/register.aspx")
    public String insert(ModelMap modelMap, @ModelAttribute User user) {
        String checkResult = userService.loginPreCheck(user.getName(), user.getPassword());
        if(checkResult != null){
            throw new RuntimeException("登录验证失败:"+checkResult);
        }
        userService.insertSelective(user);
        return "user/insert";
    }

    /**
     *  转帐
     * @param sourceUserId  转出用户id
     * @param targetUserId  转入用户id
     * @param amount    转帐金额
     */
    @RequestMapping("/transfer.aspx")
    public @ResponseBody String transfer(Long sourceUserId, Long targetUserId, Long amount){
        userService.transfer(sourceUserId, targetUserId, amount);
        return "转帐成功!";
    }

    /**
     * url:http://localhost:8080/user/referralCode.aspx?str=1&width=320&height=240
     * 四个参数:
     * 二维码字符串str,
     * 宽度:width,
     * 高度：height,
     * 图片类型(默认为jpg, 如:jpg, png, gif等):fmt
     *
     */
    @RequestMapping("/referralCode.aspx")
    public void referralCode(@RequestParam(value="str", required=true)String str,
                             @RequestParam(value="width", required=true)Integer width,
                             @RequestParam(value="height", required=true)Integer height,
                             @RequestParam(value="format", required=false)String fmt,
                             HttpServletRequest request, HttpServletResponse response) {

        try {
            if(str != null){
                ZxingUtils.writeToStream(ZxingUtils.toQrCodeMatrix(str, width, height), fmt == null ? "jpg" : fmt, response.getOutputStream());
                response.getOutputStream().flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/list.aspx")
    public String list(ModelMap modelMap, @ModelAttribute User user) {
        modelMap.put("list", userService.list(user));
        return "user/list";
    }



    @RequestMapping("/update.aspx")
    public String update(ModelMap modelMap, @ModelAttribute User user) {
        userService.update(user);
        return "user/update";
    }

    @RequestMapping("/delete.aspx")
    public @ResponseBody
    BaseOutput delete(ModelMap modelMap, Long id) {
        userService.delete(id);
        return BaseOutput.success();
    }
}