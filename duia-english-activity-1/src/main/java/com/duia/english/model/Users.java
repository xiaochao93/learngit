package com.duia.english.model;


import com.duia.english.common.core.CoreEntity;

import java.util.Date;

/**
 * 用户
 * Created by chenqi on 2018/9/20.
 */
public class Users extends CoreEntity {

    /**
     * 用户明
     */
    private String username;

    /**
     * 用户头像
     */
    private String picUrl;

    /**
     * 头像
     **/
    private String picUrlMid;

    /**
     * 头像
     **/
    private String picUrlMin;
    /**
     * 用户手机号
     */
    private String mobile;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 注册时间
     */
    private Date registTime;

    /**
     * 用户vip标记（目前该字段无法代表是否是vip）
     */
    private Integer vip;

    private String referrer;

    private String uuid;

    private String keyPromotion;

    private String mPromotion;

    private String sourcePromotion;

    private String userAddress;

    private Integer usertype;

    private Integer loginType;

    /**
     * 是否有效
     **/
    private Integer state;

    private Integer verifyStatus;

    private Integer mobileStatus;
    /**
     * 渠道
     **/
    private Long registerChannel;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getVip() {
        return vip;
    }

    public void setVip(Integer vip) {
        this.vip = vip;
    }

    public Integer getMobileStatus() {
        return mobileStatus;
    }

    public void setMobileStatus(Integer mobileStatus) {
        this.mobileStatus = mobileStatus;
    }

    public Date getRegistTime() {
        return registTime;
    }

    public void setRegistTime(Date registTime) {
        this.registTime = registTime;
    }

    public String getPicUrlMid() {
        return picUrlMid;
    }

    public void setPicUrlMid(String picUrlMid) {
        this.picUrlMid = picUrlMid;
    }

    public String getPicUrlMin() {
        return picUrlMin;
    }

    public void setPicUrlMin(String picUrlMin) {
        this.picUrlMin = picUrlMin;
    }

    public String getReferrer() {
        return referrer;
    }

    public void setReferrer(String referrer) {
        this.referrer = referrer;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getKeyPromotion() {
        return keyPromotion;
    }

    public void setKeyPromotion(String keyPromotion) {
        this.keyPromotion = keyPromotion;
    }

    public String getmPromotion() {
        return mPromotion;
    }

    public void setmPromotion(String mPromotion) {
        this.mPromotion = mPromotion;
    }

    public String getSourcePromotion() {
        return sourcePromotion;
    }

    public void setSourcePromotion(String sourcePromotion) {
        this.sourcePromotion = sourcePromotion;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public Integer getUsertype() {
        return usertype;
    }

    public void setUsertype(Integer usertype) {
        this.usertype = usertype;
    }

    public Integer getLoginType() {
        return loginType;
    }

    public void setLoginType(Integer loginType) {
        this.loginType = loginType;
    }

    public Users(String username, String picUrl, String picUrlMid, String picUrlMin, String mobile, String email,
                 String password, Date registTime, Integer vip, String referrer, String uuid, String keyPromotion,
                 String mPromotion, String sourcePromotion, String userAddress, Integer usertype, Integer loginType, Long registerChannel) {
        this.username = username;
        this.picUrl = picUrl;
        this.picUrlMid = picUrlMid;
        this.picUrlMin = picUrlMin;
        this.mobile = mobile;
        this.email = email;
        this.password = password;
        this.registTime = registTime;
        this.vip = vip;
        this.referrer = referrer;
        this.uuid = uuid;
        this.keyPromotion = keyPromotion;
        this.mPromotion = mPromotion;
        this.sourcePromotion = sourcePromotion;
        this.userAddress = userAddress;
        this.usertype = usertype;
        this.loginType = loginType;
        this.registerChannel = registerChannel;
    }

    public Users(String username, String picUrl, String picUrlMid, String picUrlMin, String mobile, String email,
                 String password, Date registTime, Integer vip, Integer usertype, Integer loginType) {
        this.username = username;
        this.picUrl = picUrl;
        this.picUrlMid = picUrlMid;
        this.picUrlMin = picUrlMin;
        this.mobile = mobile;
        this.email = email;
        this.password = password;
        this.registTime = registTime;
        this.vip = vip;
        this.usertype = usertype;
        this.loginType = loginType;
    }

    public Users() {
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getVerifyStatus() {
        return verifyStatus;
    }

    public void setVerifyStatus(Integer verifyStatus) {
        this.verifyStatus = verifyStatus;
    }
}

