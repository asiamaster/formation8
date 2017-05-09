package com.dili.formation8.service.impl;

import com.dili.formation8.dao.FinancialTransactionMapper;
import com.dili.formation8.dao.UserMapper;
import com.dili.formation8.domain.FinancialTransaction;
import com.dili.formation8.domain.User;
import com.dili.formation8.service.BizNumberService;
import com.dili.formation8.service.UserService;
import com.dili.formation8.utils.ShortUrlGenerator;
import com.dili.formation8.vo.Formation8Constants;
import com.dili.formation8.vo.UserVo;
import com.dili.utils.base.BaseServiceImpl;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.IntegerTypeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

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
    private BizNumberService bizNumberService;

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
        return null;
    }

    @Override
    public String login(String username, String password) {
        String checkResult = loginPreCheck(username, password);
        if(StringUtils.isNoneBlank(checkResult)){
            return checkResult;
        }
        User user = new User();
        user.setName(username);
        user.setPassword(password);
        List<User> users =list(user);
        if(users == null || users.isEmpty()){
            return "用户名或密码错误";
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