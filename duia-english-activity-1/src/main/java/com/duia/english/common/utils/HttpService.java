package com.duia.english.common.utils;

import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenqi on 2017/4/20.
 */
@Service
public class HttpService {

    @Autowired
    private RestTemplate restTemplate;


    public String get(String url,Map<String,String> params){
        try {
            url = getUrl(url,params);
            return restTemplate.getForObject(url,String.class,String.class,params);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return null;
    }


    public String post(String url,Map<String,String> params) {
        try {
            url = getUrl(url,params);
            return restTemplate.postForObject(url,null,String.class,params);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return null;
    }

    private String getUrl(String url, Map<String,String> params) throws URISyntaxException, UnsupportedEncodingException {
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

    public ResponseEntity<String> postForm(String url,Map<String,String> headers,Map<String,String> params){
        try {

            HttpHeaders requestHeaders = new HttpHeaders();
            MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
            requestHeaders.setContentType(type);
            requestHeaders.add("Accept", MediaType.APPLICATION_JSON.toString());
            if(headers != null && headers.size() > 0){
                for(String key : headers.keySet()){
                    requestHeaders.set(key,headers.get(key));
                }
            }
            MultiValueMap<String, String> mvm = new LinkedMultiValueMap<>();
            for(String key : params.keySet()){
                mvm.add(key,params.get(key));
            }
            mvm.add(HttpTool.signKey, HttpTool.signature(params));
            HttpEntity< MultiValueMap<String,String>> entity = new HttpEntity< MultiValueMap<String,String>>(mvm,requestHeaders);
            ResponseEntity<String> response  = restTemplate.postForEntity(url,entity,String.class);
            return response;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public ResponseEntity<String> get(String url,Map<String,String> headers,Map<String,String> params){
        try {
            if(params == null){
                params = new HashMap<String,String>();
            }
            params.put("timestamp",String.valueOf(System.currentTimeMillis()));
            params.put(HttpTool.signKey, HttpTool.signature(params));
            HttpHeaders httpHeaders = new HttpHeaders();
            if(headers != null && headers.size() > 0){
                for(String key : headers.keySet()){
                    httpHeaders.set(key,headers.get(key));
                }
            }
            HttpEntity<String> formEntity = new HttpEntity<String>(httpHeaders);
            ResponseEntity<String> result = restTemplate.exchange(getUrl(url,params),HttpMethod.GET, formEntity, String.class,params);
            return result;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public String submitForm(String url,Map<String,String> headers,Map<String,String> params){
        MultiValueMap<String,String> reqParams = new LinkedMultiValueMap<>();
        HttpHeaders httpHeaders = new HttpHeaders();
        params.keySet().forEach(key ->reqParams.add(key,params.get(key)));
        headers.keySet().forEach(key->httpHeaders.set(key,headers.get(key)));
        httpHeaders.add("Accept", MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String,String>> entity = new HttpEntity(reqParams,httpHeaders);
        return restTemplate.postForObject(url,entity,String.class);
    }

    public String getImg(String url,Map<String,String> headers,Map<String,String> params){
        MultiValueMap<String,String> reqParams = new LinkedMultiValueMap<>();
        HttpHeaders httpHeaders = new HttpHeaders();
        params.keySet().forEach(key ->reqParams.add(key,params.get(key)));
        headers.keySet().forEach(key->httpHeaders.set(key,headers.get(key)));
        httpHeaders.add("Accept", MediaType.IMAGE_JPEG_VALUE);
        httpHeaders.setContentType(MediaType.IMAGE_JPEG);
        HttpEntity<MultiValueMap<String,String>> entity = new HttpEntity(reqParams,httpHeaders);
        return restTemplate.getForEntity(url,String.class,entity).getBody();
    }

}
