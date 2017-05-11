package com.dili.formation8.passport.service;


import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Component
public class TimeLimitService {
   
    public static int IP_REGISTER_TIME_LIMIT = 10*60; //10分钟

    public static int USER_LOGIN_FAIL_TIME_LIMIT = 2*60*60;  //2小时

    public static int USER_LOGIN_FAIL_LOCK_TIME = 20*60;  //20分钟
    //可以在pom.xml进行配置
    public static int LOGIN_TIME_NEED_AUTHCODE  = 2;
    
    public static int LOGIN_FAIL_NEED_LOCK  = 6;
    
    @Resource
    private CacheControlManager cacheControlManager;
    
    public int getUserLoginFailTime(String userName){
        try {
            return cacheControlManager.getUserLoginFailTypeFromCache(URLEncoder.encode(userName, "UTF-8"));
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return -1;
    }
    
    public long incrementUserLoginFailTime(String userName){
        try {
            return cacheControlManager.incrementUserLoginFailTime(URLEncoder.encode(userName, "UTF-8"), USER_LOGIN_FAIL_TIME_LIMIT);
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return -1;
    }
    
    public void removeUserLoginFailTime(String userName){
        try {
            cacheControlManager.removeUserLoginFailTimes(URLEncoder.encode(userName, "UTF-8"));
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    
    public void lockUserLogin(String userName){
        try {
            cacheControlManager.lockUserLogin(URLEncoder.encode(userName, "UTF-8"), USER_LOGIN_FAIL_LOCK_TIME);
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 
     * this method is 查询用户账户是否处于锁定状态
     * @param userName
     * @return true - 锁定 | false - 非锁定
     * @createTime 2014-6-12 下午6:11:42
     * @author Nick
     */
    public boolean queryLockUserLoginState(String userName){
        try {
            return cacheControlManager.queryLockUserLoginState(URLEncoder.encode(userName, "UTF-8"));
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return false;
    }

    
    public static int getIP_REGISTER_TIME_LIMIT() {
        return IP_REGISTER_TIME_LIMIT;
    }

    
    public void setIP_REGISTER_TIME_LIMIT(int iP_REGISTER_TIME_LIMIT) {
        IP_REGISTER_TIME_LIMIT = iP_REGISTER_TIME_LIMIT;
    }

    
    public static int getUSER_LOGIN_FAIL_TIME_LIMIT() {
        return USER_LOGIN_FAIL_TIME_LIMIT;
    }

    
    public void setUSER_LOGIN_FAIL_TIME_LIMIT(int uSER_LOGIN_FAIL_TIME_LIMIT) {
        USER_LOGIN_FAIL_TIME_LIMIT = uSER_LOGIN_FAIL_TIME_LIMIT;
    }

    
    public static int getLOGIN_TIME_NEED_AUTHCODE() {
        return LOGIN_TIME_NEED_AUTHCODE;
    }

    
    public void setLOGIN_TIME_NEED_AUTHCODE(int lOGIN_TIME_NEED_AUTHCODE) {
        LOGIN_TIME_NEED_AUTHCODE = lOGIN_TIME_NEED_AUTHCODE;
    }

    
    public static int getUSER_LOGIN_FAIL_LOCK_TIME() {
        return USER_LOGIN_FAIL_LOCK_TIME;
    }

    
    public void setUSER_LOGIN_FAIL_LOCK_TIME(int uSER_LOGIN_FAIL_LOCK_TIME) {
        USER_LOGIN_FAIL_LOCK_TIME = uSER_LOGIN_FAIL_LOCK_TIME;
    }

    
    public static int getLOGIN_FAIL_NEED_LOCK() {
        return LOGIN_FAIL_NEED_LOCK;
    }

    
    public void setLOGIN_FAIL_NEED_LOCK(int lOGIN_FAIL_NEED_LOCK) {
        LOGIN_FAIL_NEED_LOCK = lOGIN_FAIL_NEED_LOCK;
    }
    
}
