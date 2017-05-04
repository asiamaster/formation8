package com.dili.formation8.service;

import com.dili.formation8.domain.BizNumber;
import com.dili.utils.base.BaseService;

/**
 * 由MyBatis Generator工具自动生成
 * This file was generated on 2017-04-28 10:10:06.
 */
public interface BizNumberService extends BaseService<BizNumber, Long> {

    /**
     * 获取充值订单号
     * @return
     */
    public String getDepositOrderCode();

    /**
     * 获取产品订单号
     * @return
     */
    public String getProductOrderCode();

    /**
     * 获取转帐订单号
     * @return
     */
    public String getTransferOrderCode();

}