package com.androidbasicstructure.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.androidbasicstructure.R;
import com.androidbasicstructure.annotations.Layout;
import com.androidbasicstructure.base.MVVMActivity;
import com.androidbasicstructure.connection.constant.ApiParamConstant;
import com.androidbasicstructure.databinding.ActivityLoginBinding;
import com.androidbasicstructure.db.DatabaseHandler;
import com.androidbasicstructure.models.LoginResponse;
import com.androidbasicstructure.models.User;
import com.androidbasicstructure.models.UserModel;
import com.androidbasicstructure.viewmodel.LoginViewModel;
import com.androidbasicstructure.utils.AppUtils;
import com.androidbasicstructure.utils.Prefs;
import com.androidbasicstructure.validator.ValidationErrorModel;
import com.imagepicker.FilePickUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Layout(R.layout.activity_login)
public class LoginActivity extends MVVMActivity<LoginViewModel> {

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getBinding();

        if (Prefs.with().getBoolean("isLogin", false)) {
            Intent intent = new Intent(LoginActivity.this, UserListingActivity.class);
            startActivity(intent);
        }
        initViews();
    }

    private void initViews() {
        setToolBarVisibility(View.GONE);
        clickableViews(new View[]{binding.tvLogin});
        binding.setUser(new UserModel());
        binding.etEmail.addTextChangedListener(textWatcher);
        binding.etPassword.addTextChangedListener(textWatcher);
        getViewModel().validateErrModel.observe(this, validationErrorModelObserver);
    }

    private FilePickUtils.OnFileChoose onFileChoose = new FilePickUtils.OnFileChoose() {
        @Override
        public void onFileChoose(String s, int i) {
            showAlerterBar(getActivity(), s);
        }
    };


    @NonNull
    @Override
    public LoginViewModel createViewModel() {
        return ViewModelProviders.of(this).get(LoginViewModel.class);
    }


    @Override
    public void onClick(View view) {
        super.onClick(view);
        setTootBartVLeft("Himangi");
        switch (view.getId()) {
            case R.id.tvLogin:
                HashMap<String, String> params = getDefaultParameter();
                params.put(ApiParamConstant.EMAIL, AppUtils.getText(binding.etEmail));
                params.put(ApiParamConstant.PASSWORD, AppUtils.getText(binding.etPassword));
                getViewModel().validateLoginData(params).observe(this, loginResponseObserver);
                break;
        }
    }

    private Observer<LoginResponse> loginResponseObserver = new Observer<LoginResponse>() {
        @Override
        public void onChanged(@Nullable LoginResponse loginResponse) {
            if (loginResponse != null) {
                hideProgressDialog();
                binding.etEmail.setText(loginResponse.getUserEmail());
                insertData(loginResponse.getUserFirstName().concat(" ").concat(loginResponse.getUserLastName()),
                        loginResponse.getUserEmail());
                Prefs.with().save("isLogin", true);
            }
        }
    };


    private Observer<ValidationErrorModel> validationErrorModelObserver = new Observer<ValidationErrorModel>() {
        @Override
        public void onChanged(@Nullable ValidationErrorModel validationErrorModel) {
            if (validationErrorModel != null){
                switch (validationErrorModel.getError()) {
                    case EMAIL:
                        AppUtils.requestEdittextFocus(LoginActivity.this, binding.etEmail);
                        binding.tilEmail.setError(getString(validationErrorModel.getMsg()));
                        break;
                    case PASSWORD:
                        AppUtils.requestEdittextFocus(LoginActivity.this, binding.etPassword);
                        binding.tilPassword.setError(getString(validationErrorModel.getMsg()));
                        break;
                }
            }
        }
    };

    private void insertData(String name, String email) {
        List<User> userList = new ArrayList<>();
        userList.add(new User(name, email));

        DatabaseHandler
                .getInstance(getActivity())
                .getUserDao()
                .insert(userList);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(LoginActivity.this, UserListingActivity.class);
                startActivity(intent);
                hideProgressDialog();
                finish();
            }
        }, 3000);
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            binding.tilEmail.setErrorEnabled(false);
            binding.tilPassword.setErrorEnabled(false);
        }
    };

}
