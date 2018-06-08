package com.androidbasicstructure.connection.interactor;

import com.androidbasicstructure.connection.response.Response;

import rx.Subscription;
import rx.functions.Action0;

/**
 * Created by Himangi Patel on 6/12/2017.
 */
class InterActorOnUnsubscribe<T extends Response> implements Action0 {

    private InterActorCallback<T> mInterActorCallback;
    private Subscription subscription;

    InterActorOnUnsubscribe(InterActorCallback<T> mInterActorCallback, Subscription subscription) {
        this.mInterActorCallback = mInterActorCallback;
        this.subscription = subscription;
    }

    @Override
    public void call() {

    }
}
