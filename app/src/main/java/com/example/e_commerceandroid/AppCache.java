package com.example.e_commerceandroid;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.e_commerceandroid.models.UserModel;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AppCache {
    private String TAG = "AppCache";
    private static AppCache appCache;

    public boolean isLoggedIn = false;
    public UserModel userModel = new UserModel();
    public String apiToken;

    private AppCache() {
    }

    public static AppCache getInstance() {
        if (appCache == null)
            appCache = new AppCache();
        return appCache;
    }

    public void saveUserData(Context context, UserModel userModel) {
        this.userModel = userModel;
        isLoggedIn = true;

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        Completable.fromAction(() -> sharedPreferences.edit()
                .putString("api_token", userModel.getApiToken()).apply())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                        isLoggedIn = true;
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError " + e.toString());
                        isLoggedIn = false;
                    }
                });
    }

    public Observable<String> getUserData(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String result = sharedPreferences.getString("api_token", null);
        if (result == null)
            result = "null";
        apiToken = result;
        return Observable.just(result).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());

//        return Completable.fromAction(() -> sharedPreferences.getString("user_email", null))
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io());
    }
}
