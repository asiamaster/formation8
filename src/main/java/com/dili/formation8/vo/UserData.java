package com.dili.formation8.vo;

import java.util.Date;

/**
 * Created by asiam on 2017/5/9 0009.
 */
public class UserData {
    private int version;

    private long uid;

    private String userName;

    private Date expires;

    private int userType;

    private long referer;

    private String referralCode;

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getExpires() {
        return expires;
    }

    public void setExpires(Date expires) {
        this.expires = expires;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public long getReferer() {
        return referer;
    }

    public void setReferer(long referer) {
        this.referer = referer;
    }

    public String getReferralCode() {
        return referralCode;
    }

    public void setReferralCode(String referralCode) {
        this.referralCode = referralCode;
    }

}
