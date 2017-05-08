package com.dili.formation8.domain;

import java.util.Date;
import javax.persistence.*;

/**
 * 由MyBatis Generator工具自动生成
 * 
 * This file was generated on 2017-05-08 12:46:18.
 */
@Table(name = "`financial_transaction`")
public class FinancialTransaction {
    @Id
    @Column(name = "`id`")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 交易编号,如订单编号
     */
    @Column(name = "`transaction_number`")
    private String transactionNumber;

    /**
     * 交易类型(1:充值;2:转帐)
     */
    @Column(name = "`transaction_type`")
    private Integer transactionType;

    /**
     * 所属用户ID
     */
    @Column(name = "`user_id`")
    private Long userId;

    /**
     * 打款金额，单位分
     */
    @Column(name = "`transaction_amount`")
    private Long transactionAmount;

    /**
     * 交易当时账户的余额，单位分
     */
    @Column(name = "`balance`")
    private Long balance;

    /**
     * 打款时间
     */
    @Column(name = "`payment_time`")
    private Date paymentTime;

    /**
     * 支付方式: 1:支付宝;2:微信
     */
    @Column(name = "`payment_pattern`")
    private Integer paymentPattern;

    /**
     * 目标用户id
     */
    @Column(name = "`target_user_id`")
    private Long targetUserId;

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
     * 获取交易编号,如订单编号
     *
     * @return transaction_number - 交易编号,如订单编号
     */
    public String getTransactionNumber() {
        return transactionNumber;
    }

    /**
     * 设置交易编号,如订单编号
     *
     * @param transactionNumber 交易编号,如订单编号
     */
    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    /**
     * 获取交易类型(1:充值;2:转帐)
     *
     * @return transaction_type - 交易类型(1:充值;2:转帐)
     */
    public Integer getTransactionType() {
        return transactionType;
    }

    /**
     * 设置交易类型(1:充值;2:转帐)
     *
     * @param transactionType 交易类型(1:充值;2:转帐)
     */
    public void setTransactionType(Integer transactionType) {
        this.transactionType = transactionType;
    }

    /**
     * 获取所属用户ID
     *
     * @return user_id - 所属用户ID
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置所属用户ID
     *
     * @param userId 所属用户ID
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取打款金额，单位分
     *
     * @return transaction_amount - 打款金额，单位分
     */
    public Long getTransactionAmount() {
        return transactionAmount;
    }

    /**
     * 设置打款金额，单位分
     *
     * @param transactionAmount 打款金额，单位分
     */
    public void setTransactionAmount(Long transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    /**
     * 获取交易当时账户的余额，单位分
     *
     * @return balance - 交易当时账户的余额，单位分
     */
    public Long getBalance() {
        return balance;
    }

    /**
     * 设置交易当时账户的余额，单位分
     *
     * @param balance 交易当时账户的余额，单位分
     */
    public void setBalance(Long balance) {
        this.balance = balance;
    }

    /**
     * 获取打款时间
     *
     * @return payment_time - 打款时间
     */
    public Date getPaymentTime() {
        return paymentTime;
    }

    /**
     * 设置打款时间
     *
     * @param paymentTime 打款时间
     */
    public void setPaymentTime(Date paymentTime) {
        this.paymentTime = paymentTime;
    }

    /**
     * 获取支付方式: 1:支付宝;2:微信
     *
     * @return payment_pattern - 支付方式: 1:支付宝;2:微信
     */
    public Integer getPaymentPattern() {
        return paymentPattern;
    }

    /**
     * 设置支付方式: 1:支付宝;2:微信
     *
     * @param paymentPattern 支付方式: 1:支付宝;2:微信
     */
    public void setPaymentPattern(Integer paymentPattern) {
        this.paymentPattern = paymentPattern;
    }

    /**
     * 获取目标用户id
     *
     * @return target_user_id - 目标用户id
     */
    public Long getTargetUserId() {
        return targetUserId;
    }

    /**
     * 设置目标用户id
     *
     * @param targetUserId 目标用户id
     */
    public void setTargetUserId(Long targetUserId) {
        this.targetUserId = targetUserId;
    }
}