package com.example.e_commerceandroid.models.body_models;

public class AddToCartBodyModel {
    private String api_token;
    private int product_id;
    private int quantity;

    public AddToCartBodyModel(String api_token, int product_id, int quantity) {
        this.api_token = api_token;
        this.product_id = product_id;
        this.quantity = quantity;
    }

    public String getApi_token() {
        return api_token;
    }

    public void setApi_token(String api_token) {
        this.api_token = api_token;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
