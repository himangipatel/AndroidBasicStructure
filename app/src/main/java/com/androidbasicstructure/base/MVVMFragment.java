package com.androidbasicstructure.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Himangi Patel on 6/12/2017.
 */

public abstract class MVVMFragment<P extends BaseViewModel<V>, V extends BaseView>
        extends BaseFragment {

    private P viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = createPresenter();
        viewModel.attachView(attachView());
    }

    public abstract
    @NonNull
    P createPresenter();

    public abstract
    @NonNull
    V attachView();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        viewModel.detachView();
    }

    public P getViewModel() {
        return viewModel;
    }

//    public boolean hasInternet() {
//        return AppUtils.hasInternet(getActivity());
//    }
}
