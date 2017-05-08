package com.dili.formation8.service.impl;

import com.dili.formation8.dao.WithdrawalMapper;
import com.dili.formation8.domain.BankCard;
import com.dili.formation8.domain.SystemConfig;
import com.dili.formation8.domain.User;
import com.dili.formation8.domain.Withdrawal;
import com.dili.formation8.service.BankCardService;
import com.dili.formation8.service.SystemConfigService;
import com.dili.formation8.service.UserService;
import com.dili.formation8.service.WithdrawalService;
import com.dili.formation8.vo.Formation8Constants;
import com.dili.utils.base.BaseServiceImpl;
import com.dili.utils.domain.BaseOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 由MyBatis Generator工具自动生成
 * This file was generated on 2017-04-27 10:56:44.
 */
@Service
public class WithdrawalServiceImpl extends BaseServiceImpl<Withdrawal, Long> implements WithdrawalService {

    @Autowired
    private BankCardService bankCardService;
    @Autowired
    private UserService userService;
    @Autowired
    private SystemConfigService systemConfigService;
    public WithdrawalMapper getActualDao() {
        return (WithdrawalMapper)getDao();
    }

    @Override
    public BaseOutput<Withdrawal> withdrawal(Withdrawal withdrawal) {
        //检验提现参数
        BaseOutput output = preWithdrawalCheck(withdrawal);
        if(!output.isSuccess()){
            return output;
        }
        //设置提现参数
        setWithdrawalParams(withdrawal);
        //插入提现记录
        getActualDao().insert(withdrawal);
        //给平台打款
        userService.transfer(withdrawal.getUserId(), Formation8Constants.PLATFORM_USER_ID, withdrawal.getWithdrawalCharge());
        return output;
    }

    /**
     * 检查提现参数,并设置提现银行卡号和提现时用户的余额
     * @param withdrawal
     * @return
     */
    private BaseOutput preWithdrawalCheck(Withdrawal withdrawal){
        if(withdrawal.getUserId() == null){
            return BaseOutput.failure("提现用户ID为空");
        }
        if(withdrawal.getWithdrawalAmount() == null){
            return BaseOutput.failure("提现申请金额为空");
        }
        BankCard bankCard = new BankCard();
        bankCard.setUserId(withdrawal.getUserId());
        bankCard.setIsDefault(Formation8Constants.Yn.YES.getCode());
        List<BankCard> bankCards = bankCardService.list(bankCard);
        if(bankCards.isEmpty()){
            return BaseOutput.failure("用户未绑定银行卡");
        }
        //设置提现银行卡号
        withdrawal.setCardNumber(bankCards.get(0).getCardNumber());
        User user = userService.get(withdrawal.getUserId());
        if(user == null){
            return BaseOutput.failure("提现用户不存在");
        }
        if(user.getBalance()<withdrawal.getWithdrawalAmount()){
            return BaseOutput.failure("余额不足");
        }
        SystemConfig systemConfig = new SystemConfig();
        systemConfig.setCode(Formation8Constants.SYSTEM_CONFIG_WITHDRAW_DEPOSIT_AMOUNT_FLOOR);
        systemConfig.setYn(Formation8Constants.Yn.YES.getCode());
        List<SystemConfig> systemConfigs = systemConfigService.list(systemConfig);
        if(systemConfigs.isEmpty()){
            throw new RuntimeException("系统错误:提现金额下限参数不存在");
        }
        //判断提现金额下限
        String withdrawDepositAmountFloor = systemConfigs.get(0).getValue();
        if(withdrawal.getWithdrawalAmount()<Long.parseLong(withdrawDepositAmountFloor)){
            return BaseOutput.failure("提现金额小于"+Long.parseLong(withdrawDepositAmountFloor)/100+"元，不能提现");
        }
        //设置提现时用户的余额
        withdrawal.setBalance(user.getBalance());
        return BaseOutput.success();

    }

    /**
     * 设置提现参数
     * @param withdrawal
     * @return
     */
    private void setWithdrawalParams(Withdrawal withdrawal){
        withdrawal.setApplicationTime(new Date());
        withdrawal.setWithdrawalState(Formation8Constants.WithdrawalState.PENDING.getCode());
        SystemConfig systemConfig = new SystemConfig();
        systemConfig.setCode(Formation8Constants.SYSTEM_CONFIG_WITHDRAW_DEPOSIT_RATE);
        systemConfig.setYn(Formation8Constants.Yn.YES.getCode());
        List<SystemConfig> systemConfigs = systemConfigService.list(systemConfig);
        if(systemConfigs.isEmpty()){
            throw new RuntimeException("系统错误:提现费率参数不存在");
        }
        //设置提现手续费
        String withdrawDepositRate = systemConfigs.get(0).getValue();
        withdrawal.setWithdrawalCharge(withdrawal.getWithdrawalAmount()*Long.parseLong(withdrawDepositRate)/100l);
    }


}