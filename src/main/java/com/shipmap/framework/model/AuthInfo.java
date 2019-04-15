package com.shipmap.framework.model;

import java.io.Serializable;

public class AuthInfo implements Serializable {
    public AuthInfo(String token, boolean multiplexLogin) {
        this.token = token;
        this.multiplexLogin = multiplexLogin;
    }

    public AuthInfo() {
    }

    private static final long serialVersionUID = 4162651600013148393L;
    //认证token
    private String token;
    //是否已登录了
    private boolean multiplexLogin;

    public boolean isMultiplexLogin() {
        return multiplexLogin;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean getMultiplexLogin() {
        return multiplexLogin;
    }

    public void setMultiplexLogin(boolean multiplexLogin) {
        this.multiplexLogin = multiplexLogin;
    }
}
