package com.duia.english.common.constant;

/**
 * Created by xiaochao on 2018/8/3.
 */
public class WeChatShareConfig {

    private String appId;

    private String timestamp;

    private String nonceStr;

    private String signature;

    public String getAppId() {
        return appId;
    }

    public WeChatShareConfig setAppId(String appId) {
        this.appId = appId;
        return this;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public WeChatShareConfig setTimestamp(String timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public WeChatShareConfig setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
        return this;
    }

    public String getSignature() {
        return signature;
    }

    public WeChatShareConfig setSignature(String signature) {
        this.signature = signature;
        return this;
    }
}
