package com.androidbasicstructure.base;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Himangi Patel on 11/1/2019.
 */

public abstract class MVVMFragment<A extends BaseViewModel> extends BaseFragment {

    private A viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = createViewModel();
        getViewModel().getIsLoading().observe(this, loadingStatusObserver);
        getViewModel().getErrMsg().observe(this, errMsgObserver);
        getViewModel().getSuccessMsg().observe(this, successMsgObserver);
    }

    public abstract
    @NonNull
    A createViewModel();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public A getViewModel() {
        return viewModel;
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


}
