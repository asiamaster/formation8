package com.dili.formation8.vo;

import com.dili.formation8.domain.User;

/**
 * Created by asiam on 2017/5/4 0004.
 */
public class UserVo extends User {
    //转帐金额(单位分)，正为加，负为减
    private Long transferAmount;

    public Long getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(Long transferAmount) {
        this.transferAmount = transferAmount;
    }
}
