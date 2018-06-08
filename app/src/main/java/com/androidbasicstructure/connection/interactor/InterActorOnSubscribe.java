package com.androidbasicstructure.connection.interactor;


import rx.functions.Action0;

/**
 * Created by Himangi Patel on 6/12/2017.
 */
class InterActorOnSubscribe<T> implements Action0 {

  private InterActorCallback<T> mInterActorCallback;

  InterActorOnSubscribe(InterActorCallback<T> mInterActorCallback) {
    this.mInterActorCallback = mInterActorCallback;
  }

  @Override
  public void call() {
    mInterActorCallback.onStart();
  }
}
