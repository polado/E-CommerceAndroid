package com.example.e_commerceandroid.view_models;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.MutableLiveData;

import com.example.e_commerceandroid.api.ApiClient;
import com.example.e_commerceandroid.api.ApiInterface;
import com.example.e_commerceandroid.database.DatabaseClient;
import com.example.e_commerceandroid.database.ProductDao;
import com.example.e_commerceandroid.models.CategoryModel;
import com.example.e_commerceandroid.models.HomeModel;
import com.example.e_commerceandroid.models.ProductModel;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class HomeViewModel extends ParentViewModel {
    private String TAG = "HomeViewModel";

    private Context context;
    private ProductDao productDao;

    public MutableLiveData<Integer> isLoadingLiveData = new MutableLiveData<>();
    public MutableLiveData<List<ProductModel>> newArrivalLiveData = new MutableLiveData<>();
    public MutableLiveData<List<ProductModel>> bestSellerLiveData = new MutableLiveData<>();
    public MutableLiveData<List<CategoryModel>> topCategoriesLiveData = new MutableLiveData<>();


    public HomeViewModel(Context context) {
        this.context = context;
        productDao = DatabaseClient.getInstance(context).getAppDatabase().productDao();
        getHome();
    }

    private void getHome() {
        isLoadingLiveData.setValue(View.VISIBLE);
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.getHome().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(this::parseHomeResponse)
                .subscribe(new Observer<HomeModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe");
                    }

                    @Override
                    public void onNext(HomeModel homeModel) {
                        Log.d(TAG, "onNext " + homeModel.getBestSeller().size());
                        if (homeModel.getNewArrival() != null)
                            newArrivalLiveData.setValue(homeModel.getNewArrival());
                        if (homeModel.getBestSeller() != null)
                            bestSellerLiveData.setValue(homeModel.getBestSeller());
                        if (homeModel.getTopCategories() != null)
                            topCategoriesLiveData.setValue(homeModel.getTopCategories());
                        getFavorites();
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

    private void getFavorites() {
        productDao.getAll().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<ProductModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<ProductModel> productModels) {
                        for (ProductModel productModel : productModels) {
                            productModel.setIsFav(!productModel.getIsFav());
                            isFavoriteLiveData.setValue(productModel);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private Observable<HomeModel> parseHomeResponse(Response<HomeModel> response) {

        Log.d(TAG, "parseHomeResponse " + response.body().getTopCategories().size());
        HomeModel homeModel = response.body();
        return Observable.just(homeModel);
    }

    @Override
    public void onToggleFavoriteClick(ProductModel productModel) {
        super.onToggleFavoriteClick(productModel);
        Completable.fromAction(() -> {
            if (productModel.getIsFav())
                productDao.delete(productModel);
            else
                productDao.insert(productModel);

        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onComplete() {
                        productModel.setIsFav(!productModel.getIsFav());
                        isFavoriteLiveData.setValue(productModel);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                });
    }
}
