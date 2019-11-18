package com.example.e_commerceandroid.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

class Ad {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}

public class DealsModel {

    @SerializedName("ads")
    @Expose
    private List<Ad> ads = null;
    @SerializedName("hot_deals")
    @Expose
    private List<ProductModel> hotDeals = null;
    @SerializedName("top_categories")
    @Expose
    private List<CategoryModel> topCategories = null;
    @SerializedName("top_brand")
    @Expose
    private List<BrandModel> topBrand = null;

    public List<Ad> getAds() {
        return ads;
    }

    public void setAds(List<Ad> ads) {
        this.ads = ads;
    }

    public List<ProductModel> getHotDeals() {
        return hotDeals;
    }

    public void setHotDeals(List<ProductModel> hotDeals) {
        this.hotDeals = hotDeals;
    }

    public List<CategoryModel> getTopCategories() {
        return topCategories;
    }

    public void setTopCategories(List<CategoryModel> topCategories) {
        this.topCategories = topCategories;
    }

    public List<BrandModel> getTopBrand() {
        return topBrand;
    }

    public void setTopBrand(List<BrandModel> topBrand) {
        this.topBrand = topBrand;
    }

}