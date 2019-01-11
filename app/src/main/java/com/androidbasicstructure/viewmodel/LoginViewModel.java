package com.androidbasicstructure.viewmodel;

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

import java.util.HashMap;

/**
 * Created by Himangi on 7/6/18
 */
public class LoginViewModel extends BaseViewModel {

    private MutableLiveData<LoginResponse> loginResponse = new MutableLiveData<>();
    public MutableLiveData<ValidationErrorModel> validateErrModel = new MutableLiveData<>();

    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<LoginResponse> validateLoginData(final HashMap<String, String> params) {
        ValidationErrorModel validationErrorModel = null;
        if ((validationErrorModel = (Validator.validateEmail(params.get(ApiParamConstant.EMAIL)))) != null) {
            validateErrModel.setValue(validationErrorModel);
        } else if ((validationErrorModel = (Validator.validatePassword(params.get(ApiParamConstant.PASSWORD)))) != null) {
            validateErrModel.setValue(validationErrorModel);
        } else {
            setIsLoading(true);
//            callLoginApi(params);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    LoginResponse response = new LoginResponse();
                    response.setUserEmail("dummyemail@gmail.com");
                    response.setUserId("0");
                    response.setUserFirstName("Himangi");
                    response.setUserLastName("Patel");
                    loginResponse.postValue(response);
                    setIsLoading(false);
                    setSuccessMsg("Login Successfully");
                }
            }, 1000);

        }
        return loginResponse;
    }

    private void callLoginApi(HashMap<String, String> params) {

        if (hasInternet()) {//If no internet it will show toast automatically.

            addSubscription(getAppInteractor().callLoginApi(params, new InterActorCallback<LoginResponse>() {
                @Override
                public void onStart() {
                    setIsLoading(true);
                }

                @Override
                public void onResponse(LoginResponse response) {
                    if (response.isStatus()) {
                        loginResponse.setValue(response);
                    } else {
                        setErrMsg(response.getMessage());
                    }
                }

                @Override
                public void onFinish() {
                    setIsLoading(false);
                }

                @Override
                public void onError(String message) {
                    setErrMsg(message);
                }
            }));
        }
    }
}
