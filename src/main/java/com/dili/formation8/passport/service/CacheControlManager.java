package com.dili.formation8.passport.service;



public interface CacheControlManager {
   
    public static final String REDIS_PASSPORT_PREFIX="REDIS_PASSPORT_";
    
    public static final String LOGIN_USER_TIMELIMIT_KEY_PREFIX = "REDIS_PASSPORT_LOGIN_LIMIT_";

    public static final String LOGIN_USER_LOCK_KEY_PREFIX = "REDIS_PASSPORT_LOGIN_LOCK_";

    public static final String FORGOT_PWD_TOKEN = "REDIS_PASSPORT_FORGOT_TOKEN";

    public long incrementUserLoginFailTime(String username, int expireTime);

    public int getUserLoginFailTypeFromCache(String userName);

    public void removeUserLoginFailTimes(String userName);

    public void lockUserLogin(String userName, int expireTime);

    public boolean queryLockUserLoginState(String userName);

}

