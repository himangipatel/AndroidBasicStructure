package com.androidbasicstructure.base;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Himangi Patel on 11/1/2019.
 */


public abstract class MVVMActivity<A extends BaseViewModel> extends BaseActivity {

    private A viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = createViewModel();
        getViewModel().getIsLoading().observe(this, loadingStatusObserver);
        getViewModel().getErrMsg().observe(this, errMsgObserver);
        getViewModel().getSuccessMsg().observe(this, successMsgObserver);
    }

    private Observer<Boolean> loadingStatusObserver = new Observer<Boolean>() {
        @Override
        public void onChanged(@Nullable Boolean isLoading) {
            if (isLoading == null) return;
            showProgressDialog(isLoading);
        }
    };

    private Observer<String> errMsgObserver = new Observer<String>() {
        @Override
        public void onChanged(@Nullable String s) {
            showAlerterBar(getActivity(), s);
        }
    };

    private Observer<String> successMsgObserver = new Observer<String>() {
        @Override
        public void onChanged(@Nullable String s) {
            showAlerterBar(getActivity(), s);
        }
    };



    public abstract @NonNull
    A createViewModel();

    public A getViewModel() {
        return viewModel;
    }

    public Activity getActivity() {
        return this;
    }


}
