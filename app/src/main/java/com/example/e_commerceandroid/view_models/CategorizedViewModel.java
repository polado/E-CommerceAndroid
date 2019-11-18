package com.example.e_commerceandroid.view_models;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.MutableLiveData;

import com.example.e_commerceandroid.api.ApiClient;
import com.example.e_commerceandroid.api.ApiInterface;
import com.example.e_commerceandroid.database.DatabaseClient;
import com.example.e_commerceandroid.database.ProductDao;
import com.example.e_commerceandroid.models.PageModel;
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

public class CategorizedViewModel extends ParentViewModel {
    private String TAG = "CategorizedViewModel";

    private String categorized;
    private Context context;
    private ProductDao productDao;

    public MutableLiveData<Integer> isLoadingLiveData = new MutableLiveData<>();
    public MutableLiveData<PageModel> categorizedLiveData = new MutableLiveData<>();


    public CategorizedViewModel(Context context, String categorized) {
        this.context = context;
        this.categorized = categorized;
        productDao = DatabaseClient.getInstance(context).getAppDatabase().productDao();
        getCategorized(1);
    }


    public void getCategorized(int page) {
        isLoadingLiveData.setValue(View.VISIBLE);

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.getCategorized(categorized, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(this::parseCategorizedResponse)
                .subscribe(new Observer<PageModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(PageModel pageModel) {
                        if (pageModel != null)
                            categorizedLiveData.setValue(pageModel);
                        getFavorites();
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
                });
    }

    private Observable<PageModel> parseCategorizedResponse(Response<PageModel> response) {

        Log.d(TAG, response.body().getCurrentPage()+" parseCategorizedResponse " + response.body().getData().size());
        PageModel pageModel = response.body();
        return Observable.just(pageModel);
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
                            Log.d(TAG, productModel.getId()+" product fav "+productModel.getNameEn());
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
