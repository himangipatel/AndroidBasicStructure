package com.androidbasicstructure.validator;

import android.widget.EditText;
import android.widget.Toast;
import com.androidbasicstructure.R;

import java.util.regex.Pattern;

/**
 * Created by Himangi Patel on 6/12/2017.
 */

public class Validator {

    public static final String EMAIL_PATTERN = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
            "@" +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
            "(" +
            "\\." +
            "[a-zA-Z]{2,3}" +
            ")+";



    public static final String WEBSITE_PATTERN =
            "^(http:\\/\\/|https:\\/\\/)?(www.)?([a-zA-Z0-9]+).[a-zA-Z0-9]*.[a-z]{3}.?([a-z]+)?$";

    public static final String PHONE_PATTERN = "^[+0-9][0-9]*$";

    //6-len alpha-numeric pattern with optional special characters
    public static final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";

    public static Toast toast = null;

    public static ValidationErrorModel generateError(int msg, ValidationError error) {
        return new ValidationErrorModel(msg, error);
    }

    private static boolean isValidPassword(String password) {
        return !Pattern.compile(PASSWORD_PATTERN).matcher(password).matches();
    }

    public static ValidationErrorModel validatePassword(String password) {
        if (isBlank(password)) {
            return new ValidationErrorModel(R.string.enter_password, ValidationError.PASSWORD);
        } else if (isValidPassword(password)) {
            return new ValidationErrorModel(R.string.invalid_password, ValidationError.PASSWORD);
        }
        return null;
    }


    public static ValidationErrorModel validateEmail(String email) {
        return isBlank(email) ?
                new ValidationErrorModel(R.string.enter_email, ValidationError.EMAIL)
                : !Pattern.compile(EMAIL_PATTERN).matcher(email).matches()
                ? new ValidationErrorModel(R.string.invalid_email, ValidationError.EMAIL)
                : null;
    }


    public static boolean isValidEmail(String email) {
        return Pattern.compile(EMAIL_PATTERN).matcher(email).matches();
    }

    public static boolean isBlank(String text) {
        return text == null || text.trim().length() == 0;
    }

    public static boolean isBlank(EditText editText) {
        return editText.getText() == null || editText.getText().toString().trim().length() == 0;
    }

    public static boolean validatePNumber(String number) {
        //return (number.length() >= 6 && number.length() <= 15);
        return number.length() == 10;
    }


    public static boolean validateNumber(String strNumber, int min, int max) {
        return strNumber.length() >= min && strNumber.length() <= max;
    }

}
