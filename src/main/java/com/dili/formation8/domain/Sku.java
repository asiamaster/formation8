package com.dili.formation8.domain;

import java.util.Date;
import javax.persistence.*;

/**
 * 由MyBatis Generator工具自动生成
 * 
 * This file was generated on 2017-04-27 10:56:44.
 */
public class Sku {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 商品id
     */
    @Column(name = "product_id")
    private Long productId;

    /**
     * 详情
     */
    private String details;

    /**
     * 价格
     */
    private Date price;

    /**
     * 修改产品时更新
     */
    @Column(name = "modify_time")
    private Date modifyTime;

    /**
     * 0:删除,,1:可用
     */
    private Integer yn;

    /**
     * 最大购买份数(限额)
     */
    private Integer quota;

    /**
     * 配送费用
     */
    private Long freight;

    /**
     * 只有一张图,从数据字典值表的code取，字典编码为IMAGE_CODE
     */
    @Column(name = "image_code")
    private String imageCode;

    /**
     * 当前已购买份数
     */
    private Integer quantity;

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
    public Date getPrice() {
        return price;
    }

    /**
     * 设置价格
     *
     * @param price 价格
     */
    public void setPrice(Date price) {
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

    /**
     * 获取当前已购买份数
     *
     * @return quantity - 当前已购买份数
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * 设置当前已购买份数
     *
     * @param quantity 当前已购买份数
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}