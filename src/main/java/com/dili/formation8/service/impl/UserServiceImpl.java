package com.dili.formation8.service.impl;

import com.dili.formation8.dao.FinancialTransactionMapper;
import com.dili.formation8.dao.UserMapper;
import com.dili.formation8.domain.FinancialTransaction;
import com.dili.formation8.domain.User;
import com.dili.formation8.passport.CookieEncryptUtil;
import com.dili.formation8.passport.CookieManager;
import com.dili.formation8.passport.PassportUtils;
import com.dili.formation8.passport.service.TimeLimitService;
import com.dili.formation8.service.BizNumberService;
import com.dili.formation8.service.UserService;
import com.dili.formation8.utils.*;
import com.dili.formation8.vo.UserData;
import com.dili.formation8.passport.UserLoginData;
import com.dili.formation8.vo.UserVo;
import com.dili.utils.BeanConver;
import com.dili.utils.base.BaseServiceImpl;
import com.dili.utils.domain.BaseOutput;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 由MyBatis Generator工具自动生成
 * This file was generated on 2017-04-27 10:56:44.
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User, Long> implements UserService {

    @Autowired
    private FinancialTransactionMapper financialTransactionMapper;

    @Autowired
    TimeLimitService timeLimitService;

    @Autowired
    CookieManager cookieManager;

    @Autowired
    CookieEncryptUtil cookieEncryptUtil;

    @Autowired
    private BizNumberService bizNumberService;

    protected static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    public UserMapper getActualDao() {
        return (UserMapper)getDao();
    }

    /**
     * 登录前验证
     * 返回消息为验证不通过的提示信息
     * @param username
     * @param password
     * @return
     */
    private String loginPreCheck(String username, String password) {
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
        // 检查同个用户名输入错误密码登陆次数，若20分钟6次输错，冻结此账号20分钟
        long idLoginTimes = 0;
        try {
            idLoginTimes = timeLimitService.getUserLoginFailTime(username); // get value from memcache
        } catch (Exception e) {
            idLoginTimes = 0;
        }

        if (idLoginTimes >= TimeLimitService.LOGIN_FAIL_NEED_LOCK) {
            return "密码错误次数超限，请" + TimeLimitService.USER_LOGIN_FAIL_TIME_LIMIT / 60 + "分钟后重试";
        }
        return null;
    }

    @Override
    public BaseOutput<UserLoginData> login(String username, String password, String rememberMe, HttpServletRequest request, HttpServletResponse response) {
//        if (cookieManager.getLoginTimes(request) >= TimeLimitService.LOGIN_TIME_NEED_AUTHCODE ||  cookieManager.getLoginTimesByIp(request) >= TimeLimitService.LOGIN_TIME_NEED_AUTHCODE){
//            needAuthCode = true;
//        }
        String checkResult = loginPreCheck(username, password);
        if(StringUtils.isNoneBlank(checkResult)){
            return BaseOutput.failure(checkResult);
        }
        User user = new User();
        user.setName(username);
        user.setPassword(password);
        List<User> users =list(user);
        if(users == null || users.isEmpty()){
            String msg = checkLockCondition(username);
            //登录失败则添加cookie和ip登录失败次数，用于显示验证码
//            cookieManager.addLoginTimes(request, response);
            //增加redis中用户和ip登录失败次数
            addUserLoginFailTimes(username, PassportUtils.getRemoteIP(request));
            return msg == null ? BaseOutput.failure("用户名或密码错误"): BaseOutput.failure(msg);
        }

        //检查是否需要验证码 同时需要检查对应的IP登录次数
//        int cookieTimes = cookieManager.getLoginTimes(request);
//        int ipTimes = cookieManager.getLoginTimesByIp(request);
//        boolean showAuthCode = cookieManager.getLoginTimes(request) >= TimeLimitService.LOGIN_TIME_NEED_AUTHCODE ||  cookieManager.getLoginTimesByIp(request) >= TimeLimitService.LOGIN_TIME_NEED_AUTHCODE;

        //登录成功流程.................................
        user = users.get(0);
        //写入cookie
        try {
            String cookieValue = cookieEncryptUtil.generateCookieString(getUserData(user));
            //登录成功后，清除cookies登录次数和ip登录次数
//            cookieManager.setCookieLoginTimes(request, response, -1);
            timeLimitService.removeUserLoginFailTime(PassportUtils.getRemoteIP(request));
            if (StringUtils.isNotBlank(rememberMe) /**&& "checked".equalsIgnoreCase(rememberMe)*/) {
                cookieManager.newAuthCookies(response, CookieManager.DILI_AUTH_COOKIE_NAME, cookieValue, CookieManager.REMEMBER_ME_EXPIRE_TIME, true);
            } else {
                cookieManager.newAuthCookies(response, CookieManager.DILI_AUTH_COOKIE_NAME, cookieValue, -1, true);
            }
            log.info("---用户 [" + user.getName() + " | " + PassportUtils.getRemoteIP(request) + "] 登录Login Success---");

        }catch(Exception e){
//            cookieManager.addLoginTimes(request, response);
            //增加redis中用户和ip登录失败次数
            addUserLoginFailTimes(username, PassportUtils.getRemoteIP(request));
            return BaseOutput.failure(e.getMessage());
        }
        UserLoginData userLoginData = BeanConver.copeBean(user, UserLoginData.class);
        userLoginData.setReturnUrl(PassportUtils.getReturnUrl(request));
        userLoginData.setIp(PassportUtils.getRemoteIP(request));
        return BaseOutput.success("登录成功").setData(userLoginData);
    }

    private UserData getUserData(User user) {
        UserData userData = new UserData();
        userData.setUserName(user.getName());
        userData.setReferer(user.getReferrer());
        userData.setReferralCode(user.getReferralCode());
        userData.setUid(user.getId());//UserId
        userData.setUserType(user.getType());
        userData.setVersion(1);
        return userData;
    }

    /**
     * 增加用户登录失败次数
     */
    private void addUserLoginFailTimes(String name , String ip) {
        timeLimitService.incrementUserLoginFailTime(name);
        timeLimitService.incrementUserLoginFailTime(ip);
    }

    /**
     * 检查是否被锁定，密码错误给出相应提示
     * @param name
     * @return
     */
    private String checkLockCondition(String name) {
//        获取redis登录失败次数，没有则返回0
        long idLoginTimes = timeLimitService.getUserLoginFailTime(name);
        if(idLoginTimes == TimeLimitService.LOGIN_FAIL_NEED_LOCK - 2){
            return "密码错误，还可以输入两次";
        }else if(idLoginTimes == TimeLimitService.LOGIN_FAIL_NEED_LOCK - 1){
            return "密码错误，还可以输入一次";
        }else if(idLoginTimes >= TimeLimitService.LOGIN_FAIL_NEED_LOCK){
//            登录超过一定次数后，redis锁定用户一段时间
            timeLimitService.lockUserLogin(name);
            return "密码错误次数超限，请" + TimeLimitService.USER_LOGIN_FAIL_TIME_LIMIT / 60 + "分钟后重试";
        }
        return null;
    }

    public String register(User user){
        String checkResult = loginPreCheck(user.getName(), user.getPassword());
        if(checkResult != null){
            return checkResult;
        }
        insertSelective(user);
        return null;
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED,rollbackFor=Exception.class)
    public Boolean transfer(Long sourceUserId, Long targetUserId, Long amount) {
        Boolean result = transferQuietly(sourceUserId, targetUserId, amount);
        if(!result) return false;
        //记录转帐日志
        FinancialTransaction financialTransaction = new FinancialTransaction();
        financialTransaction.setUserId(sourceUserId);
        financialTransaction.setPaymentTime(new Date());
        financialTransaction.setTransactionType(Formation8Constants.FinanciaTransactionType.TRANSFER.getCode());
        financialTransaction.setPaymentPattern(Formation8Constants.PaymentPattern.ALIPAY.getCode());
        financialTransaction.setTransactionAmount(amount);
        financialTransaction.setTargetUserId(targetUserId);
        financialTransaction.setTransactionNumber(bizNumberService.getTransferOrderCode());
        financialTransactionMapper.insert(financialTransaction);
        return true;
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED,rollbackFor=Exception.class)
    public Boolean transferQuietly(Long sourceUserId, Long targetUserId, Long amount) {
        User sourceUser = get(sourceUserId);
        //如果余额不足
        if( sourceUser.getBalance()<amount){
            return false;
        }
        UserVo user = new UserVo();
        user.setId(sourceUserId);
        user.setTransferAmount(-amount);
        getActualDao().transfer(user);
        user = new UserVo();
        user.setId(targetUserId);
        user.setTransferAmount(amount);
        getActualDao().transfer(user);
        return true;
    }

    @Override
    public Boolean balanceAdjust(Long userId, Long balanceAdjust) {
        User user = get(userId);
        if(user == null){
            throw new RuntimeException("用户户id["+userId+"]不存在");
        }
        //如果是扣款，并且余额不足
        if(balanceAdjust<0 && user.getBalance()+balanceAdjust<0){
            return false;
        }
        UserVo userVo = new UserVo();
        userVo.setId(userId);
        userVo.setTransferAmount(balanceAdjust);
        getActualDao().transfer(userVo);
        return true;
    }

    @Override
    public Long getParentReferrer(UserVo userVo) {
        return getActualDao().getParentReferrer(userVo);
    }

    @Override
    public List<List<User>> listSubReferrers(Long id, Integer level) {
        List<List<User>> users = new ArrayList<>();
        listRecursiveReferrers(Lists.newArrayList(id), level, users);
        return users;
    }

    /**
     * 递归查询下级推荐人
     * @param ids   要查的用户id列表
     * @param level 指定查询的层级，如果<0则查所有下级推荐人
     * @param users 递归查询结果对象
     * @return
     */
    private void listRecursiveReferrers(List<Long> ids, Integer level, List<List<User>> users) {
        if(level == 0) {
            return;
        }
        List<User> referals = listOneLevelReferrers(ids);
        if(!referals.isEmpty()) {
            users.add(referals);
        }
        level--;
        //如果层级没查完或者是level<0的情况下，继续往下查
        if(level >0 || level < 0) {
            //先获取当前推荐人的id列表
            List<Long> referalIds = Lists.newArrayList();
            for(User referal:referals){
                referalIds.add(referal.getId());
            }
            //如果下级推荐人不为空，则继续往下查
            if(!referalIds.isEmpty()) {
                listRecursiveReferrers(referalIds, level, users);
            }
        }

    }

    /**
     * 根据所有推荐人id列表向下查一级
     * @param ids 推荐人id列表
     * @return
     */
    private List<User> listOneLevelReferrers(List<Long> ids){
        Example example = new Example(User.class);
        example.createCriteria()
        .andIn("referrer", ids);
        example.orderBy("id").asc();
        return getActualDao().selectByExample(example);
    }

    @Override
    public int insertSelective(User user) {
        user.setReferralCode(ShortUrlGenerator.shortUrl(user.getName())[0]);
        return super.insertSelective(user);
    }

}