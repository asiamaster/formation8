package com.dili.formation8.domain;

import java.util.Date;
import javax.persistence.*;

/**
 * 由MyBatis Generator工具自动生成
 * 
 * This file was generated on 2017-04-27 10:56:44.
 */
public class Withdrawal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 提现编号
     */
    @Column(name = "transaction_number")
    private String transactionNumber;

    /**
     * 提现时余额,单位(分)
     */
    private Long balance;

    /**
     * 提现申请金额
     */
    @Column(name = "withdrawal_amount")
    private Long withdrawalAmount;

    /**
     * 提现手续费
     */
    @Column(name = "withdrawal_charge")
    private Integer withdrawalCharge;

    /**
     * 待处理，已处理
     */
    @Column(name = "withdrawal_state")
    private Integer withdrawalState;

    /**
     * 提现申请时间
     */
    @Column(name = "application_time")
    private Date applicationTime;

    /**
     * 提现完成时间
     */
    @Column(name = "finish_time")
    private Date finishTime;

    /**
     * 银行卡id
     */
    @Column(name = "bank_card_id")
    private Long bankCardId;

    /**
     * 提现人id
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取提现编号
     *
     * @return transaction_number - 提现编号
     */
    public String getTransactionNumber() {
        return transactionNumber;
    }

    /**
     * 设置提现编号
     *
     * @param transactionNumber 提现编号
     */
    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    /**
     * 获取提现时余额,单位(分)
     *
     * @return balance - 提现时余额,单位(分)
     */
    public Long getBalance() {
        return balance;
    }

    /**
     * 设置提现时余额,单位(分)
     *
     * @param balance 提现时余额,单位(分)
     */
    public void setBalance(Long balance) {
        this.balance = balance;
    }

    /**
     * 获取提现申请金额
     *
     * @return withdrawal_amount - 提现申请金额
     */
    public Long getWithdrawalAmount() {
        return withdrawalAmount;
    }

    /**
     * 设置提现申请金额
     *
     * @param withdrawalAmount 提现申请金额
     */
    public void setWithdrawalAmount(Long withdrawalAmount) {
        this.withdrawalAmount = withdrawalAmount;
    }

    /**
     * 获取提现手续费
     *
     * @return withdrawal_charge - 提现手续费
     */
    public Integer getWithdrawalCharge() {
        return withdrawalCharge;
    }

    /**
     * 设置提现手续费
     *
     * @param withdrawalCharge 提现手续费
     */
    public void setWithdrawalCharge(Integer withdrawalCharge) {
        this.withdrawalCharge = withdrawalCharge;
    }

    /**
     * 获取待处理，已处理
     *
     * @return withdrawal_state - 待处理，已处理
     */
    public Integer getWithdrawalState() {
        return withdrawalState;
    }

    /**
     * 设置待处理，已处理
     *
     * @param withdrawalState 待处理，已处理
     */
    public void setWithdrawalState(Integer withdrawalState) {
        this.withdrawalState = withdrawalState;
    }

    /**
     * 获取提现申请时间
     *
     * @return application_time - 提现申请时间
     */
    public Date getApplicationTime() {
        return applicationTime;
    }

    /**
     * 设置提现申请时间
     *
     * @param applicationTime 提现申请时间
     */
    public void setApplicationTime(Date applicationTime) {
        this.applicationTime = applicationTime;
    }

    /**
     * 获取提现完成时间
     *
     * @return finish_time - 提现完成时间
     */
    public Date getFinishTime() {
        return finishTime;
    }

    /**
     * 设置提现完成时间
     *
     * @param finishTime 提现完成时间
     */
    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    /**
     * 获取银行卡id
     *
     * @return bank_card_id - 银行卡id
     */
    public Long getBankCardId() {
        return bankCardId;
    }

    /**
     * 设置银行卡id
     *
     * @param bankCardId 银行卡id
     */
    public void setBankCardId(Long bankCardId) {
        this.bankCardId = bankCardId;
    }

    /**
     * 获取提现人id
     *
     * @return user_id - 提现人id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置提现人id
     *
     * @param userId 提现人id
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }
}