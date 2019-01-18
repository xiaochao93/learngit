package com.duia.english.controller.rest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.duia.english.common.annotation.IgnoreValidation;
import com.duia.english.common.constant.Constants;
import com.duia.english.common.constant.ResponseEntity;
import com.duia.english.common.constant.Result;
import com.duia.english.common.constant.WeChatShareConfig;
import com.duia.english.common.utils.HttpService;
import com.duia.english.common.utils.SHAUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.security.DigestException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by xiaochao on 2018/8/2.
 */
@Api(value = "微信",description = "微信对接接口")
@RestController
@RequestMapping("/wechat")
public class WeChatController {
    private static Logger logger = LoggerFactory.getLogger(WeChatController.class);
    @Autowired
    private HttpService httpService;
    @Resource
    private RedisTemplate redisTemplate;
    @Value("${wechat.appid}")
    private String appId;
    @Value("${wechat.secret}")
    private String secret;
    @Value("${wechat.share}")
    private boolean share;


    private static String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";
    private static String API_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=%s&type=jsapi";

    @ApiOperation(value="微信分享获取参数",produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParam(name = "url",value = "要分享的页面地址",required = true,paramType = "query")
    @IgnoreValidation
    @PostMapping("/setting")
    public Result setting(String url){
        try {
            if(!share){
                return ResponseEntity.FAILURE("请联系开发配置微信分享环境");
            }
            WeChatShareConfig wechatCfg = getWechatCfg(appId, secret, url);
            return ResponseEntity.OK(wechatCfg);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            logger.error("-----微信参数获取失败----appid="+appId+"----secret="+secret+"----url="+url);
            return ResponseEntity.EXCEPTION("微信参数获取失败");
        }
    }



    private WeChatShareConfig getWechatCfg(String appId, String appSecret, String url){
        String ticket = (String)redisTemplate.opsForValue().get(Constants.Cache.API_TICKET_PREFIX + appId);
        if(StringUtils.isNotEmpty(ticket)){
            return signature(appId,ticket,url);

        }
        String token = (String)redisTemplate.opsForValue().get(Constants.Cache.ACCESS_TOKEN_PREFIX + appId);
        if(StringUtils.isEmpty(token)){
            token = getAccessToken(appId,appSecret);
            if(StringUtils.isEmpty(token)){
                logger.error("微信access_token获取失败");
                return null;
            }
            redisTemplate.opsForValue().set(Constants.Cache.ACCESS_TOKEN_PREFIX + appId,token);
            redisTemplate.expire(Constants.Cache.ACCESS_TOKEN_PREFIX + appId,5400, TimeUnit.SECONDS);
        }
        ticket = getApiTicket(token);
        if(StringUtils.isEmpty(ticket)){
            logger.error("微信api_ticket获取失败");
            return null;
        }
        redisTemplate.opsForValue().set(Constants.Cache.API_TICKET_PREFIX + appId,ticket);
        redisTemplate.expire(Constants.Cache.API_TICKET_PREFIX + appId,5400, TimeUnit.SECONDS);
        return signature(appId,ticket,url);
    }

    private String getAccessToken(String appId,String appSecret){
        try{
            String url = String.format(ACCESS_TOKEN_URL,appId,appSecret);
            String result = httpService.get(url,null);
            JSONObject obj = JSON.parseObject(result);
            logger.info("获取微信access_token结果：" + result);
            return obj.getString("access_token");

        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
        return null;
    }

    private String getApiTicket(String token){
        try{
            String url = String.format(API_TICKET_URL,token);
            String result = httpService.get(url,null);
            JSONObject obj = JSON.parseObject(result);
            logger.info("获取微信api_ticket结果：" + result);
            return obj.getString("ticket");
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
        return null;
    }

    private WeChatShareConfig signature(String appId, String ticket, String url){

        try {
            Long timestamp = System.currentTimeMillis() / 1000;
            String nonceStr = UUID.randomUUID().toString();
            Map<String,Object> params = new HashMap<>();
            params.put("noncestr",nonceStr);
            params.put("timestamp",timestamp);
            params.put("url", url);
            params.put("jsapi_ticket",ticket);
            String[] keys = params.keySet().toArray(new String[0]);
            Arrays.sort(keys);

            // 2. 按照排序拼接参数名与参数值
            StringBuilder sb = new StringBuilder();
            boolean isFirst = true;
            for (String key : keys) {
                if(!isFirst){
                    sb.append("&");
                }
                isFirst = false;
                sb.append(key).append("=").append(params.get(key));
            }
            logger.info("加密串：" + sb.toString());
            String signature = SHAUtil.SHA(sb.toString());
            signature = signature.toLowerCase();
            logger.info("秘钥：" + signature);
            WeChatShareConfig share = new WeChatShareConfig();
            share.setAppId(appId);
            share.setNonceStr(nonceStr);
            share.setTimestamp(String.valueOf(timestamp));
            share.setSignature(signature);
            return share;
        } catch (DigestException e) {
            logger.error(e.getMessage(),e);
        }
        return null;
    }


}
