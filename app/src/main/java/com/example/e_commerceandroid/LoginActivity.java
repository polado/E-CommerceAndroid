package com.example.e_commerceandroid;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.e_commerceandroid.view_models.LoginViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {
    LoginViewModel loginViewModel;

    @BindView(R.id.et_login_email)
    EditText email_tv;

    @BindView(R.id.et_login_password)
    EditText password_tv;

    @BindView(R.id.btn_login)
    Button login_btn;

    @BindView(R.id.loading_login)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);


        loginViewModel = new LoginViewModel(this);
        loginViewModel.isLoggedIn();

        loginViewModel.isLoadingLiveData.observe(this, aBoolean -> progressBar.setVisibility(aBoolean));
        loginViewModel.isEnabledLiveData.observe(this, aBoolean -> login_btn.setEnabled(aBoolean));
        loginViewModel.isLoggedInLiveData.observe(this, aBoolean -> {
            if (aBoolean) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
        });

        login_btn.setOnClickListener(v -> {
            if (checkValidData()) {
                loginViewModel.login(email_tv.getText().toString().trim(), password_tv.getText().toString().trim());
            }
        });
    }

    private boolean checkValidData() {
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

    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString().trim();
        return TextUtils.isEmpty(str);
    }

    boolean checkLength(EditText text) {
        return text.getText().toString().length() > 5;
    }

    boolean isEmailValid(EditText text) {
        CharSequence email = text.getText().toString();
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
