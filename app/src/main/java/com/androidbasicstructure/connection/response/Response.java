package com.androidbasicstructure.connection.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Himangi Patel on 6/12/2017.
 */

public class Response {

    @SerializedName("status")
    private boolean status;
    @SerializedName("message")
    private String message;
    @SerializedName("authentication")
    private boolean authentication;
    @SerializedName("request_id")
    private String request_id;
    @SerializedName("access_token")
    private String access_token;
    @SerializedName("is_verified")
    private String is_verified; // 1-verify,2-not verify

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isAuthentication() {
        return authentication;
    }

    public void setAuthentication(boolean authentication) {
        this.authentication = authentication;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getIs_verified() {
        return is_verified;
    }

    public void setIs_verified(String is_verified) {
        this.is_verified = is_verified;
    }
}
