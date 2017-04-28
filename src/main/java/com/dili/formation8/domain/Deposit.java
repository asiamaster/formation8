package com.dili.formation8.domain;

import java.util.Date;
import javax.persistence.*;

/**
 * 由MyBatis Generator工具自动生成
 * 
 * This file was generated on 2017-04-28 21:44:28.
 */
public class Deposit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    /**
     * 充值编号
     */
    @Column(name = "deposite_number")
    private String depositeNumber;

    /**
     * 1:待付款,2:付款成功;3:付款失败
     */
    private Integer status;

    /**
     * 充值金额
     */
    private Long price;

    /**
     * 充值时间
     */
    @Column(name = "deposite_time")
    private Date depositeTime;

    /**
     * 到帐时间
     */
    @Column(name = "received_time")
    private Date receivedTime;

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
     * @return user_id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取充值编号
     *
     * @return deposite_number - 充值编号
     */
    public String getDepositeNumber() {
        return depositeNumber;
    }

    /**
     * 设置充值编号
     *
     * @param depositeNumber 充值编号
     */
    public void setDepositeNumber(String depositeNumber) {
        this.depositeNumber = depositeNumber;
    }

    /**
     * 获取1:待付款,2:付款成功;3:付款失败
     *
     * @return status - 1:待付款,2:付款成功;3:付款失败
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置1:待付款,2:付款成功;3:付款失败
     *
     * @param status 1:待付款,2:付款成功;3:付款失败
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取充值金额
     *
     * @return price - 充值金额
     */
    public Long getPrice() {
        return price;
    }

    /**
     * 设置充值金额
     *
     * @param price 充值金额
     */
    public void setPrice(Long price) {
        this.price = price;
    }

    /**
     * 获取充值时间
     *
     * @return deposite_time - 充值时间
     */
    public Date getDepositeTime() {
        return depositeTime;
    }

    /**
     * 设置充值时间
     *
     * @param depositeTime 充值时间
     */
    public void setDepositeTime(Date depositeTime) {
        this.depositeTime = depositeTime;
    }

    /**
     * 获取到帐时间
     *
     * @return received_time - 到帐时间
     */
    public Date getReceivedTime() {
        return receivedTime;
    }

    /**
     * 设置到帐时间
     *
     * @param receivedTime 到帐时间
     */
    public void setReceivedTime(Date receivedTime) {
        this.receivedTime = receivedTime;
    }
}