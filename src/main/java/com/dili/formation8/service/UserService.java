package com.dili.formation8.service;

import com.dili.formation8.domain.User;
import com.dili.formation8.passport.UserLoginData;
import com.dili.formation8.vo.UserVo;
import com.dili.utils.base.BaseService;
import com.dili.utils.domain.BaseOutput;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 由MyBatis Generator工具自动生成
 * This file was generated on 2017-04-27 10:56:44.
 */
public interface UserService extends BaseService<User, Long> {


    /**
     * 用户登录
     * @param username
     * @param password
     * @param request
     * @param response
     * @return
     */
    public BaseOutput<UserLoginData> login(String username, String password, String rememberMe, HttpServletRequest request, HttpServletResponse response);

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

    /**
     * 查询指定层级的推荐人
     * @param userVo
     * @return
     */
    public Long getParentReferrer(UserVo userVo);

    /**
     * 查询指定层级的下级引领和引荐人列表<br/>
     * @param id 用户id
     * @param level 查询的层级，如果为-1则查全部
     * @return 返回值的第0位是引领人列表，第1位-第N位是引荐人层级1-N的列表。
     */
    public List<List<User>> listSubReferrers(Long id, Integer level);


}