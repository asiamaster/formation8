package com.dili.formation8.domain;

import java.util.Date;
import javax.persistence.*;

/**
 * 由MyBatis Generator工具自动生成
 * 
 * This file was generated on 2017-05-08 12:46:19.
 */
@Table(name = "`product`")
public class Product {
    @Id
    @Column(name = "`id`")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 名称
     */
    @Column(name = "`name`")
    private String name;

    /**
     * 从数据字典值表的code取，字典编码为IMAGE_CODE
     */
    @Column(name = "`image`")
    private String image;

    /**
     * 详情,富文本内容
     */
    @Column(name = "`details`")
    private String details;

    /**
     * 文字说明，如:食品，电器，医疗等
     */
    @Column(name = "`type`")
    private String type;

    /**
     * 简单处理，发布时间就是创建时间
     */
    @Column(name = "`publish_time`")
    private Date publishTime;

    /**
     * 众筹成功的截止时间
     */
    @Column(name = "`cutoff_time`")
    private Date cutoffTime;

    /**
     * 众筹成功要求的金额
     */
    @Column(name = "`success_amount`")
    private Long successAmount;

    /**
     * 已筹金额
     */
    @Column(name = "`current_amount`")
    private Long currentAmount;

    /**
     * 下架时间，下架时更新
     */
    @Column(name = "`drops_time`")
    private Date dropsTime;

    /**
     * 1:上架,2:过期下架,3:手动下架
     */
    @Column(name = "`status`")
    private Integer status;

    /**
     * 项目发起人
     */
    @Column(name = "`seller_name`")
    private String sellerName;

    /**
     * 企业类型
     */
    @Column(name = "`company_desc`")
    private String companyDesc;

    /**
     * 赔偿费率百分比:如7天赔偿3%
     */
    @Column(name = "`refund_rate`")
    private Integer refundRate;

    /**
     * 平台抽成比例百分比:如3即这3%
     */
    @Column(name = "`commission_rate`")
    private Integer commissionRate;

    /**
     * 修改产品时更新
     */
    @Column(name = "`modify_time`")
    private Date modifyTime;

    /**
     * 0:删除,1:可用
     */
    @Column(name = "`yn`")
    private Integer yn;

    /**
     * 周期(天)
     */
    private Integer period;

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
     * 获取名称
     *
     * @return name - 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置名称
     *
     * @param name 名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取从数据字典值表的code取，字典编码为IMAGE_CODE
     *
     * @return image - 从数据字典值表的code取，字典编码为IMAGE_CODE
     */
    public String getImage() {
        return image;
    }

    /**
     * 设置从数据字典值表的code取，字典编码为IMAGE_CODE
     *
     * @param image 从数据字典值表的code取，字典编码为IMAGE_CODE
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * 获取详情,富文本内容
     *
     * @return details - 详情,富文本内容
     */
    public String getDetails() {
        return details;
    }

    /**
     * 设置详情,富文本内容
     *
     * @param details 详情,富文本内容
     */
    public void setDetails(String details) {
        this.details = details;
    }

    /**
     * 获取文字说明，如:食品，电器，医疗等
     *
     * @return type - 文字说明，如:食品，电器，医疗等
     */
    public String getType() {
        return type;
    }

    /**
     * 设置文字说明，如:食品，电器，医疗等
     *
     * @param type 文字说明，如:食品，电器，医疗等
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取简单处理，发布时间就是创建时间
     *
     * @return publish_time - 简单处理，发布时间就是创建时间
     */
    public Date getPublishTime() {
        return publishTime;
    }

    /**
     * 设置简单处理，发布时间就是创建时间
     *
     * @param publishTime 简单处理，发布时间就是创建时间
     */
    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    /**
     * 获取众筹成功的截止时间
     *
     * @return cutoff_time - 众筹成功的截止时间
     */
    public Date getCutoffTime() {
        return cutoffTime;
    }

    /**
     * 设置众筹成功的截止时间
     *
     * @param cutoffTime 众筹成功的截止时间
     */
    public void setCutoffTime(Date cutoffTime) {
        this.cutoffTime = cutoffTime;
    }

    /**
     * 获取众筹成功要求的金额
     *
     * @return success_amount - 众筹成功要求的金额
     */
    public Long getSuccessAmount() {
        return successAmount;
    }

    /**
     * 设置众筹成功要求的金额
     *
     * @param successAmount 众筹成功要求的金额
     */
    public void setSuccessAmount(Long successAmount) {
        this.successAmount = successAmount;
    }

    /**
     * 获取已筹金额
     *
     * @return current_amount - 已筹金额
     */
    public Long getCurrentAmount() {
        return currentAmount;
    }

    /**
     * 设置已筹金额
     *
     * @param currentAmount 已筹金额
     */
    public void setCurrentAmount(Long currentAmount) {
        this.currentAmount = currentAmount;
    }

    /**
     * 获取下架时间，下架时更新
     *
     * @return drops_time - 下架时间，下架时更新
     */
    public Date getDropsTime() {
        return dropsTime;
    }

    /**
     * 设置下架时间，下架时更新
     *
     * @param dropsTime 下架时间，下架时更新
     */
    public void setDropsTime(Date dropsTime) {
        this.dropsTime = dropsTime;
    }

    /**
     * 获取1:上架,2:过期下架,3:手动下架
     *
     * @return status - 1:上架,2:过期下架,3:手动下架
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置1:上架,2:过期下架,3:手动下架
     *
     * @param status 1:上架,2:过期下架,3:手动下架
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取项目发起人
     *
     * @return seller_name - 项目发起人
     */
    public String getSellerName() {
        return sellerName;
    }

    /**
     * 设置项目发起人
     *
     * @param sellerName 项目发起人
     */
    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    /**
     * 获取企业类型
     *
     * @return company_desc - 企业类型
     */
    public String getCompanyDesc() {
        return companyDesc;
    }

    /**
     * 设置企业类型
     *
     * @param companyDesc 企业类型
     */
    public void setCompanyDesc(String companyDesc) {
        this.companyDesc = companyDesc;
    }

    /**
     * 获取赔偿费率百分比:如7天赔偿3%
     *
     * @return refund_rate - 赔偿费率百分比:如7天赔偿3%
     */
    public Integer getRefundRate() {
        return refundRate;
    }

    /**
     * 设置赔偿费率百分比:如7天赔偿3%
     *
     * @param refundRate 赔偿费率百分比:如7天赔偿3%
     */
    public void setRefundRate(Integer refundRate) {
        this.refundRate = refundRate;
    }

    /**
     * 获取平台抽成比例百分比:如3即这3%
     *
     * @return commission_rate - 平台抽成比例百分比:如3即这3%
     */
    public Integer getCommissionRate() {
        return commissionRate;
    }

    /**
     * 设置平台抽成比例百分比:如3即这3%
     *
     * @param commissionRate 平台抽成比例百分比:如3即这3%
     */
    public void setCommissionRate(Integer commissionRate) {
        this.commissionRate = commissionRate;
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
     * 获取0:删除,1:可用
     *
     * @return yn - 0:删除,1:可用
     */
    public Integer getYn() {
        return yn;
    }

    /**
     * 设置0:删除,1:可用
     *
     * @param yn 0:删除,1:可用
     */
    public void setYn(Integer yn) {
        this.yn = yn;
    }

    /**
     * 获取周期(天)
     * @return 周期(天)
     */
    public Integer getPeriod() {
        return period;
    }

    /**
     * 设置周期(天)
     * @param period 周期(天)
     */
    public void setPeriod(Integer period) {
        this.period = period;
    }
}