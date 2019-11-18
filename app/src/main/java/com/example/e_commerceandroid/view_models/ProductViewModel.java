package com.example.e_commerceandroid.view_models;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.MutableLiveData;

import com.example.e_commerceandroid.AppCache;
import com.example.e_commerceandroid.api.ApiClient;
import com.example.e_commerceandroid.api.ApiInterface;
import com.example.e_commerceandroid.models.CartResponse;
import com.example.e_commerceandroid.models.ProductModel;
import com.example.e_commerceandroid.models.body_models.AddToCartBodyModel;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class ProductViewModel extends ParentViewModel {
    private String TAG = "HomeViewModel";

    public MutableLiveData<Integer> isLoadingLiveData = new MutableLiveData<>();
    public MutableLiveData<ProductModel> productModelMutableLiveData = new MutableLiveData<>();


    public ProductViewModel(int productID) {
        getProduct(productID);
    }

    private void getProduct(int productID) {
        isLoadingLiveData.setValue(View.VISIBLE);

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.getProduct(productID).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(this::parseProductResponse)
                .subscribe(new Observer<ProductModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                        Log.d(TAG, "onSubscribe");
                    }

                    @Override
                    public void onNext(ProductModel productModel) {

                        Log.d(TAG, "onNext");

                        if (productModel != null)
                            productModelMutableLiveData.setValue(productModel);
                        isLoadingLiveData.setValue(View.INVISIBLE);
                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.d(TAG, "onError");
                        isLoadingLiveData.setValue(View.INVISIBLE);
                    }

                    @Override
                    public void onComplete() {

                        Log.d(TAG, "onComplete");
                        isLoadingLiveData.setValue(View.INVISIBLE);
                    }
                });
    }

    private Observable<ProductModel> parseProductResponse(Response<ProductModel> response) {

        Log.d(TAG, "parseProductResponse " + response.body().getNameEn());
        ProductModel productModel = response.body();
        return Observable.just(productModel);
    }

    public void addToCart(ProductModel productModel) {

        isLoadingLiveData.setValue(View.VISIBLE);

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.addToCart(new AddToCartBodyModel(AppCache.getInstance().apiToken, productModel.getId(), 1))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(this::parseCartResponse)
                .subscribe(new Observer<CartResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CartResponse cartResponse) {
                        isLoadingLiveData.setValue(View.INVISIBLE);

                    }

                    @Override
                    public void onError(Throwable e) {
                        isLoadingLiveData.setValue(View.INVISIBLE);

                    }

                    @Override
                    public void onComplete() {
                        isLoadingLiveData.setValue(View.INVISIBLE);

                    }
                })
        ;
    }

    private Observable<CartResponse> parseCartResponse(Response<CartResponse> response) {
        Log.d(TAG, "parseCartResponse " + response.body().getCartModels().size());
        CartResponse cartResponse = response.body();
        return Observable.just(cartResponse);
    }
}