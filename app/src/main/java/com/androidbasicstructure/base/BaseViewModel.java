package com.androidbasicstructure.base;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.androidbasicstructure.connection.interactor.AppInteractor;
import com.androidbasicstructure.utils.AppUtils;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Himangi Patel on 6/12/2017.
 */
public abstract class BaseViewModel extends AndroidViewModel {

    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private MutableLiveData<String> errMsg = new MutableLiveData<>();
    private MutableLiveData<String> successMsg = new MutableLiveData<>();

    private AppInteractor appInteractor;
    private CompositeSubscription subscription = new CompositeSubscription();

    public BaseViewModel(@NonNull Application application) {
        super(application);
    }


//    Are ViewModels independent of activity/fragment lifecycles or just their configuration changes.
//
//    They are independent of configuration changes and are cleared when activity/fragment is destroyed.

    @Override
    protected void onCleared() {
        super.onCleared();
        if (subscription != null) {
            subscription.clear();
        }
    }

    protected void addSubscription(Subscription s) {
        subscription.add(s);
    }

    protected final AppInteractor getAppInteractor() {
        if (appInteractor == null) {
            appInteractor = new AppInteractor();
        }
        return appInteractor;
    }

    protected boolean hasInternet() {
        return AppUtils.hasInternet(getApplication().getBaseContext());
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public MutableLiveData<String> getErrMsg() {
        return errMsg;
    }

    public void setIsLoading(Boolean isLoading) {
        this.isLoading.postValue(isLoading);
    }

    public void setErrMsg(String errMsg) {
        this.errMsg.postValue(errMsg);
    }

    public MutableLiveData<String> getSuccessMsg() {
        return successMsg;
    }

    public void setSuccessMsg(String successMsg) {
        this.successMsg.setValue(successMsg);
    }
}
