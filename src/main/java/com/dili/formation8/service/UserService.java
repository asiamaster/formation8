package com.dili.formation8.service;

import com.dili.formation8.domain.User;
import com.dili.formation8.vo.UserVo;
import com.dili.utils.base.BaseService;

/**
 * 由MyBatis Generator工具自动生成
 * This file was generated on 2017-04-27 10:56:44.
 */
public interface UserService extends BaseService<User, Long> {


    /**
     * 用户登录
     * @param username
     * @param password
     * @return
     */
    public String login(String username, String password);

    /**
     * 用户注册
     * @param user
     * @return
     */
    public String register(User user);

    /**
     *  转帐
     * @param sourceUserId  转出用户id
     * @param targetUserId  转入用户id
     * @param amount    转帐金额
     */
    public Boolean transfer(Long sourceUserId, Long targetUserId, Long amount);

    /**
     *  无日志转帐
     * @param sourceUserId  转出用户id
     * @param targetUserId  转入用户id
     * @param amount    转帐金额
     */
    public Boolean transferQuietly(Long sourceUserId, Long targetUserId, Long amount);

    /**
     * 余额调整
     * @param userId
     * @param balanceAdjust 正为加，负为减
     */
    public Boolean balanceAdjust(Long userId, Long balanceAdjust);

    //查询指定层级的推荐人
    public Long getParentReferrer(UserVo userVo);
}