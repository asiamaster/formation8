package com.dili.formation8.component.impl;

import com.dili.formation8.component.CacheControlManager;
import com.dili.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("cacheControlManager")
public class CacheControlManagerImpl implements CacheControlManager {

    //登录次数前缀
    public static final String REDIS_KEY_LOGIN_COUNT = "user_login_count";

    @Autowired
    private RedisUtil redisUtil;

    public static final Logger logger = LoggerFactory.getLogger(CacheControlManagerImpl.class);


    //获取登录失败次数
    @Override
    public int getUserLoginFailTypeFromCache(String userName) {
        Integer times = null;
        String key = LOGIN_USER_TIMELIMIT_KEY_PREFIX + userName;
        times = redisUtil.get(key, Integer.class);
        return times != null ? times : 0;
    }

    //增加登录失败次数
    @Override
    public long incrementUserLoginFailTime(String userName, int expireTime) {
        String key = LOGIN_USER_TIMELIMIT_KEY_PREFIX + userName;
        Long times = redisUtil.get(key, Long.class);
        if (times == null) {
            if (redisUtil.set(key, 1L, new Long(expireTime))) {
                return 1L;
            } else {
                return -1L;
            }
        } else {
            return redisUtil.increment(key, 1l);
        }
    }
    //清空登录失败次数记录
    @Override
    public void removeUserLoginFailTimes(String userName) {
        String key = LOGIN_USER_TIMELIMIT_KEY_PREFIX + userName;
        redisUtil.remove(key);
        //清空登录次数
        key = makeKey(REDIS_KEY_LOGIN_COUNT, userName);
        redisUtil.remove(key);
    }

    private String makeKey(String key, String unique) {
        String rst = REDIS_PASSPORT_PREFIX + key;
        if(unique != null) {
            rst = rst + unique;
        }
        return rst;
    }

    @Override
    public void lockUserLogin(String userName, int expireTime) {
        String key = LOGIN_USER_LOCK_KEY_PREFIX + userName;
        redisUtil.set(key, userName, new Long(expireTime));
    }

    @Override
    public boolean queryLockUserLoginState(String userName) {
        String key = LOGIN_USER_LOCK_KEY_PREFIX + userName;
        return redisUtil.get(key) != null;
    }

}
