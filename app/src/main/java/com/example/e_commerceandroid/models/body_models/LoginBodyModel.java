package com.example.e_commerceandroid.models.body_models;

public class LoginBodyModel {
    private String name;
    private String password;
    private String mobile_token;
    private String os;

    public LoginBodyModel(String name, String password, String mobile_token, String os) {
        this.name = name;
        this.password = password;
        this.mobile_token = mobile_token;
        this.os = os;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile_token() {
        return mobile_token;
    }

    public void setMobile_token(String mobile_token) {
        this.mobile_token = mobile_token;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }
}
