package com.dili.formation8.utils;

import com.dili.formation8.vo.UserData;

import java.io.UnsupportedEncodingException;
import java.util.Date;

public class UserDataTransformer {

    private UserData userData;

    public UserDataTransformer(UserData userData) {
        if(userData == null){
            throw new IllegalArgumentException();
        }
        this.userData = userData;
    }
    
    public byte[] getUserId(){
        return Utils.longToByteArray(this.userData.getUid());
    }
    
    public byte getVersion(){
        return (byte)this.userData.getVersion();
    }
    
    public byte[] getUserName() throws UnsupportedEncodingException{
        return this.userData.getUserName().getBytes(Formation8Constants.USER_DATA_ENCODING);
    }

    public byte[] getExpires(){
        return Utils.DateToByteArray(new Date(System.currentTimeMillis() + Formation8Constants.EXPIRED_TIME));
    }
    
    public byte getUserType(){
        return (byte)this.userData.getUserType();
    }
    
    public byte[] getReferer() {
        return Utils.longToByteArray(this.userData.getReferer());
    }
    
    public byte[] getReferralCode() throws UnsupportedEncodingException{
        return this.userData.getReferralCode() == null ? null : this.userData.getReferralCode().getBytes(Formation8Constants.USER_DATA_ENCODING);
    }

}
