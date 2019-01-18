package com.duia.english;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Arrays;

@SpringBootApplication
@EnableTransactionManagement
@DubboComponentScan(basePackages = "com.duia.english")
public class StartApplication {
    public static void main(String[] args) { 
        Arrays.stream(args).forEach(System.out::println);
        SpringApplication.run(StartApplication.class, args);
    }
}
