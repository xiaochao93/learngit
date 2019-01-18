package com.duia.english.common.utils;

import com.duia.english.common.constant.Common;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author liuhao
 * @date 2015/05/25
 */
public class CookieUtil {
    private final static Logger logger = LoggerFactory.getLogger(CookieUtil.class);

    public static String getCookie(HttpServletRequest request, String key) {
        Cookie[] cookies = request.getCookies();
        String ticket = null;
        if (cookies != null && cookies.length > 0 && key != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(key)) {
                    ticket = cookie.getValue();
                }
            }
        }
        return ticket;
    }

    /**
     * 创建一个cookie
     *
     * @param key    cookie名
     * @param value  cookie值
     * @param expire cookie生存周期
     * @return
     */
    public static Cookie createCookie(String key, String value, int expire) {
        return optCookie(key, value, expire, Common.COOKIE_DOMAIN);
    }

    public static Cookie optCookie(String key, String value, int expire, String Domain) {
        Cookie cookie = new Cookie(key, value);
        cookie.setDomain(Domain);
        cookie.setPath(Common.COOKIE_PATH);
        cookie.setMaxAge(expire);
        return cookie;
    }


    /**
     * 创建ORGCncookie
     *
     * @param key    cookie名
     * @param value  cookie值
     * @param expire cookie生存周期
     * @return
     */
    public static Cookie createOrgCnCookie(String key, String value, int expire) {
        return optCookie(key, value, expire, Common.COOKIE_ORG_DOMAIN);
    }

    public static Cookie removeCookie(String key) {
        return optCookie(key, null, 0, Common.COOKIE_DOMAIN);
    }

    public static Cookie removeOrgCnCookie(String key) {
        return optCookie(key, null, 0, Common.COOKIE_ORG_DOMAIN);
    }
}
