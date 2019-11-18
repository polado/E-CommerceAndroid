package com.example.e_commerceandroid.api;

import com.example.e_commerceandroid.models.CartResponse;
import com.example.e_commerceandroid.models.DealsModel;
import com.example.e_commerceandroid.models.HomeModel;
import com.example.e_commerceandroid.models.PageModel;
import com.example.e_commerceandroid.models.ProductModel;
import com.example.e_commerceandroid.models.UserResponse;
import com.example.e_commerceandroid.models.body_models.AddToCartBodyModel;
import com.example.e_commerceandroid.models.body_models.LoginBodyModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @POST("auth/signup")
    Observable<UserResponse> signUp(@Body UserResponse userResponse);

    @POST("auth/signin")
    Observable<Response<UserResponse>> login(@Body LoginBodyModel bodyModel);

    @GET("app/home")
    Observable<Response<HomeModel>> getHome();

    @GET("app/deals-page")
    Observable<Response<DealsModel>> getDeals();


    @GET("app/{categorized}")
    Observable<Response<PageModel>> getCategorized(@Path("categorized") String categorized
            , @Query("page") int page);

    @GET("app/product/{product_id}")
    Observable<Response<ProductModel>> getProduct(@Path("product_id") int productID);

    @GET("app/cart/get-carts")
    Observable<Response<CartResponse>> getCarts(@Query("api_token") String apiToken);

    @POST("app/cart/add")
    Observable<Response<CartResponse>> addToCart(@Body AddToCartBodyModel addToCartBodyModel);
}