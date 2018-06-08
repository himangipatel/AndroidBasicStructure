package com.androidbasicstructure.connection.constant;

public class ApiRequestUrl {

    public static final String LOGIN = "login";
    private String value;

    public ApiRequestUrl(String value) {
        this.value = ApiParamConstant.BASE_URL + value;
    }

    public String getValue() {
        return value;
    }
}