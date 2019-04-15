package com.shipmap.framework.interceptor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthInterceptorConfig {
    //URL白名单列表
    @Value(value = "#{'${srping.security.whiteList}'.split(',')}")
    private String[] whiteList = null;
    //api版本
    @Value(value = "${api.version:v1}")
    private String apiVersion;

    public String[] getWhiteList() {
        return whiteList;
    }

    public String getApiVersion() {
        return apiVersion;
    }
}
