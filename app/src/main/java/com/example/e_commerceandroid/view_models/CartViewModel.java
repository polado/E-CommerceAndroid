package com.example.e_commerceandroid.view_models;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.MutableLiveData;

import com.example.e_commerceandroid.AppCache;
import com.example.e_commerceandroid.api.ApiClient;
import com.example.e_commerceandroid.api.ApiInterface;
import com.example.e_commerceandroid.models.CartResponse;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class CartViewModel extends ParentViewModel {
    private String TAG = "CartViewModel";

    private Context context;

    public MutableLiveData<Integer> isLoadingLiveData = new MutableLiveData<>();
    public MutableLiveData<CartResponse> cartItemsLiveData = new MutableLiveData<>();


    public CartViewModel(Context context) {
        this.context = context;
        getCart();
    }

    private void getCart() {
        isLoadingLiveData.setValue(View.VISIBLE);
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Log.d(TAG, "getCart " + AppCache.getInstance().apiToken);
        apiInterface.getCarts(AppCache.getInstance().apiToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(this::parseCartResponse)
                .subscribe(new Observer<CartResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe");
                    }

                    @Override
                    public void onNext(CartResponse cartResponse) {
                        Log.d(TAG, "onNext " + cartResponse.getTotalItems());
                        if (cartResponse.getCartModels() != null)
                            cartItemsLiveData.setValue(cartResponse);
                        isLoadingLiveData.setValue(View.INVISIBLE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError " + e.toString());
                        isLoadingLiveData.setValue(View.INVISIBLE);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                        isLoadingLiveData.setValue(View.INVISIBLE);
                    }
                });
    }


    private Observable<CartResponse> parseCartResponse(Response<CartResponse> response) {
        Log.d(TAG, "parseCartResponse " + response.body().getCartModels().size());
        CartResponse cartResponse = response.body();
        return Observable.just(cartResponse);
    }
}
