package com.duia.english.configure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 测试/预发部开启 swagger2
 * 扫描 com.duia.english.controller.rest 包下的Controller
 * Created by liuhao on 2018/4/8.
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Value("${swagger.enable}")
    private boolean enableSwagger;

    @Value("${swagger.version}")
    private String version;

    @Bean
    public Docket buildDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(buildApiInf())
                .enable(enableSwagger)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.duia.english.controller.rest"))
                .paths(PathSelectors.any())
                .build();
    }
    private ApiInfo buildApiInf() {
        return new ApiInfoBuilder()
                .title("duia-englih-activity api")
                .description("对啊网英语活动项目接口")
                .version(version)
                .contact(new Contact("xiaochao","www.duia.com","xiaochao@duia.com"))
                .build();

    }

}
