package com.example.e_commerceandroid.view_models;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.e_commerceandroid.models.ProductModel;

public abstract class ParentViewModel extends ViewModel {

    public MutableLiveData<ProductModel> isFavoriteLiveData = new MutableLiveData<>();
    public MutableLiveData<ProductModel> productClickedLiveData = new MutableLiveData<>();

    public void onToggleFavoriteClick(ProductModel productModel) {

    }

    public void onProductClick(ProductModel productModel) {
        productClickedLiveData.setValue(productModel);
    }
}
