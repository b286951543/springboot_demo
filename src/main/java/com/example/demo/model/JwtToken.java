package com.example.demo.model;

import org.apache.shiro.authc.AuthenticationToken;

public class JwtToken implements AuthenticationToken {

    private static final long serialVersionUID = 1282057025599826155L;

    private String token;

    private String expireAt;

    public JwtToken(String token) {
        this.token = token;
    }

    public JwtToken(String token, String expireAt) {
        this.token = token;
        this.expireAt = expireAt;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getExpireAt() {
        return expireAt;
    }

    public void setExpireAt(String expireAt) {
        this.expireAt = expireAt;
    }
}
