package com.example.e_commerceandroid.view_models;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.e_commerceandroid.database.DatabaseClient;
import com.example.e_commerceandroid.database.ProductDao;
import com.example.e_commerceandroid.models.ProductModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class IsFavorite {
    public static MutableLiveData<ProductModel> favoritesLiveData = new MutableLiveData<>();
    public static List<ProductModel> favoriteList = new ArrayList<>();
    private static ProductDao productDao;

    public IsFavorite(Context context) {
        productDao = DatabaseClient.getInstance(context).getAppDatabase().productDao();
        getFavorites();
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
                        favoriteList.clear();
                        favoriteList.addAll(productModels);

                        for (ProductModel productModel : productModels) {
                            productModel.setIsFav(!productModel.getIsFav());
                            favoritesLiveData.setValue(productModel);
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

    public static void onToggleFavoriteClick(ProductModel productModel) {
        Completable.fromAction(() -> {
            if (productModel.getIsFav()) {
                productModel.setIsFav(false);
                productDao.delete(productModel);
            } else {
                productModel.setIsFav(true);
                productDao.insert(productModel);
            }

        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onComplete() {
                        int index = isContain(productModel);
                        if (productModel.getIsFav()) {
                            Log.d("IsFavorite", index + " favoriteList add " + productModel.getId());
                            if (index == -1)
                                favoriteList.add(productModel);
                        } else {
                            Log.d("IsFavorite", "favoriteList delete " + productModel.getId());


                            if (index > -1)
                                favoriteList.remove(index);
                        }

                        Log.d("IsFavorite", "favoriteList " + favoriteList.size());

                        favoritesLiveData.setValue(productModel);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                });
    }

    private static int isContain(ProductModel productModel) {
        int index = -1;
        for (int i = 0; i < favoriteList.size(); i++) {
            Log.d("IsFavorite", i + " favoriteList isContain " + favoriteList.get(i).getId());
            if (favoriteList.get(i).getId().equals(productModel.getId()))
                index = i;
        }
        return index;

    }
}
