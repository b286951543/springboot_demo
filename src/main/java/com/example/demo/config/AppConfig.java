package com.example.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="app") // 设置前缀，会自动把 application.yml 里对应的参数注入进来，需要依赖：spring-boot-configuration-processor
public class AppConfig {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    private String name;

    private String version;
}
