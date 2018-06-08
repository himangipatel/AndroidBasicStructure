package com.androidbasicstructure.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.androidbasicstructure.utils.AppUtils;

/**
 * Created by Himangi Patel on 6/12/2017.
 */


public abstract class MVPActivity<P extends BasePresenter<V>, V extends BaseView> extends BaseActivity {

    private P presenter;

    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = createPresenter();
        presenter.attachView(attachView());
    }

    @Override
    public void onDestroy() {
        presenter.detachView();
        super.onDestroy();
    }

    public abstract
    @NonNull
    P createPresenter();

    public abstract
    @NonNull
    V attachView();

    public P getPresenter() {
        return presenter;
    }

    public boolean hasInternet() {
        return AppUtils.hasInternet(this);
    }


    public Activity getActivity() {
        return this;
    }
}
