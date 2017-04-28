package com.dili.formation8.domain;

import javax.persistence.*;

/**
 * 由MyBatis Generator工具自动生成
 * 
 * This file was generated on 2017-04-27 10:56:44.
 */
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String password;

    private Long referrer;

    private Integer 是否已投;

    private Integer 是否平台用户;

    /**
     * 可用余额,单位分
     */
    private Long balance;

    /**
     * 邮箱
     */
    private String email;

    private String phone;

    /**
     * 1:普通用户,2:平台当前现金,3:平台抽成,4:股东,5:机器人
     */
    private Integer type;

    /**
     * 推荐码,邀请码
     */
    @Column(name = "referral_code")
    private String referralCode;

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
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
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
     * @return 是否已投
     */
    public Integer get是否已投() {
        return 是否已投;
    }

    /**
     * @param 是否已投
     */
    public void set是否已投(Integer 是否已投) {
        this.是否已投 = 是否已投;
    }

    /**
     * @return 是否平台用户
     */
    public Integer get是否平台用户() {
        return 是否平台用户;
    }

    /**
     * @param 是否平台用户
     */
    public void set是否平台用户(Integer 是否平台用户) {
        this.是否平台用户 = 是否平台用户;
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
}