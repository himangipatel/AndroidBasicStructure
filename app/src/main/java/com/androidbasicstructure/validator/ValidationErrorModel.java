package com.androidbasicstructure.validator;

/**
 * Created by Himangi Patel on 6/12/2017.
 */

public class ValidationErrorModel {

    private int msg;
    private ValidationError error;

    public ValidationErrorModel() {

    }

    public ValidationErrorModel(int msg, ValidationError error) {
        this.msg = msg;
        this.error = error;
    }

    public int getMsg() {
        return msg;
    }

    public void setMsg(int msg) {
        this.msg = msg;
    }

    public ValidationError getError() {
        return error;
    }

    public void setError(ValidationError error) {
        this.error = error;
    }
}
