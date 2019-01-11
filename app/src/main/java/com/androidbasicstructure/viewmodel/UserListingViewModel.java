package com.androidbasicstructure.viewmodel;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.androidbasicstructure.base.App;
import com.androidbasicstructure.base.BaseViewModel;
import com.androidbasicstructure.db.DatabaseHandler;
import com.androidbasicstructure.models.User;

import java.util.List;
import java.util.concurrent.Callable;

import rx.Observable;
import rx.functions.Action1;

/**
 * Created by Himangi on 9/1/19
 */
public class UserListingViewModel extends BaseViewModel {

    private LiveData<List<User>> userList;
    private String name;

    public UserListingViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<User>> getUserList() {
        if (userList == null) {
            getUsersFromDatabase();
        }
        return userList;
    }


    private void getUsersFromDatabase() {

        Observable.fromCallable(new Callable<LiveData<List<User>>>() {
            @Override
            public LiveData<List<User>> call() {
                return DatabaseHandler.getInstance(App.getAppContext()).getUserDao().getAllUsers();
            }
        }).doOnNext(new Action1<LiveData<List<User>>>() {
            @Override
            public void call(LiveData<List<User>> users) {
                userList = users;
                name = "Himangi";
//                getView().onSuccess(users);
            }
        }).subscribe();

    }

    @Override
    protected void onCleared() {
        super.onCleared();

    }
}
