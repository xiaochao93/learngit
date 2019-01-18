package com.duia.english.common.utils;

import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

/**
 * Created by chenqi on 2016/11/23.
 */
public class HttpTool {

    private static String signSecret = "duiaNiuBi)JN#ERFGBN";
    public final static String signKey = "signature";

    /**
     * http请求参数加密
     * @param params
     * @return
     */
    public static String signature( Map<String,String> params){
        if(params == null){
            params = new HashMap<String,String>();
        }
        Set<String> keys = params.keySet();
        List<String> list = new ArrayList<String>(keys);
        Collections.sort(list);
        StringBuffer sb = new StringBuffer();
        for(int i = 0;i < list.size();i ++ ){
            if(i > 0){
                sb.append("&");
            }
            sb.append(list.get(i));
            sb.append("=").append(params.get(list.get(i)));
        }
        sb.append(signSecret);
        return MD5.getMD5(sb.toString());
    }

}
