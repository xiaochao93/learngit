package com.duia.english.configure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by chenqi on 2017/7/28.
 */
@Component
@ConfigurationProperties(prefix = "system")
public class SystemConfig {

    private String env;

    private String[] crossOrigin;

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String[] getCrossOrigin() {
        return crossOrigin;
    }

    public void setCrossOrigin(String[] crossOrigin) {
        this.crossOrigin = crossOrigin;
    }
}
