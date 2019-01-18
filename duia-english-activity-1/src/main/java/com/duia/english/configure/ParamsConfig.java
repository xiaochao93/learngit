package com.duia.english.configure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 全局变量application
 * 也可使用JsonControllerAdvice中 @ModelAttribute 或者 @sessionAttribute
 * Created by xiaochao on 2018/8/6.
 */
@Configuration
public class ParamsConfig implements ServletContextListener {
    @Value("${englishStaticServer}")
    private String englishStaticServer;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext ctx = servletContextEvent.getServletContext();
        ctx.setAttribute("englishStaticServer",englishStaticServer);
        ctx.setAttribute("staticVersion",System.currentTimeMillis());
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
