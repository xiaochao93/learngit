package com.duia.english.controller.rest;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.duia.english.common.annotation.IgnoreValidation;
import com.duia.english.common.constant.ResponseEntity;
import com.duia.english.common.constant.Result;
import com.duia.english.common.utils.HttpService;
import com.duia.english.common.utils.ImageUtils;
import com.duia.english.common.utils.StringsUtil;
import com.duia.english.model.UserExamScore;
import com.duia.english.service.IUserExamScoreService;
import com.duia.us.dubbo.service.IDuiaUsersDubboService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.annotations.ApiIgnore;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.util.StringUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by xiaochao on 2018/7/25.
 */
@Api(value = "822-四六级成绩",description = "四六级成绩查询接口")
@RestController
@RequestMapping("/user-exam-score")
public class UserExamScoreController {
    private static Logger logger = LoggerFactory.getLogger(UserExamScoreController.class);
    private static Pattern p_http_img = Pattern.compile("http://(?!(\\.jpg|\\.png)).+?(\\.jpg|\\.png)",Pattern.CASE_INSENSITIVE);

    @Value("${cet4.url.code}")
    private String CET4_URL_CODE;
    @Value("${cet4.url.check}")
    private String CET4_URL_CHECK;
    @Value("${cet4.url.code.chsi}")
    private String CET4_URL_CODE_CESI;
    @Value("${cet4.url.check.chsi}")
    private String CET4_URL_CHECK_CESI;
    @Value("${imageServiceRealPath}")
    private String imageServiceRealPath;
    @Value("${imageServicePath}")
    private String imageServicePath;
    @Value("${host.url}")
    private String hostUrl;

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private HttpService httpService;
    @Autowired
    private IUserExamScoreService userExamScoreService;

    /**
     * 教育考试网获取验证码
     * @return
     */
    @ApiOperation(value="教育考试网获取验证码", notes="教育考试网获取验证码")
    @ApiImplicitParam(name = "card", value = "准考证", required = true, dataType = "String",paramType = "query")
    @IgnoreValidation
    @PostMapping("/code")
    public Result getCode(String card){
        if(StringUtil.isEmpty(card)){
            return ResponseEntity.FAILURE.setMessage("card can not be null");
        }
        Map<String,String> params = new HashMap<>();
        params.put("c","CET");
        params.put("ik",card);
        params.put("t",String.valueOf(Math.random()));
        Map<String,String> header =new HashMap<>();
        header.put("Host","cache.neea.edu.cn");
        header.put("Referer","http://cet.neea.edu.cn/cet/");
        org.springframework.http.ResponseEntity<String> responseEntity = httpService.get(CET4_URL_CODE,header,params);
        if(responseEntity == null){
            return ResponseEntity.FAILURE(CET4_URL_CODE+"出现错误");
        }
        String res = responseEntity.getBody();
        logger.info("【教育网验证码】" + res);
        if(StringUtil.isNotEmpty(res)){
            if(res.contains("result.err")){
                Pattern pattern = Pattern.compile("(?<=\\()[^\\)]+");
                Matcher matcher = pattern.matcher(res);
                if(matcher.find()){
                    String result = matcher.group();
                    return ResponseEntity.FAILURE(result.replaceAll("'",""));
                }
            }
            Matcher matcher = p_http_img.matcher(res);
            if (matcher.find()) {
                res = matcher.group(0);
            }
            return ResponseEntity.OK(res);
        }else {
            return ResponseEntity.FAILURE(CET4_URL_CODE+"出现错误");
        }
    }


    @ApiOperation(value="教育考试网获取成绩",produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParams(value ={@ApiImplicitParam (name = "code",value = "验证码",paramType = "query"),
            @ApiImplicitParam (name = "card",value = "准考证号",paramType = "query"),
            @ApiImplicitParam (name = "name",value = "学生姓名",paramType = "query"),
            @ApiImplicitParam (name = "grade",value = "查询级别:CET4或CET6",paramType = "query")} )
    @IgnoreValidation
    @PostMapping("/check")
    public Result check(String code, String card, String name, HttpServletRequest request,String grade) {
        Map<String,String> params = new HashMap<>();
        Map<String,String> headers = new HashMap<>();
        params.put("data", grade+"_181_DANGCI," + card + "," + name);
        params.put("v", code);
        headers.put("Host","cache.neea.edu.cn");
        headers.put("Referer","http://cet.neea.edu.cn/cet/");
        headers.put("Origin","http://cet.neea.edu.cn");
        headers.put("Upgrade-Insecure-Requests","1");
        String ret = httpService.submitForm(CET4_URL_CHECK, headers, params);
        try {
            Pattern pattern = Pattern.compile("(?<=\\()[^\\)]+");
            Matcher matcher = pattern.matcher(ret);
            if (matcher.find()) {
                String result = matcher.group();
                if(StringUtil.isNotEmpty(result)){
                    result=result.replaceAll("\"","");
                    if (result.indexOf("error") > 0) {
                        JSONObject obj = JSON.parseObject(result);
                        return ResponseEntity.EXCEPTION.setMessage("【教育网】"+obj.getString("error"));
                    }
                    UserExamScore examScore = json2entity(grade,result);
                    save(examScore);
                    return ResponseEntity.OK(examScore);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.EXCEPTION.setMessage("【教育网】查询无结果，请稍后重新查询");
    }

    @ApiIgnore
    @IgnoreValidation
    @GetMapping("/codeCESI")
    public void codeCESI(HttpServletRequest request, HttpServletResponse response){
        repsonseImg(request,response);
    }

    @ApiOperation(value="学信网获取验证码",produces = MediaType.APPLICATION_JSON_VALUE)
    @IgnoreValidation
    @PostMapping("/code2")
    public Result getCode()  {
        return ResponseEntity.OK(hostUrl+"/user-exam-score/codeCESI");
    }



    @ApiOperation(value = "学信网获取成绩",notes = "四六级成绩查询",produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParams({@ApiImplicitParam(name = "code",value = "验证码",paramType = "query"),
            @ApiImplicitParam(name = "cookie",value = "获取验证码返回时的cookie",paramType = "query"),
            @ApiImplicitParam(name = "card",value = "准考证",paramType = "query"),
            @ApiImplicitParam(name = "name",value = "学生姓名",paramType = "query")})
    @IgnoreValidation
    @PostMapping("/check2")
    public Result check2(String code, String name,String cookie,String card) {
        if(StringUtil.isEmpty(cookie)){
            return ResponseEntity.FAILURE.setMessage("未识别用户");
        }
        Map<String,String> params = new HashMap<>();
        String url = CET4_URL_CHECK_CESI+"?zkzh="+card+"&xm="+name+"&yzm="+code;
        Map<String,String> headers = new HashMap<>();
        params.put("zkzh",card);
        params.put("xm",name);
        params.put("yzm",code);
        headers.put("Host","www.chsi.com.cn");
        headers.put("Referer","https://www.chsi.com.cn/cet/");
        headers.put("Cookie",cookie);
        MultiValueMap<String,String> reqParams = new LinkedMultiValueMap<>();
        HttpHeaders httpHeaders = new HttpHeaders();
        params.keySet().forEach(key ->reqParams.add(key,params.get(key)));
        headers.keySet().forEach(key->httpHeaders.set(key,headers.get(key)));
        httpHeaders.add("Accept", MediaType.TEXT_HTML_VALUE);
        HttpEntity<MultiValueMap<String,String>> entity = new HttpEntity(reqParams,httpHeaders);
        org.springframework.http.ResponseEntity<String> result = restTemplate.exchange(url,HttpMethod.GET, entity, String.class,params);
        if(result == null || result.getStatusCodeValue()!=200){
            return ResponseEntity.NOT_FOUND.setMessage("【学信网】查询无结果，请稍后重新查询");
        }
        String res = result.getBody();
        logger.info("【学信网查询结果】" + result);
        //解析html
        Document doc = Jsoup.parse(res);
        Element form1 = doc.getElementById("form1");
        //验证码错误或准考证信息错误
        if(form1!=null && form1.select("div").hasClass("error")){
           return ResponseEntity.FAILURE.setMessage(form1.select("div").text());
        }
        Elements td = doc.select("div.m_cnt_m").get(0).select(".cetTable td");
        UserExamScore examScore = elements2entity(td);
        save(examScore);
        return  ResponseEntity.OK(examScore);
    }

    @ApiOperation(value = "生成分享成绩的图片" ,notes = "返回图片地址",produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParam(name = "card",value = "准考证号",paramType = "query")
    @IgnoreValidation
    @PostMapping(value = "/share-grade")
    public Result createImage(String card){
        if(StringUtils.isBlank(card)){
            return ResponseEntity.FAILURE.setMessage("card can not be null");
        }
        UserExamScore result = this.userExamScoreService.findByExamCard(card);
        if(result==null){
            return ResponseEntity.FAILURE;
        }
        String path = null;
        try {
            path =new ImageUtils().exportImg(result, imageServiceRealPath);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.EXCEPTION;
        }
        return ResponseEntity.OK(imageServicePath+path);
    }

    public  void repsonseImg(HttpServletRequest request,HttpServletResponse response) {
        InputStream inputStream = getInputStream(request,response);
        byte[] data = new byte[1024];
        int len = 0;
        OutputStream fileOutputStream = null;
        try {
            fileOutputStream = response.getOutputStream();
            while ((len = inputStream.read(data)) != -1) {
                fileOutputStream.write(data, 0, len);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        }

    }

    public InputStream  getInputStream(HttpServletRequest request,HttpServletResponse response) {
        InputStream inputStream = null;
        HttpURLConnection httpURLConnection = null;
        String URL_PATH = CET4_URL_CODE_CESI+Math.random()*1000;
        try {
            URL url = new URL(URL_PATH);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(3000);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setRequestMethod("GET");
            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode == 200) {
                inputStream = httpURLConnection.getInputStream();
                Map<String,List<String>> map=httpURLConnection.getHeaderFields();
                map.keySet().stream().filter(headKey->"Set-Cookie".equals(headKey)).forEach(realKey->{
                    StringBuilder builder = new StringBuilder();
                    map.get(realKey).forEach(str->builder.append(str));
                    String firstCookie = builder.toString();
                    Map<String,String> cookieMap = transStringToMap(firstCookie,";","=");
                    cookieMap.keySet().forEach(cookieKey->{
                        request.setAttribute(cookieKey,cookieMap.get(cookieKey));
                        Cookie cookie = new Cookie(cookieKey, cookieMap.get(cookieKey));
                        cookie.setPath("/");
                        response.addCookie(cookie);
                    });

                });
            }
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return inputStream;

    }
    public static Map<String, String> transStringToMap(String mapString, String separator, String pairSeparator) {
        Map<String, String> map = new HashMap<>();
         String[] fSplit = mapString.split(separator);
         for (int i = 0; i < fSplit.length; i++) {
             if (fSplit[i]==null||fSplit[i].length()==0) {
                   continue;
                 }
             String[] sSplit = fSplit[i].split(pairSeparator);
             String value = fSplit[i].substring(fSplit[i].indexOf('=') + 1, fSplit[i].length());
             if("Path".equals(sSplit[0].trim())||"HttpOnly".equals(sSplit[0].trim())||"Expires".equals(sSplit[0].trim())){
                 continue;
             }
             map.put(sSplit[0].trim(), value);
         }
         return map;
     }

    private UserExamScore json2entity(String grade,String str){
        JSONObject obj = JSON.parseObject(str);
        UserExamScore examScore = new UserExamScore();
        examScore.setExamCardNo(obj.getString("z"))
                .setRealName(obj.getString("n"))
                .setRealSchool(obj.getString("x"))
                .setAllScore(obj.getBigDecimal("s"))
                .setReadScore(obj.getBigDecimal("r"))
                .setOtherScore(obj.getBigDecimal("w"))
                .setListenScore(obj.getBigDecimal("l"))
                .setExamCardNo(obj.getString("z"))
                .setOralExamCardNo(((obj.getString("kyz").equals("--"))==false)?obj.getString("kyz"):null)
                .setOralGrade(((obj.getString("kys")).equals("--")==false)?obj.getString("kys"):null)
                .setUpdateTime(new Date());
        examScore.setSkuId("CET6".equals(grade)?202L:4L);
        return examScore;
    }

    private UserExamScore elements2entity(Elements td){
        String realName = td.get(0).text();
        String school = td.get(1).text();
        String grade = td.get(2).text();
        String realCard = td.get(3).text();
        String allScore = StringsUtil.delHtmlTag(td.get(4).text());
        String listenScore = td.get(6).text();
        String readScore = td.get(8).text();
        String otherScore = td.get(10).text();
        UserExamScore examScore = new UserExamScore(realName,school,realCard,new BigDecimal(allScore),new BigDecimal(listenScore),new BigDecimal(readScore),new BigDecimal(otherScore));
        if(!td.get(11).text().equals("--")){
            examScore.setOralExamCardNo(td.get(11).text()).setOralGrade(td.get(12).text());
        }
        examScore.setSkuId(grade.contains("六")?202L:4L);
        return examScore;
    }




    private void save(UserExamScore examScore){
        Condition condition = new Condition(UserExamScore.class);
        condition.selectProperties("examCardNo","findNum");
        condition.createCriteria().andEqualTo("examCardNo", examScore.getExamCardNo());
        List<UserExamScore> byCondition = this.userExamScoreService.findByCondition(condition);
        examScore.setGrade(examScore.getSkuId().equals(4L)?"英语四级":"英语六级");
        if(!CollectionUtils.isEmpty(byCondition)){
         examScore.setFindNum(byCondition.get(0).getFindNum()+1).setUpdateTime(new Date());
         this.userExamScoreService.updateByCardNo(examScore);
        }else {
         examScore.setCreateTime(new Date());
         examScore.setUpdateTime(new Date());
         this.userExamScoreService.save(examScore);
        }
    }

}
