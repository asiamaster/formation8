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
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

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
    @Transactional(propagation= Propagation.REQUIRED,rollbackFor=Exception.class)
    public void transfer(Long sourceUserId, Long targetUserId, Long amount) {
        transferQuietly(sourceUserId, targetUserId, amount);
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
    }

    @Override
    public void transferQuietly(Long sourceUserId, Long targetUserId, Long amount) {
        UserVo user = new UserVo();
        user.setId(sourceUserId);
        user.setTransferAmount(-amount);
        getActualDao().transfer(user);
        user = new UserVo();
        user.setId(targetUserId);
        user.setTransferAmount(amount);
        getActualDao().transfer(user);
    }

    @Override
    public void balanceAdjust(Long userId, Long balanceAdjust) {
        UserVo user = new UserVo();
        user.setId(userId);
        user.setTransferAmount(balanceAdjust);
        getActualDao().transfer(user);
    }

    @Override
    public Long getParentReferrer(UserVo userVo) {
        return getActualDao().getParentReferrer(userVo);
    }

    @Override
    public int insertSelective(User user) {
        user.setReferralCode(ShortUrlGenerator.shortUrl(user.getName())[0]);
        return super.insertSelective(user);
    }

}