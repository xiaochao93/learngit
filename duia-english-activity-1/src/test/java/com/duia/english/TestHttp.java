package com.duia.english;

import com.duia.english.common.constant.Constants;
import com.duia.english.common.utils.HttpService;
import com.duia.english.common.utils.HttpTool;
import org.apache.http.client.utils.URIBuilder;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.awt.*;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenqi on 2018/7/26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TestHttp{
    @Autowired
    private HttpService httpService;

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void testurl(){
        Map<String,String> params = new HashMap<>();
        params.put("c","CET");
        params.put("ik","440610181100820");
        params.put("t","0.2764075732614075");
        String url = "http://cet.neea.edu.cn/cet/";
        Map<String,String> header =new HashMap<>();
        header.put("Host","cache.neea.edu.cn");
        header.put("Referer","http://cet.neea.edu.cn/cet/");
        org.springframework.http.ResponseEntity<String> res = httpService.get(url,header,params);
        System.out.println(res);
    }

    @Test
    public void test() throws UnsupportedEncodingException, URISyntaxException {
        Map<String,String> params = new HashMap<>();
        params.put("c","CET");
        params.put("ik","530090172103915");
        params.put("t","0.2764075732614075");
        String url = "http://cache.neea.edu.cn/Imgs.do";
        Map<String,String> header =new HashMap<>();
        header.put("Host","cache.neea.edu.cn");
        header.put("Referer","http://cet.neea.edu.cn/cet/");
        org.springframework.http.ResponseEntity<String> res = httpService.get(url,header,params);
        System.out.println(res);
    }

    @Test
    public void test2(){
        Map<String,String> params = new HashMap<>();
        Map<String,String> headers = new HashMap<>();
        params.put("data","CET4_181_DANGCI,530090172103915,杨悦");
        params.put("v","wmyb");
        headers.put("Host","cache.neea.edu.cn");
        headers.put("Referer","http://cet.neea.edu.cn/cet/");
        headers.put("Origin","http://cet.neea.edu.cn");
        headers.put("Upgrade-Insecure-Requests","1");
        String result = httpService.submitForm("http://cache.neea.edu.cn/cet/query", headers, params);
        System.out.println(result);

    }

    @Test
    public void test23(){
        String url = "http://bang.test.duia.com/web/getTimeout";
        long time = System.currentTimeMillis();
        Map<String,String> params = new HashMap<>();
        params.put("aa","1");
        String result = httpService.post(url,params);
        System.out.println(System.currentTimeMillis() - time);
    }


    @Test
    public void test3(){
        Map<String,String> params = new HashMap<>();
        Map<String,String> headers = new HashMap<>();
        headers.put("Host","www.chsi.com.cn");
        headers.put("Referer","https://www.chsi.com.cn/cet/");
        params.put("ID",String.valueOf(Math.random()));
        MultiValueMap<String,String> reqParams = new LinkedMultiValueMap<>();
        HttpHeaders httpHeaders = new HttpHeaders();
        params.keySet().forEach(key ->reqParams.add(key,params.get(key)));
        headers.keySet().forEach(key->httpHeaders.set(key,headers.get(key)));
        httpHeaders.add("Accept", MediaType.IMAGE_JPEG_VALUE);
        HttpEntity<MultiValueMap<String,String>> entity = new HttpEntity(reqParams,httpHeaders);
        String body = restTemplate.getForEntity("https://www.chsi.com.cn/cet/ValidatorIMG.JPG", String.class, entity).getBody();
        System.out.println(body+"");

    }



    private String getUrl(String url,Map<String,String> params) throws URISyntaxException, UnsupportedEncodingException {
        if(params == null){
            params = new HashMap<String,String>();
        }
        params.put("timestamp",String.valueOf(System.currentTimeMillis()));
        params.put(HttpTool.signKey, HttpTool.signature(params));

        URI uri = new URI(url);
        URIBuilder builder = new URIBuilder(uri);

        for(String key : params.keySet()){
            builder.addParameter(key,"{"+key +"}");
        }
        url = builder.build().toString();
        url = URLDecoder.decode(url,"UTF-8");
        return url;
    }


    @Test
    public void testValidate(){
        Map<String,String> params = new HashMap<>();
        params.put("d_t","2b3d3222-2578-47fe-a710-182823a62f64");
        //发送请求验证d_t的有效性
        ResponseEntity<String> responseEntity = httpService.get(String.format(Constants.URL.VALIDATE_USER_URL, "http://cq.duia.com:8086"),null,params);
        Long userId = 0l;
        String result = responseEntity.getBody();
        System.out.println(result);
    }

}
