package com.androidbasicstructure.connection.interactor;


/**
 * Created by Himangi Patel on 6/12/2017.
 */
public interface InterActorCallback<T> {

    void onStart();

    void onResponse(T response);

    void onFinish();

    void onError(String message);

}
