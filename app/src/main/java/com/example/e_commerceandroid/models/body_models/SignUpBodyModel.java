package com.example.e_commerceandroid.models.body_models;

public class SignUpBodyModel {
    private String name;
    private String password;
    private String email;
    private String phone;

    public SignUpBodyModel(String name, String password, String email, String phone) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.phone = phone;
    }
}
