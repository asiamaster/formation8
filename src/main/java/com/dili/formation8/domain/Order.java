package com.dili.formation8.domain;

import java.util.Date;
import javax.persistence.*;

/**
 * 由MyBatis Generator工具自动生成
 * 
 * This file was generated on 2017-05-12 21:28:28.
 */
@Table(name = "`order`")
public class Order {
    @Id
    @Column(name = "`id`")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "`user_id`")
    private Long userId;

    @Column(name = "`sku_id`")
    private Long skuId;

    /**
     * 产品id，冗余数据，便于查询
     */
    @Column(name = "`product_id`")
    private Long productId;

    /**
     * 购买数量
     */
    @Column(name = "`count`")
    private Integer count;

    /**
     * 订单编号
     */
    @Column(name = "`order_number`")
    private String orderNumber;

    /**
     * 1:众筹中;2:众筹成功;3众筹失败
     */
    @Column(name = "`status`")
    private Integer status;

    /**
     * 投资金额
     */
    @Column(name = "`price`")
    private Long price;

    /**
     * 1:钱;2:产品;  众筹成功后可以选产品
     */
    @Column(name = "`transaction_type`")
    private Integer transactionType;

    /**
     * 下单时间
     */
    @Column(name = "`start_time`")
    private Date startTime;

    /**
     * 结束时间
     */
    @Column(name = "`end_time`")
    private Date endTime;

    /**
     * 用户提款时间
     */
    @Column(name = "`withdraw_time`")
    private Date withdrawTime;

    /**
     * 完成时间
     */
    @Column(name = "`finish_time`")
    private Date finishTime;

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
     * @return sku_id
     */
    public Long getSkuId() {
        return skuId;
    }

    /**
     * @param skuId
     */
    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    /**
     * 获取产品id，冗余数据，便于查询
     *
     * @return product_id - 产品id，冗余数据，便于查询
     */
    public Long getProductId() {
        return productId;
    }

    /**
     * 设置产品id，冗余数据，便于查询
     *
     * @param productId 产品id，冗余数据，便于查询
     */
    public void setProductId(Long productId) {
        this.productId = productId;
    }

    /**
     * 获取购买数量
     *
     * @return count - 购买数量
     */
    public Integer getCount() {
        return count;
    }

    /**
     * 设置购买数量
     *
     * @param count 购买数量
     */
    public void setCount(Integer count) {
        this.count = count;
    }

    /**
     * 获取订单编号
     *
     * @return order_number - 订单编号
     */
    public String getOrderNumber() {
        return orderNumber;
    }

    /**
     * 设置订单编号
     *
     * @param orderNumber 订单编号
     */
    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    /**
     * 获取1:众筹中;2:众筹成功;3众筹失败
     *
     * @return status - 1:众筹中;2:众筹成功;3众筹失败
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置1:众筹中;2:众筹成功;3众筹失败
     *
     * @param status 1:众筹中;2:众筹成功;3众筹失败
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取投资金额
     *
     * @return price - 投资金额
     */
    public Long getPrice() {
        return price;
    }

    /**
     * 设置投资金额
     *
     * @param price 投资金额
     */
    public void setPrice(Long price) {
        this.price = price;
    }

    /**
     * 获取1:钱;2:产品;  众筹成功后可以选产品
     *
     * @return transaction_type - 1:钱;2:产品;  众筹成功后可以选产品
     */
    public Integer getTransactionType() {
        return transactionType;
    }

    /**
     * 设置1:钱;2:产品;  众筹成功后可以选产品
     *
     * @param transactionType 1:钱;2:产品;  众筹成功后可以选产品
     */
    public void setTransactionType(Integer transactionType) {
        this.transactionType = transactionType;
    }

    /**
     * 获取下单时间
     *
     * @return start_time - 下单时间
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * 设置下单时间
     *
     * @param startTime 下单时间
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * 获取结束时间
     *
     * @return end_time - 结束时间
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * 设置结束时间
     *
     * @param endTime 结束时间
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * 获取用户提款时间
     *
     * @return withdraw_time - 用户提款时间
     */
    public Date getWithdrawTime() {
        return withdrawTime;
    }

    /**
     * 设置用户提款时间
     *
     * @param withdrawTime 用户提款时间
     */
    public void setWithdrawTime(Date withdrawTime) {
        this.withdrawTime = withdrawTime;
    }

    /**
     * 获取完成时间
     *
     * @return finish_time - 完成时间
     */
    public Date getFinishTime() {
        return finishTime;
    }

    /**
     * 设置完成时间
     *
     * @param finishTime 完成时间
     */
    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }
}