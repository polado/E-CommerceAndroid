package com.example.e_commerceandroid.models;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity
public class ProductModel {
    @PrimaryKey
    @SerializedName("id")
    @Expose
    private Integer id;
    @ColumnInfo(name = "name_ar")
    @SerializedName("name_ar")
    @Expose
    private String nameAr;
    @ColumnInfo(name = "name_en")
    @SerializedName("name_en")
    @Expose
    private String nameEn;
    @ColumnInfo(name = "description_ar")
    @SerializedName("description_ar")
    @Expose
    private String descriptionAr;
    @ColumnInfo(name = "description_en")
    @SerializedName("description_en")
    @Expose
    private String descriptionEn;
    @SerializedName("stock")
    @Expose
    private Integer stock;
    @SerializedName("status")
    @Expose
    private Integer status;
    @ColumnInfo(name = "shipping_specification")
    @SerializedName("shipping_specification")
    @Expose
    private String shippingSpecification;
    @ColumnInfo(name = "sub_category_id")
    @SerializedName("sub_category_id")
    @Expose
    private Integer subCategoryId;
    @ColumnInfo(name = "created_at")
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @ColumnInfo(name = "updated_at")
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("price")
    @Expose
    private Integer price;
    @ColumnInfo(name = "long_description_ar")
    @SerializedName("long_description_ar")
    @Expose
    private String longDescriptionAr;
    @ColumnInfo(name = "long_description_en")
    @SerializedName("long_description_en")
    @Expose
    private String longDescriptionEn;
    @ColumnInfo(name = "brand_id")
    @SerializedName("brand_id")
    @Expose
    private int brandId;
    @SerializedName("disable")
    @Expose
    private Integer disable;
    @ColumnInfo(name = "friendly_url")
    @SerializedName("friendly_url")
    @Expose
    private String friendlyUrl;
    @ColumnInfo(name = "count_paid")
    @SerializedName("count_paid")
    @Expose
    private Integer countPaid;
    @SerializedName("url")
    @Expose
    private String url;
    @ColumnInfo(name = "default_image")
    @SerializedName("default_image")
    @Expose
    private String defaultImage;
    @ColumnInfo(name = "is_fav")
    @SerializedName("is_fav")
    @Expose
    private Boolean isFav;
    @ColumnInfo(name = "total_rate")
    @SerializedName("total_rate")
    @Expose
    private Integer totalRate;
    @ColumnInfo(name = "is_reviewed")
    @SerializedName("is_reviewed")
    @Expose
    private Boolean isReviewed;
    @Ignore
    @SerializedName("images")
    @Expose
    private List<ImageModel> imagesModel = null;
    @Ignore
    @SerializedName("subcategory")
    @Expose
    private SubcategoryModel subcategoryModel;
    @Ignore
    @SerializedName("brand")
    @Expose
    private BrandModel brand;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNameAr() {
        return nameAr;
    }

    public void setNameAr(String nameAr) {
        this.nameAr = nameAr;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getDescriptionAr() {
        return descriptionAr;
    }

    public void setDescriptionAr(String descriptionAr) {
        this.descriptionAr = descriptionAr;
    }

    public String getDescriptionEn() {
        return descriptionEn;
    }

    public void setDescriptionEn(String descriptionEn) {
        this.descriptionEn = descriptionEn;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getShippingSpecification() {
        return shippingSpecification;
    }

    public void setShippingSpecification(String shippingSpecification) {
        this.shippingSpecification = shippingSpecification;
    }

    public Integer getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(Integer subCategoryId) {
        this.subCategoryId = subCategoryId;
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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getLongDescriptionAr() {
        return longDescriptionAr;
    }

    public void setLongDescriptionAr(String longDescriptionAr) {
        this.longDescriptionAr = longDescriptionAr;
    }

    public String getLongDescriptionEn() {
        return longDescriptionEn;
    }

    public void setLongDescriptionEn(String longDescriptionEn) {
        this.longDescriptionEn = longDescriptionEn;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public Integer getDisable() {
        return disable;
    }

    public void setDisable(Integer disable) {
        this.disable = disable;
    }

    public String getFriendlyUrl() {
        return friendlyUrl;
    }

    public void setFriendlyUrl(String friendlyUrl) {
        this.friendlyUrl = friendlyUrl;
    }

    public Integer getCountPaid() {
        return countPaid;
    }

    public void setCountPaid(Integer countPaid) {
        this.countPaid = countPaid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDefaultImage() {
        return defaultImage;
    }

    public void setDefaultImage(String defaultImage) {
        this.defaultImage = defaultImage;
    }

    public Boolean getIsFav() {
        return isFav;
    }

    public void setIsFav(Boolean isFav) {
        this.isFav = isFav;
    }

    public Integer getTotalRate() {
        return totalRate;
    }

    public void setTotalRate(Integer totalRate) {
        this.totalRate = totalRate;
    }

    public Boolean getIsReviewed() {
        return isReviewed;
    }

    public void setIsReviewed(Boolean isReviewed) {
        this.isReviewed = isReviewed;
    }

    public List<ImageModel> getImageModels() {
        return imagesModel;
    }

    public void setImageModels(List<ImageModel> imageModels) {
        this.imagesModel = imageModels;
    }

    public SubcategoryModel getSubcategoryModel() {
        return subcategoryModel;
    }

    public void setSubcategoryModel(SubcategoryModel subcategoryModel) {
        this.subcategoryModel = subcategoryModel;
    }

    public BrandModel getBrand() {
        return brand;
    }

    public void setBrand(BrandModel brand) {
        this.brand = brand;
    }
}



