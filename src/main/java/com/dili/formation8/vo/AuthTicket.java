package com.dili.formation8.vo;

import java.util.Date;

/**
 * Created by asiam on 2017/5/9 0009.
 */
public class AuthTicket {
    private static final ThreadLocal<AuthTicket> holder = new ThreadLocal<AuthTicket>();

    private Integer _version = 0;

    private Long _uid = 0L;

    private String _userName;

    private Date _expires;

    private Integer _userType = 0;

    private Long _referer = 0l;

    private String _referralCode;

    private String cookieValue;

    @Override
    public String toString() {
        return "AuthTicket [_version=" + _version + ", _uid=" + _uid + ", _userName=" + _userName +  ", _expires=" + _expires + ", _userType=" + _userType + ", _referer="
                + _referer+ ", _referralCode=" + _referralCode + ", cookieValue=" + cookieValue + "]";
    }

    public AuthTicket(Integer _version, Long _uid, String _userName, Date _expires, Integer _userType, Long _referer, String _referralCode) {
        this._version = _version;
        this._uid = _uid;
        this._userName = _userName;
        this._expires = _expires;
        this._userType = _userType;
        this._referer = _referer;
        this._referralCode = _referralCode;
    }

    public int hashCode() {
        int result = this._version;
        result = 31 * result + (this._userName != null ? this._userName.hashCode() : 0);
        result = 31 * result + (this._referer != null ? this._referer.hashCode() : 0);
        result = 31 * result + (this._referralCode != null ? this._referralCode.hashCode() : 0);
        result = 31 * result + (this._expires != null ? this._expires.hashCode() : 0);
        result = 31 * result + (this._userType != null ? this._userType.hashCode() : 0);
        return result;
    }

    public static void setTicket(AuthTicket ticket) {
        holder.set(ticket);
    }

    public static AuthTicket getTicket() {
        return (AuthTicket) holder.get();
    }

    public static void remove() {
        holder.remove();
    }

    public Integer get_version() {
        return _version;
    }

    public void set_version(Integer _version) {
        this._version = _version;
    }

    public Long get_uid() {
        return _uid;
    }

    public void set_uid(Long _uid) {
        this._uid = _uid;
    }

    public String get_userName() {
        return _userName;
    }

    public void set_userName(String _userName) {
        this._userName = _userName;
    }

    public Date get_expires() {
        return _expires;
    }

    public void set_expires(Date _expires) {
        this._expires = _expires;
    }

    public Integer get_userType() {
        return _userType;
    }

    public void set_userType(Integer _userType) {
        this._userType = _userType;
    }

    public Long get_referer() {
        return _referer;
    }

    public void set_referer(Long _referer) {
        this._referer = _referer;
    }

    public String get_referralCode() {
        return _referralCode;
    }

    public void set_referralCode(String _referralCode) {
        this._referralCode = _referralCode;
    }

    public String getCookieValue() {
        return cookieValue;
    }

    public void setCookieValue(String cookieValue) {
        this.cookieValue = cookieValue;
    }




}
