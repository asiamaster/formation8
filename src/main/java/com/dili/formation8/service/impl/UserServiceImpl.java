package com.dili.formation8.service.impl;

import com.dili.formation8.dao.UserMapper;
import com.dili.formation8.domain.User;
import com.dili.formation8.service.UserService;
import com.dili.formation8.utils.ShortUrlGenerator;
import com.dili.utils.base.BaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 由MyBatis Generator工具自动生成
 * This file was generated on 2017-04-27 10:56:44.
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User, Long> implements UserService {

    public UserMapper getActualDao() {
        return (UserMapper)getDao();
    }

    @Override
    public String loginPreCheck(String username, String password) {
        // 检查用户名
        if (StringUtils.isBlank(username)) {
            return "用户名不能为空";
        }
        if (StringUtils.isBlank(password)) {
            return "密码不能为空";
        }
        if(username.length()>20){
            return "用户名长度不能多于20个字符";
        }
        if(password.length()>20){
            return "密码长度不能多于20个字符";
        }
        return null;
    }

    @Override
    public int insertSelective(User user) {
        user.setReferralCode(ShortUrlGenerator.shortUrl(user.getName())[0]);
        return super.insertSelective(user);
    }

}