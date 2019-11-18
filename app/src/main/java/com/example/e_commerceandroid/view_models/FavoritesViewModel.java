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

public class FavoritesViewModel extends ParentViewModel {
    private String TAG = "HomeViewModel";

    private Context context;
    private ProductDao productDao;

    public MutableLiveData<Integer> isLoadingLiveData = new MutableLiveData<>();
    public MutableLiveData<List<ProductModel>> favoritesLiveData = new MutableLiveData<>();


    public FavoritesViewModel(Context context) {
        this.context = context;
        productDao = DatabaseClient.getInstance(context).getAppDatabase().productDao();
        getFavorites();
    }

    private void getFavorites() {
        isLoadingLiveData.setValue(View.VISIBLE);
        productDao.getAll().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<ProductModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<ProductModel> productModels) {
                        isLoadingLiveData.setValue(View.INVISIBLE);
                        favoritesLiveData.setValue(productModels);
                        for (ProductModel productModel : productModels) {
                            productModel.setIsFav(!productModel.getIsFav());
                            isFavoriteLiveData.setValue(productModel);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        isLoadingLiveData.setValue(View.INVISIBLE);

                    }

                    @Override
                    public void onComplete() {
                        isLoadingLiveData.setValue(View.INVISIBLE);

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
