package com.androidbasicstructure.view;

import com.androidbasicstructure.base.BaseView;
import com.androidbasicstructure.validator.ValidationErrorModel;

public interface ValidationErrorView<T> extends BaseView<T> {
    void onValidationError(ValidationErrorModel validationErrorModel);
}