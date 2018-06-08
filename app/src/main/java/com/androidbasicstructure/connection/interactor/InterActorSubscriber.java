package com.androidbasicstructure.connection.interactor;

import java.net.SocketTimeoutException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

import static java.net.HttpURLConnection.HTTP_UNAUTHORIZED;

/**
 * Created by Himangi Patel on 6/12/2017.
 */
abstract class InterActorSubscriber<T> extends Subscriber<T> {

    private final String TAG = getClass().getSimpleName();
    private InterActorCallback<T> mInterActorCallback;
    private AppInteractor appInteractor;

    InterActorSubscriber(InterActorCallback<T> mInterActorCallback, AppInteractor appInteractor) {
        this.mInterActorCallback = mInterActorCallback;
        this.appInteractor = appInteractor;
    }

    @Override
    public void onCompleted() {
        if (!appInteractor.isCancel) {
            mInterActorCallback.onFinish();
        }
    }

    @Override
    public void onError(Throwable e) {
        if (!appInteractor.isCancel) {
            if (e instanceof SocketTimeoutException) {
                mInterActorCallback.onError("Connection time out, please try again");
            } else if (e instanceof HttpException && ((HttpException) e).code() == HTTP_UNAUTHORIZED) {
                mInterActorCallback.onError("Unauthorized Access");
            } else {
                mInterActorCallback.onError("Unknown error occurred");
            }
            mInterActorCallback.onFinish();
        }
    }
}