package com.dili.formation8.controller;

import com.dili.formation8.domain.User;
import com.dili.formation8.service.UserService;
import com.dili.formation8.utils.ZxingUtils;
import com.dili.utils.domain.BaseOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 由MyBatis Generator工具自动生成
 * This file was generated on 2017-04-27 10:56:44.
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    protected static final Logger log = LoggerFactory.getLogger(UserController.class);

    /**
     * 注册
     * @param modelMap
     * @param user
     * @return
     */
    @RequestMapping("/register.aspx")
    public @ResponseBody BaseOutput insert(ModelMap modelMap, @ModelAttribute User user) {
        String msg = userService.register(user);
        return msg == null ? BaseOutput.success("注册成功") : BaseOutput.failure(msg);
    }

    /**
     *  转帐
     * @param sourceUserId  转出用户id
     * @param targetUserId  转入用户id
     * @param amount    转帐金额
     */
    @RequestMapping("/transfer.aspx")
    public @ResponseBody BaseOutput transfer(Long sourceUserId, Long targetUserId, Long amount){
        Boolean result = userService.transfer(sourceUserId, targetUserId, amount);
        return result?BaseOutput.success("转帐成功"):BaseOutput.failure("余额不足");
    }

    /**
     * 查询指定层级的下级引领和引荐人列表<br/>
     * @param userId 用户id
     * @param level 查询的层级，如果为-1则查全部
     * @return 返回值的第0位是引领人列表，第1位-第N位是引荐人层级1-N的列表。
     */
    @RequestMapping("/listSubReferrers.aspx")
    public @ResponseBody BaseOutput listSubReferrers(Long userId, Integer level){
        return BaseOutput.success().setData(userService.listSubReferrers(userId, level));
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