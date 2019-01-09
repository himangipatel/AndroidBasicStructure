package com.androidbasicstructure.presenter;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.os.Handler;
import android.support.annotation.NonNull;

import com.androidbasicstructure.base.BaseViewModel;
import com.androidbasicstructure.connection.constant.ApiParamConstant;
import com.androidbasicstructure.connection.interactor.InterActorCallback;
import com.androidbasicstructure.models.LoginResponse;
import com.androidbasicstructure.validator.ValidationErrorModel;
import com.androidbasicstructure.validator.Validator;
import com.androidbasicstructure.view.LoginView;

import java.util.HashMap;

/**
 * Created by Himangi on 7/6/18
 */
public class LoginViewModel extends BaseViewModel<LoginView<LoginResponse>> {

    private MutableLiveData<LoginResponse> loginResponse;

    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

//    private MutableLiveData<String> email;
//    private MutableLiveData<String> password;

    public MutableLiveData<LoginResponse> getLoginResponse(HashMap<String, String> params) {
        if (loginResponse == null) {
            loginResponse = new MutableLiveData<>();
            validateLoginData(params);
        }
        return loginResponse;
    }

    private void validateLoginData(final HashMap<String, String> params) {
        ValidationErrorModel validationErrorModel = null;
        if ((validationErrorModel = (Validator.validateEmail(params.get(ApiParamConstant.EMAIL)))) != null) {
            getView().onValidationError(validationErrorModel);
        } else if ((validationErrorModel = (Validator.validatePassword(params.get(ApiParamConstant.PASSWORD)))) != null) {
            getView().onValidationError(validationErrorModel);
        } else {
            getView().showProgressDialog(true);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    LoginResponse response = new LoginResponse();
                    response.setUserEmail("dummyemail@gmail.com");
                    response.setUserId("0");
                    response.setUserFirstName("Himangi");
                    response.setUserLastName("Patel");
                    loginResponse.setValue(response);
                }
            }, 1000);

        }
    }

    private void callLoginApi(HashMap<String, String> params) {

        if (hasInternet()) {//If no internet it will show toast automatically.

            addSubscription(getAppInteractor().callLoginApi(params, new InterActorCallback<LoginResponse>() {
                @Override
                public void onStart() {
                    getView().showProgressDialog(true);
                }

                @Override
                public void onResponse(LoginResponse response) {
                    if (response.isStatus()) {
                        loginResponse.setValue(response);
                        //getView().onSuccess(response);
                    } else {
                        getView().onFailure(response.getMessage());
                    }
                }

                @Override
                public void onFinish() {
                    getView().showProgressDialog(false);
                }

                @Override
                public void onError(String message) {
                    getView().onFailure(message);
                }
            }));
        }
    }
}
