package com.androidbasicstructure.models;

import android.arch.lifecycle.ViewModel;

import java.util.List;


public class UsersViewModel extends ViewModel {

    private List<User> userList;

    public List<User> getUserList() {
        if (userList == null) {
            //userList = loadUsers();
        }
        return userList;
    }

//    private List<User> loadUsers() {
//        return DatabaseHandler.getInstance().getUserDao().getAllUsers();
//    }

    @Override
    protected void onCleared() {
        super.onCleared();
        userList = null;
    }
}