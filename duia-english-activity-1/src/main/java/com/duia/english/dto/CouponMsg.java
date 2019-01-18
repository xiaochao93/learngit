package com.duia.english.dto;

import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * Created by xiaochao on 2018/11/12.
 */
@Alias("CouponMsg")
public class CouponMsg implements Serializable {
    private Integer appType;

    private Long userid;

    public Integer getAppType() {
        return appType;
    }

    public CouponMsg setAppType(Integer appType) {
        this.appType = appType;
        return this;
    }

    public Long getUserid() {
        return userid;
    }

    public CouponMsg setUserid(Long userid) {
        this.userid = userid;
        return this;
    }

    public CouponMsg(Integer appType, Long userid) {
        this.appType = appType;
        this.userid = userid;
    }

    public CouponMsg() {
    }
}
