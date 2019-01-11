package com.androidbasicstructure.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidbasicstructure.BuildConfig;
import com.androidbasicstructure.R;
import com.androidbasicstructure.interfaces.OnSearchData;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

import static android.text.Spanned.SPAN_INCLUSIVE_INCLUSIVE;
import static android.text.TextUtils.isEmpty;

/**
 * Created by Himangi Patel on 6/12/2017.
 */

public class AppUtils {

    public static RequestOptions getGlidePlaceHolders() {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.ic_profile_place_holder);
        requestOptions.error(R.drawable.ic_profile_place_holder);
        return requestOptions;
    }

    public static void logOut(Context context) {
        Prefs.with().clearAll();
    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void showSnackBar(Activity activity, @StringRes int mainTextString, final int actionStringId,
                                    View.OnClickListener listener) {
        Snackbar.make(activity.findViewById(android.R.id.content),
                mainTextString,
                Snackbar.LENGTH_INDEFINITE)
                .setAction(activity.getString(actionStringId), listener).show();
    }

    public static void requestEdittextFocus(Activity activity, EditText view) {
        view.requestFocus();
//        showKeyboard(activity, view);
    }

    public static RequestBody getRequestBody(String value) {
        return RequestBody.create(MediaType.parse("multipart/form-data"), value);
    }

    public static String getDeviceId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static String SHA1(String text)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        md.update(text.getBytes("iso-8859-1"), 0, text.length());
        byte[] sha1hash = md.digest();
        return convertToHex(sha1hash);
    }

    public static AlertDialog createAlertDialog(Activity activity, String message, String positiveText,
                                                String negativeText, DialogInterface.OnClickListener mDialogClick) {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(activity).setPositiveButton(positiveText,
                        mDialogClick).setNegativeButton(negativeText, mDialogClick).setMessage(message);
        return builder.create();
    }

    private static String convertToHex(byte[] data) {
        StringBuilder buf = new StringBuilder();
        for (byte b : data) {
            int halfbyte = (b >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                buf.append((0 <= halfbyte) && (halfbyte <= 9) ? (char) ('0' + halfbyte)
                        : (char) ('a' + (halfbyte - 10)));
                halfbyte = b & 0x0F;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }

    public static String getText(TextView textView) {
        return textView.getText().toString().trim();
    }

    public static boolean isEmptyText(TextView textView) {
        return isEmpty(getText(textView));
    }

    public static boolean hasInternet(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (!(networkInfo != null && networkInfo.isConnectedOrConnecting())) {
            showToast(context, "No Internet Connection");
        }
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    public static void showKeyboard(Activity activity, EditText view) {

        Context context = activity;
        try {
            if (context != null) {
                InputMethodManager inputManager =
                        (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
            }
        } catch (Exception e) {
            Logger.withTag("Exception on  show").log(e.toString());
        }
    }

    public static void hideKeyboard(Activity ctx) {
        if (ctx.getCurrentFocus() != null) {
            InputMethodManager imm =
                    (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(ctx.getCurrentFocus().getWindowToken(), 0);
        }
    }

    public static File getWorkingDirectory() {
        File directory =
                new File(Environment.getExternalStorageDirectory(), BuildConfig.APPLICATION_ID);
        if (!directory.exists()) {
            directory.mkdir();
        }
        return directory;
    }


    public static float dp2px(Resources resources, float dp) {
        final float scale = resources.getDisplayMetrics().density;
        return dp * scale + 0.5f;
    }

    public static float sp2px(Resources resources, float sp) {
        final float scale = resources.getDisplayMetrics().scaledDensity;
        return sp * scale;
    }

    public static void expand(final View v) {
        v.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final int targetHeight = v.getMeasuredHeight();

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? ViewGroup.LayoutParams.WRAP_CONTENT
                        : (int) (targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int) (targetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    public static void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    v.setVisibility(View.GONE);
                } else {
                    v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int) (initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    @SuppressLint("MissingPermission")
    public static void openCallIntent(Activity activity, String phone) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
        activity.startActivity(intent);
    }

    public static File saveImageToGallery(Context context, Bitmap bitmap) {
        File photo =
                new File(getWorkingDirectory().getAbsolutePath(),
                        SystemClock.currentThreadTimeMillis() + ".jpg");
        try {

            FileOutputStream fos = new FileOutputStream(photo.getPath());
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();
            if (photo.exists()) {
                ContentValues values = new ContentValues();

                values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
                values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
                values.put(MediaStore.MediaColumns.DATA, photo.getAbsolutePath());

                context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                showToast(context, "Image saved to Gallery");
            }
            return photo;
        } catch (Exception e) {
            Log.e("Picture", "Exception in photoCallback", e);
        }
        return null;
    }

    public static Uri shareImage(Context context, Bitmap bitmap) {
        File photo =
                new File(getWorkingDirectory().getAbsolutePath(),
                        SystemClock.currentThreadTimeMillis() + ".jpg");
        try {

            FileOutputStream fos = new FileOutputStream(photo.getPath());
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();
            if (photo.exists()) {
                ContentValues values = new ContentValues();

                values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
                values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
                values.put(MediaStore.MediaColumns.DATA, photo.getAbsolutePath());

                return context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            }
        } catch (Exception e) {
            Log.e("Picture", "Exception in photoCallback", e);
        }
        return null;
    }


    public static boolean isDigitsOnly(String number) {
        return TextUtils.isDigitsOnly(number);
    }


    public static CharSequence getSpannableText(Context context, String date) {
        String[] each = date.split(" ");

        int size12 = context.getResources().getDimensionPixelSize(R.dimen._12sdp);
        int size14 = context.getResources().getDimensionPixelSize(R.dimen._18sdp);

        SpannableString span1 = new SpannableString(each[0]);
        SpannableString span2 = new SpannableString(each[1]);

        span1.setSpan(new AbsoluteSizeSpan(size14), 0, each[0].length(), SPAN_INCLUSIVE_INCLUSIVE);
        span2.setSpan(new AbsoluteSizeSpan(size12), 0, each[1].length(), SPAN_INCLUSIVE_INCLUSIVE);

        return TextUtils.concat(span1, "\n", span2);
    }

    public static PublishSubject<String> onSearchData(final OnSearchData onSearchData) {
        PublishSubject<String> onSearchSubject = PublishSubject.create();
        onSearchSubject
                .debounce(1000, TimeUnit.MILLISECONDS)
                .observeOn(Schedulers.io())
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        return s;
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        onSearchData.onNext(s);
                    }
                });
        return onSearchSubject;
    }

}
