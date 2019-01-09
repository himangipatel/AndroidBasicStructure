package com.androidbasicstructure.models;

import android.util.Log;

/**
 * Created by Himangi on 8/6/18
 */
public class UserModel {

    private String email;
    private String password;
    private String name;

    public UserModel() {

    }

    public UserModel(String name, String email, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void onUsernameTextChanged(CharSequence text) {
        Log.d("UserName", text.toString());
    }

}
