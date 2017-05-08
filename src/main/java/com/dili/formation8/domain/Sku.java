package com.dili.formation8.domain;

import java.util.Date;
import javax.persistence.*;

/**
 * 由MyBatis Generator工具自动生成
 * 
 * This file was generated on 2017-05-08 12:46:19.
 */
@Table(name = "`sku`")
public class Sku {
    @Id
    @Column(name = "`id`")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 商品id
     */
    @Column(name = "`product_id`")
    private Long productId;

    /**
     * 详情
     */
    @Column(name = "`details`")
    private String details;

    /**
     * 价格
     */
    @Column(name = "`price`")
    private Long price;

    /**
     * 修改产品时更新
     */
    @Column(name = "`modify_time`")
    private Date modifyTime;

    /**
     * 0:删除,,1:可用
     */
    @Column(name = "`yn`")
    private Integer yn;

    /**
     * 最大购买份数(限额)
     */
    @Column(name = "`quota`")
    private Integer quota;

    /**
     * 当前已购买总份数
     */
    @Column(name = "`quantity`")
    private Integer quantity;

    /**
     * 每日限额xxx份
     */
    @Column(name = "`daily_quota`")
    private Integer dailyQuota;

    /**
     * 每日购买xxx份
     */
    @Column(name = "`daily_quantity`")
    private Integer dailyQuantity;

    /**
     * 当前购买日期串,用于计算每日购买份数,如:2017-05-05
     */
    @Column(name = "`current_date_str`")
    private String currentDateStr;

    /**
     * 配送费用
     */
    @Column(name = "`freight`")
    private Long freight;

    /**
     * 只有一张图,从数据字典值表的code取，字典编码为IMAGE_CODE
     */
    @Column(name = "`image_code`")
    private String imageCode;

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
     * 获取商品id
     *
     * @return product_id - 商品id
     */
    public Long getProductId() {
        return productId;
    }

    /**
     * 设置商品id
     *
     * @param productId 商品id
     */
    public void setProductId(Long productId) {
        this.productId = productId;
    }

    /**
     * 获取详情
     *
     * @return details - 详情
     */
    public String getDetails() {
        return details;
    }

    /**
     * 设置详情
     *
     * @param details 详情
     */
    public void setDetails(String details) {
        this.details = details;
    }

    /**
     * 获取价格
     *
     * @return price - 价格
     */
    public Long getPrice() {
        return price;
    }

    /**
     * 设置价格
     *
     * @param price 价格
     */
    public void setPrice(Long price) {
        this.price = price;
    }

    /**
     * 获取修改产品时更新
     *
     * @return modify_time - 修改产品时更新
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * 设置修改产品时更新
     *
     * @param modifyTime 修改产品时更新
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * 获取0:删除,,1:可用
     *
     * @return yn - 0:删除,,1:可用
     */
    public Integer getYn() {
        return yn;
    }

    /**
     * 设置0:删除,,1:可用
     *
     * @param yn 0:删除,,1:可用
     */
    public void setYn(Integer yn) {
        this.yn = yn;
    }

    /**
     * 获取最大购买份数(限额)
     *
     * @return quota - 最大购买份数(限额)
     */
    public Integer getQuota() {
        return quota;
    }

    /**
     * 设置最大购买份数(限额)
     *
     * @param quota 最大购买份数(限额)
     */
    public void setQuota(Integer quota) {
        this.quota = quota;
    }

    /**
     * 获取当前已购买总份数
     *
     * @return quantity - 当前已购买总份数
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * 设置当前已购买总份数
     *
     * @param quantity 当前已购买总份数
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     * 获取每日限额xxx份
     *
     * @return daily_quota - 每日限额xxx份
     */
    public Integer getDailyQuota() {
        return dailyQuota;
    }

    /**
     * 设置每日限额xxx份
     *
     * @param dailyQuota 每日限额xxx份
     */
    public void setDailyQuota(Integer dailyQuota) {
        this.dailyQuota = dailyQuota;
    }

    /**
     * 获取每日购买xxx份
     *
     * @return daily_quantity - 每日购买xxx份
     */
    public Integer getDailyQuantity() {
        return dailyQuantity;
    }

    /**
     * 设置每日购买xxx份
     *
     * @param dailyQuantity 每日购买xxx份
     */
    public void setDailyQuantity(Integer dailyQuantity) {
        this.dailyQuantity = dailyQuantity;
    }

    /**
     * 获取当前购买日期串,用于计算每日购买份数,如:2017-05-05
     *
     * @return current_date_str - 当前购买日期串,用于计算每日购买份数,如:2017-05-05
     */
    public String getCurrentDateStr() {
        return currentDateStr;
    }

    /**
     * 设置当前购买日期串,用于计算每日购买份数,如:2017-05-05
     *
     * @param currentDateStr 当前购买日期串,用于计算每日购买份数,如:2017-05-05
     */
    public void setCurrentDateStr(String currentDateStr) {
        this.currentDateStr = currentDateStr;
    }

    /**
     * 获取配送费用
     *
     * @return freight - 配送费用
     */
    public Long getFreight() {
        return freight;
    }

    /**
     * 设置配送费用
     *
     * @param freight 配送费用
     */
    public void setFreight(Long freight) {
        this.freight = freight;
    }

    /**
     * 获取只有一张图,从数据字典值表的code取，字典编码为IMAGE_CODE
     *
     * @return image_code - 只有一张图,从数据字典值表的code取，字典编码为IMAGE_CODE
     */
    public String getImageCode() {
        return imageCode;
    }

    /**
     * 设置只有一张图,从数据字典值表的code取，字典编码为IMAGE_CODE
     *
     * @param imageCode 只有一张图,从数据字典值表的code取，字典编码为IMAGE_CODE
     */
    public void setImageCode(String imageCode) {
        this.imageCode = imageCode;
    }
}