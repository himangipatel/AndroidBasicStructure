package com.androidbasicstructure;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;

import com.androidbasicstructure.annotations.Layout;
import com.androidbasicstructure.base.MVPActivity;
import com.androidbasicstructure.connection.constant.ApiParamConstant;
import com.androidbasicstructure.models.LoginResponse;
import com.androidbasicstructure.presenter.LoginPresenter;
import com.androidbasicstructure.utils.AppUtils;
import com.androidbasicstructure.validator.ValidationErrorModel;
import com.androidbasicstructure.view.LoginView;
import com.imagepicker.FilePickUtils;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

@Layout(R.layout.activity_login)
public class LoginActivity extends MVPActivity<LoginPresenter, LoginView<LoginResponse>>
        implements LoginView<LoginResponse> {

    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.etPassword)
    EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showImagePickerDialog(onFileChoose);
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


    @OnClick(R.id.tvLogin)
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.tvLogin:
                HashMap<String, String> params = getDefaultParameter();
                params.put(ApiParamConstant.EMAIL, AppUtils.getText(etEmail));
                params.put(ApiParamConstant.PASSWORD, AppUtils.getText(etPassword));
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
                AppUtils.requestEdittextFocus(LoginActivity.this, etEmail);
                break;
            case PASSWORD:
                AppUtils.requestEdittextFocus(LoginActivity.this, etPassword);
                break;
        }
    }
}
