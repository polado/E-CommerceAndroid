
package com.example.e_commerceandroid.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class CartResponse {

    @SerializedName("carts")
    @Expose
    private List<CartModel> cartModels = new ArrayList<>();
    @SerializedName("total_price")
    @Expose
    private Integer totalPrice;
    @SerializedName("total_items")
    @Expose
    private Integer totalItems;
    @SerializedName("shipping")
    @Expose
    private String shipping;

    public List<CartModel> getCartModels() {
        return cartModels;
    }

    public void setCartModels(List<CartModel> cartModels) {
        this.cartModels = cartModels;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(Integer totalItems) {
        this.totalItems = totalItems;
    }

    public String getShipping() {
        return shipping;
    }

    public void setShipping(String shipping) {
        this.shipping = shipping;
    }

}