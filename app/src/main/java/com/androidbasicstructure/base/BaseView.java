package com.androidbasicstructure.base;

/**
 * Created by Himangi Patel on 6/12/2017.
 */

public interface BaseView<T> {
    boolean hasInternet();

    void showProgressDialog(boolean show);

//    void onSuccess(T response);

    void onFailure(String message);

}