package com.dili.formation8.service;

import com.dili.formation8.domain.Withdrawal;
import com.dili.utils.base.BaseService;
import com.dili.utils.domain.BaseOutput;

/**
 * 由MyBatis Generator工具自动生成
 * This file was generated on 2017-04-27 10:56:44.
 */
public interface WithdrawalService extends BaseService<Withdrawal, Long> {

    /**
     * 提现流程:
     * 1. 检查提现必填参数,用户ID和提现申请金额不能为空<br/>
     * 2. 判断用户是否存在，用户是否绑定银行卡，用户余额是否充足,判断提现金额下限,记录提现时用户的余额<br/>
     * 3. 设置提现手续费和提现编号<br/>
     * 4. 插入提现记录<br/>
     * 5. 扣除余额(提现金额-手续费)<br/>
     * 6. 给平台打款手续费<br/>
     * @param withdrawal
     * @return
     */
    public BaseOutput<Withdrawal> withdrawal(Withdrawal withdrawal);


}