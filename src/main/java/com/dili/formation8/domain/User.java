package com.dili.formation8.domain;

import javax.persistence.*;

/**
 * 由MyBatis Generator工具自动生成
 * 
 * This file was generated on 2017-05-08 12:46:19.
 */
@Table(name = "`user`")
public class User {
    @Id
    @Column(name = "`id`")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户名
     */
    @Column(name = "`name`")
    private String name;

    /**
     * 密码
     */
    @Column(name = "`password`")
    private String password;

    /**
     * 支付密码
     */
    @Column(name = "`payment_code`")
    private String paymentCode;

    @Column(name = "`referrer`")
    private Long referrer;

    /**
     * 可用余额,单位分
     */
    @Column(name = "`balance`")
    private Long balance;

    /**
     * 邮箱
     */
    @Column(name = "`email`")
    private String email;

    @Column(name = "`phone`")
    private String phone;

    /**
     * 1:普通用户,2:平台当前现金,3:平台抽成,4:股东,5:机器人
     */
    @Column(name = "`type`")
    private Integer type;

    /**
     * 推荐码,邀请码
     */
    @Column(name = "`referral_code`")
    private String referralCode;

    /**
     * 收货地址
     */
    @Column(name = "`address`")
    private String address;

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
     * 获取用户名
     *
     * @return name - 用户名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置用户名
     *
     * @param name 用户名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取密码
     *
     * @return password - 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     *
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取支付密码
     *
     * @return payment_code - 支付密码
     */
    public String getPaymentCode() {
        return paymentCode;
    }

    /**
     * 设置支付密码
     *
     * @param paymentCode 支付密码
     */
    public void setPaymentCode(String paymentCode) {
        this.paymentCode = paymentCode;
    }

    /**
     * @return referrer
     */
    public Long getReferrer() {
        return referrer;
    }

    /**
     * @param referrer
     */
    public void setReferrer(Long referrer) {
        this.referrer = referrer;
    }

    /**
     * 获取可用余额,单位分
     *
     * @return balance - 可用余额,单位分
     */
    public Long getBalance() {
        return balance;
    }

    /**
     * 设置可用余额,单位分
     *
     * @param balance 可用余额,单位分
     */
    public void setBalance(Long balance) {
        this.balance = balance;
    }

    /**
     * 获取邮箱
     *
     * @return email - 邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置邮箱
     *
     * @param email 邮箱
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取1:普通用户,2:平台当前现金,3:平台抽成,4:股东,5:机器人
     *
     * @return type - 1:普通用户,2:平台当前现金,3:平台抽成,4:股东,5:机器人
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置1:普通用户,2:平台当前现金,3:平台抽成,4:股东,5:机器人
     *
     * @param type 1:普通用户,2:平台当前现金,3:平台抽成,4:股东,5:机器人
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取推荐码,邀请码
     *
     * @return referral_code - 推荐码,邀请码
     */
    public String getReferralCode() {
        return referralCode;
    }

    /**
     * 设置推荐码,邀请码
     *
     * @param referralCode 推荐码,邀请码
     */
    public void setReferralCode(String referralCode) {
        this.referralCode = referralCode;
    }

    /**
     * 获取收货地址
     *
     * @return address - 收货地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置收货地址
     *
     * @param address 收货地址
     */
    public void setAddress(String address) {
        this.address = address;
    }
}