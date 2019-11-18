package com.example.e_commerceandroid.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HomeModel {

    @SerializedName("new_arrival")
    @Expose
    private List<ProductModel> newArrival = null;
    @SerializedName("top_categories")
    @Expose
    private List<CategoryModel> topCategories = null;
    @SerializedName("best_seller")
    @Expose
    private List<ProductModel> bestSeller = null;
    @SerializedName("hot_deals")
    @Expose
    private List<ProductModel> hotDeals = null;
    @SerializedName("side_menu_categories")
    @Expose
    private List<CategoryModel> sideMenuCategories = null;

    public List<ProductModel> getNewArrival() {
        return newArrival;
    }

    public void setNewArrival(List<ProductModel> newArrival) {
        this.newArrival = newArrival;
    }

    public List<CategoryModel> getTopCategories() {
        return topCategories;
    }

    public void setTopCategories(List<CategoryModel> topCategories) {
        this.topCategories = topCategories;
    }

    public List<ProductModel> getBestSeller() {
        return bestSeller;
    }

    public void setBestSeller(List<ProductModel> bestSeller) {
        this.bestSeller = bestSeller;
    }

    public List<ProductModel> getHotDeals() {
        return hotDeals;
    }

    public void setHotDeals(List<ProductModel> hotDeals) {
        this.hotDeals = hotDeals;
    }

    public List<CategoryModel> getSideMenuCategories() {
        return sideMenuCategories;
    }

    public void setSideMenuCategories(List<CategoryModel> sideMenuCategories) {
        this.sideMenuCategories = sideMenuCategories;
    }

}