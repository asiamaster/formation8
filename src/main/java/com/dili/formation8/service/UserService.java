package com.dili.formation8.service;

import com.dili.formation8.domain.User;
import com.dili.utils.base.BaseService;

/**
 * 由MyBatis Generator工具自动生成
 * This file was generated on 2017-04-27 10:56:44.
 */
public interface UserService extends BaseService<User, Long> {


    /**
     * 登录前验证
     * 返回消息为验证不通过的提示信息
     * @param username
     * @param password
     * @return
     */
    public String loginPreCheck(String username, String password);
}