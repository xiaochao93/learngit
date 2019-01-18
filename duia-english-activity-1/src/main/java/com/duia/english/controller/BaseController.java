package com.duia.english.controller;

import com.duia.english.common.utils.HttpService;
import com.duia.english.configure.SystemConfig;
import com.duia.english.model.Users;
import com.duia.sso.client.common.Common;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Description 基础信息获取的控制器
 * @Author chenqi
 * @email chenqi@duia.com
 * @Date 2018/11/6
 */
public class BaseController {
    private static Logger logger = LoggerFactory.getLogger(BaseController.class);

    @Autowired
    private HttpService httpService;
    @Autowired
    private SystemConfig systemConfig;

    /**
     * @description 根据cookie 中的登录票据获取当前登录用户ID
     * @param request
     * @author chenqi
     * @date 2018/11/6
     * @return 用户ID
     **/
    public Long getLoginUserId(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Users user = (Users) session.getAttribute(Common.USERS_CLIENT);
        Long userId = Common.VERIFICATION_STATUS_CODE;
        if(user != null){
            userId = user.getId();
        }
        return userId;
    }

}
