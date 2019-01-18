package com.duia.english.configure;

import com.duia.sso.client.filter.DuiaSsoFilter;
import com.duia.sso.client.service.path.BlackPathMatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.ArrayList;

/**
 * Created by liuhao on 2018/4/2.
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    @Autowired
    private SystemConfig systemConfig;

//    @Autowired
//    private RestTemplateBuilder restTemplateBuilder;
    @Bean
    public RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(3000);
        requestFactory.setReadTimeout(3000);
        requestFactory.setChunkSize(1000);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(requestFactory);
        return restTemplate;//restTemplateBuilder.build();
    }


    //解决跨域问题
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        if(systemConfig.getCrossOrigin() != null && systemConfig.getCrossOrigin().length > 0){
            registry.addMapping("/**")
                    .allowedOrigins(systemConfig.getCrossOrigin())
                    .allowedMethods("POST", "GET")
                    .allowCredentials(true).maxAge(3600);
        }
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**","/favicon.ico","/MP_verify_RMhpSxdf9vE5SLyF.txt","/MP_verify_7zegp4minPFV197U.txt","/MP_verify_CzcPhBX1YnkW4MKT")
                .addResourceLocations("classpath:/static/");
        //swagger资源配置
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    /**
     * @description 配置登录过滤器
     * @author chenqi
     * @date 2018/11/13
     **/
    @Bean
    public FilterRegistrationBean sessionFilter(){
        DuiaSsoFilter duiaSsoFilter = new DuiaSsoFilter();
        BlackPathMatch blackPathMatch = new BlackPathMatch();
        blackPathMatch.setUrls(new ArrayList<>());
        duiaSsoFilter.setListFilter(blackPathMatch);
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(duiaSsoFilter);
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.setName("duiaSsoFilter");

        return filterRegistrationBean;
    }


}
