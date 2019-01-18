package com.duia.english.common.verify;

import com.duia.english.common.utils.MD5;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by admin on 2015/12/25.
 */
@Component
public class SecurityVerifyRequest {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String signKey = "signature";

    @Value("${verify.signSecret:@Duia(8dJKd80jau}")
    private String signSecret;

    public boolean verifyRequest(Map<String, String> params) {
        String sign = params.get(signKey);
        //缺少验证参数的情况
        if (sign == null) {
            return false;
        }

        //没参数的情况
        if (sign.equals(signSecret)) {
            return true;
        }
        StringBuffer pvStr = new StringBuffer();
        params.keySet().stream().filter(key -> !signKey.equals(key)).sorted().forEach(key -> {
            String value = params.get(key);
            pvStr.append("&");
            pvStr.append(key);
            pvStr.append("=");
            pvStr.append(value);
        });


        StringBuffer pvsStr = pvStr.append(signSecret);
        String finalPvsStr = pvsStr.substring(1, pvsStr.length());
        if (sign != null && sign.equals(MD5.getMD5(finalPvsStr))) {
            return true;
        }
        logger.error("[加密] 加秘钥后为：" + finalPvsStr + "；加密后签名为：" + MD5.getMD5(finalPvsStr) + ";接收的签名为 " + sign);
        return false;
    }
}
