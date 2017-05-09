package com.dili.formation8.domain;

import java.util.Date;
import javax.persistence.*;

/**
 * 由MyBatis Generator工具自动生成
 * 
 * This file was generated on 2017-05-08 12:46:19.
 */
@Table(name = "`bank_card`")
public class BankCard {
    @Id
    @Column(name = "`id`")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户ID
     */
    @Column(name = "`user_id`")
    private Long userId;

    /**
     * 默认选中储蓄卡，目前仅“储蓄卡”一个项
     */
    @Column(name = "`card_type`")
    private Integer cardType;

    /**
     * 法人银行账户：显示认证时填写的法人姓名，企业
     */
    @Column(name = "`account_name`")
    private String accountName;

    /**
     * 法人银行账户：显示认证时填写的法人身份证号，
     */
    @Column(name = "`id_number`")
    private String idNumber;

    /**
     * 选填，允许5-50位汉字、字母和数字，不允许特殊
     */
    @Column(name = "`subbranch`")
    private String subbranch;

    /**
     * 允许填写8-30位纯数字
     */
    @Column(name = "`card_number`")
    private String cardNumber;

    /**
     * 开户行:中国银行，建设银行，招商银行
     */
    @Column(name = "`bank`")
    private String bank;

    /**
     * 1:是, 0：否
     */
    @Column(name = "`is_default`")
    private Integer isDefault;

    @Column(name = "`add_time`")
    private Date addTime;

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
     * 获取用户ID
     *
     * @return user_id - 用户ID
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置用户ID
     *
     * @param userId 用户ID
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取默认选中储蓄卡，目前仅“储蓄卡”一个项
     *
     * @return card_type - 默认选中储蓄卡，目前仅“储蓄卡”一个项
     */
    public Integer getCardType() {
        return cardType;
    }

    /**
     * 设置默认选中储蓄卡，目前仅“储蓄卡”一个项
     *
     * @param cardType 默认选中储蓄卡，目前仅“储蓄卡”一个项
     */
    public void setCardType(Integer cardType) {
        this.cardType = cardType;
    }

    /**
     * 获取法人银行账户：显示认证时填写的法人姓名，企业
     *
     * @return account_name - 法人银行账户：显示认证时填写的法人姓名，企业
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * 设置法人银行账户：显示认证时填写的法人姓名，企业
     *
     * @param accountName 法人银行账户：显示认证时填写的法人姓名，企业
     */
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    /**
     * 获取法人银行账户：显示认证时填写的法人身份证号，
     *
     * @return id_number - 法人银行账户：显示认证时填写的法人身份证号，
     */
    public String getIdNumber() {
        return idNumber;
    }

    /**
     * 设置法人银行账户：显示认证时填写的法人身份证号，
     *
     * @param idNumber 法人银行账户：显示认证时填写的法人身份证号，
     */
    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    /**
     * 获取选填，允许5-50位汉字、字母和数字，不允许特殊
     *
     * @return subbranch - 选填，允许5-50位汉字、字母和数字，不允许特殊
     */
    public String getSubbranch() {
        return subbranch;
    }

    /**
     * 设置选填，允许5-50位汉字、字母和数字，不允许特殊
     *
     * @param subbranch 选填，允许5-50位汉字、字母和数字，不允许特殊
     */
    public void setSubbranch(String subbranch) {
        this.subbranch = subbranch;
    }

    /**
     * 获取允许填写8-30位纯数字
     *
     * @return card_number - 允许填写8-30位纯数字
     */
    public String getCardNumber() {
        return cardNumber;
    }

    /**
     * 设置允许填写8-30位纯数字
     *
     * @param cardNumber 允许填写8-30位纯数字
     */
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    /**
     * 获取开户行:中国银行，建设银行，招商银行
     *
     * @return bank - 开户行:中国银行，建设银行，招商银行
     */
    public String getBank() {
        return bank;
    }

    /**
     * 设置开户行:中国银行，建设银行，招商银行
     *
     * @param bank 开户行:中国银行，建设银行，招商银行
     */
    public void setBank(String bank) {
        this.bank = bank;
    }

    /**
     * 获取1:是, 0：否
     *
     * @return is_default - 1:是, 0：否
     */
    public Integer getIsDefault() {
        return isDefault;
    }

    /**
     * 设置1:是, 0：否
     *
     * @param isDefault 1:是, 0：否
     */
    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
    }

    /**
     * @return add_time
     */
    public Date getAddTime() {
        return addTime;
    }

    /**
     * @param addTime
     */
    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }
}