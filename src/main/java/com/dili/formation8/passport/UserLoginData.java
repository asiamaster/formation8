package com.dili.formation8.passport;

import com.dili.formation8.domain.User;

/**
 * Created by asiam on 2017/5/4 0004.
 */
public class UserLoginData extends User {
    //用户登录的ip
    private String ip;

    //登录前用户访问的，需要跳回去的url
    private String returnUrl;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }



}
