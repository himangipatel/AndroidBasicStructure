package com.androidbasicstructure.models;

import com.androidbasicstructure.connection.response.Response;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Himangi on 7/6/18
 */
public class LoginResponse extends Response{

    @SerializedName("user_id")
    private String userId = "";

    @SerializedName("user_first_name")
    private String userFirstName = "";

    @SerializedName("user_last_name")
    private String userLastName = "";

    @SerializedName("user_email")
    private String userEmail = "";

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
