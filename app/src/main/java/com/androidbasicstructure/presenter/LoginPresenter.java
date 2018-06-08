package com.androidbasicstructure.presenter;

import com.androidbasicstructure.base.BasePresenter;
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
public class LoginPresenter extends BasePresenter<LoginView<LoginResponse>> {

    public void validateLoginData(HashMap<String, String> params) {
        ValidationErrorModel validationErrorModel = null;
        if ((validationErrorModel = (Validator.validateEmail(params.get(ApiParamConstant.EMAIL)))) != null) {
            getView().onValidationError(validationErrorModel);
        } else if ((validationErrorModel = (Validator.validatePassword(params.get(ApiParamConstant.PASSWORD)))) != null) {
            getView().onValidationError(validationErrorModel);
        } else {
            callLoginApi(params);
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
                        getView().onSuccess(response);
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
