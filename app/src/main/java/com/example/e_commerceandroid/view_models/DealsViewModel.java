package com.example.e_commerceandroid.view_models;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.MutableLiveData;

import com.example.e_commerceandroid.api.ApiClient;
import com.example.e_commerceandroid.api.ApiInterface;
import com.example.e_commerceandroid.database.DatabaseClient;
import com.example.e_commerceandroid.database.ProductDao;
import com.example.e_commerceandroid.models.BrandModel;
import com.example.e_commerceandroid.models.CategoryModel;
import com.example.e_commerceandroid.models.DealsModel;
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

public class DealsViewModel extends ParentViewModel {
    private String TAG = "DealsViewModel";

    private Context context;
    private ProductDao productDao;

    public MutableLiveData<Integer> isLoadingLiveData = new MutableLiveData<>();
    public MutableLiveData<List<ProductModel>> hotDealsLiveData = new MutableLiveData<>();
    public MutableLiveData<List<BrandModel>> topBrandsLiveData = new MutableLiveData<>();
    public MutableLiveData<List<CategoryModel>> topCategoriesLiveData = new MutableLiveData<>();


    public DealsViewModel(Context context) {
        this.context = context;
        productDao = DatabaseClient.getInstance(context).getAppDatabase().productDao();
        getDeals();
    }

    private void getDeals() {
        isLoadingLiveData.setValue(View.VISIBLE);
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.getDeals().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(this::parseDealsResponse)
                .subscribe(new Observer<DealsModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe");
                    }

                    @Override
                    public void onNext(DealsModel dealsModel) {
                        Log.d(TAG, "onNext " + dealsModel.getTopCategories().size());
                        if (dealsModel.getHotDeals() != null)
                            hotDealsLiveData.setValue(dealsModel.getHotDeals());
                        if (dealsModel.getTopBrand() != null)
                            topBrandsLiveData.setValue(dealsModel.getTopBrand());
                        if (dealsModel.getTopCategories() != null)
                            topCategoriesLiveData.setValue(dealsModel.getTopCategories());
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

    private Observable<DealsModel> parseDealsResponse(Response<DealsModel> response) {
        Log.d(TAG, "parseDealsResponse " + response.body().getTopCategories().size());
        DealsModel dealsModel = response.body();
        return Observable.just(dealsModel);
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
