package com.example.e_commerceandroid.view_models;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.e_commerceandroid.AppCache;
import com.example.e_commerceandroid.api.ApiClient;
import com.example.e_commerceandroid.api.ApiInterface;
import com.example.e_commerceandroid.models.UserModel;
import com.example.e_commerceandroid.models.UserResponse;
import com.example.e_commerceandroid.models.body_models.LoginBodyModel;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class LoginViewModel extends ViewModel {
    private String TAG = "LoginViewModel";
    private Context context;


    public MutableLiveData<Integer> isLoadingLiveData = new MutableLiveData<>();
    public MutableLiveData<Boolean> isEnabledLiveData = new MutableLiveData<>();
    public MutableLiveData<Boolean> isLoggedInLiveData = new MutableLiveData<>();

    public LoginViewModel(Context context) {
        this.context = context;
    }

    public void isLoggedIn() {
        Observable<String> observable = AppCache.getInstance().getUserData(context);
        observable.subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe");
            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "onNext Token " + s);
                if (s == null)
                    isLoggedInLiveData.setValue(false);
                else if (s.equals("null"))
                    isLoggedInLiveData.setValue(false);
                else
                    isLoggedInLiveData.setValue(true);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete");
            }
        });
    }

    public void login(String email, String password) {
        isLoadingLiveData.setValue(View.VISIBLE);
        isEnabledLiveData.setValue(false);
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.login(new LoginBodyModel(email, password,
                "121212121", "andriod"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(this::parseLoginResponse)
                .subscribe(new Observer<UserModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe");
                    }

                    @Override
                    public void onNext(UserModel userModel) {
                        Log.d(TAG, "onNext Token " + userModel.getApiToken());
                        AppCache.getInstance().saveUserData(context, userModel);

                        isLoadingLiveData.setValue(View.INVISIBLE);
                        isEnabledLiveData.setValue(true);
                        isLoggedInLiveData.setValue(true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError " + e.toString());
                        isLoadingLiveData.setValue(View.INVISIBLE);
                        isEnabledLiveData.setValue(true);
                        isLoggedInLiveData.setValue(false);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                    }
                });
    }

    private Observable<UserModel> parseLoginResponse(Response<UserResponse> response) {

        Log.d(TAG, "parseLoginResponse " + response.body().getUserModel());
        UserModel userModel = response.body().getUserModel();
        return Observable.just(userModel);

    }

    public boolean checkValidData(EditText email_tv, EditText password_tv) {
        boolean isValid = true;
        if (isEmpty(email_tv)) {
            email_tv.setError("Email Can't be Empty");
            isValid = false;
        }
        if (isEmpty(password_tv)) {
            password_tv.setError("Password Can't be Empty");
            isValid = false;
        }
        if (!checkLength(password_tv)) {
            password_tv.setError("Password Must be 6 characters or more");
            isValid = false;
        }
        if (!isEmailValid(email_tv)) {
            email_tv.setError("Please Enter a Valid Email");
            isValid = false;
        }

        return isValid;
    }

    private boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    private boolean checkLength(EditText text) {
        return text.getText().toString().length() > 5;
    }

    private boolean isEmailValid(EditText text) {
        CharSequence email = text.getText().toString();
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
