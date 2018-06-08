package com.androidbasicstructure;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.androidbasicstructure.annotations.Layout;
import com.androidbasicstructure.base.MVPActivity;
import com.androidbasicstructure.connection.constant.ApiParamConstant;
import com.androidbasicstructure.databinding.ActivityLoginBinding;
import com.androidbasicstructure.models.LoginResponse;
import com.androidbasicstructure.presenter.LoginPresenter;
import com.androidbasicstructure.utils.AppUtils;
import com.androidbasicstructure.validator.ValidationErrorModel;
import com.androidbasicstructure.view.LoginView;
import com.imagepicker.FilePickUtils;

import java.util.HashMap;

@Layout(R.layout.activity_login)
public class LoginActivity extends MVPActivity<LoginPresenter, LoginView<LoginResponse>>
        implements LoginView<LoginResponse> {

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showImagePickerDialog(onFileChoose);
        binding = (ActivityLoginBinding) getBinding();
       // binding.setUser(new UserModel("himangi.patel@imobdev.com", "Imobdev@123"));
    }

    private FilePickUtils.OnFileChoose onFileChoose = new FilePickUtils.OnFileChoose() {
        @Override
        public void onFileChoose(String s, int i) {
            showAlerterBar(getActivity(), s);
        }
    };


    @NonNull
    @Override
    public LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    @NonNull
    @Override
    public LoginView<LoginResponse> attachView() {
        return this;
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
                getPresenter().validateLoginData(params);
                break;
        }
    }

    @Override
    public void onSuccess(LoginResponse response) {

    }

    @Override
    public void onFailure(String message) {

    }

    @Override
    public void onValidationError(ValidationErrorModel validationErrorModel) {
        showAlerterBar(getActivity(), getString(validationErrorModel.getMsg()));
        switch (validationErrorModel.getError()) {
            case EMAIL:
                AppUtils.requestEdittextFocus(LoginActivity.this, binding.etEmail);
                break;
            case PASSWORD:
                AppUtils.requestEdittextFocus(LoginActivity.this, binding.etPassword);
                break;
        }
    }
}
