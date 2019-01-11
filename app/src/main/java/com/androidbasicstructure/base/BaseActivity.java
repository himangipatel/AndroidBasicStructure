package com.androidbasicstructure.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.androidbasicstructure.BottomDialog;
import com.androidbasicstructure.R;
import com.androidbasicstructure.annotations.Layout;
import com.androidbasicstructure.connection.constant.ApiParamConstant;
import com.androidbasicstructure.databinding.ActivityBaseBinding;
import com.androidbasicstructure.databinding.AppLoadingDialogBinding;
import com.androidbasicstructure.databinding.DialogPhotoSelectorBinding;
import com.androidbasicstructure.utils.AppUtils;
import com.imagepicker.FilePickUtils;
import com.imagepicker.LifeCycleCallBackManager;

import java.util.HashMap;
import java.util.Locale;

import static com.imagepicker.FilePickUtils.CAMERA_PERMISSION;
import static com.imagepicker.FilePickUtils.STORAGE_PERMISSION_IMAGE;

/**
 * Created by Himangi on 7/6/18
 */
public class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    public Toolbar toolbar;

    protected ActivityBaseBinding activityBaseBinding;
    private Dialog progressDialog;
    private FilePickUtils filePickUtils;

    private BottomDialog bottomSheetDialog;
    private LifeCycleCallBackManager lifeCycleCallBackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getLayout() != null) {
            activityBaseBinding = DataBindingUtil.setContentView(this, R.layout.activity_base);
            activityBaseBinding.toolbar.tvToolbarLeft.setOnClickListener(this);
        }
        setSupportActionBar(toolbar);
        clickableViews(new View[]{activityBaseBinding.toolbar.tvToolbarLeft, activityBaseBinding.toolbar.tvToolbarRight,
                activityBaseBinding.toolbar.tvToolbarTitle});
        overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
    }

    protected <T extends ViewDataBinding> T getBinding() {
        return DataBindingUtil.inflate(getLayoutInflater(), getLayout().value(),
                activityBaseBinding.layoutContainer, true);
    }

    public Layout getLayout() {
        return getClass().getAnnotation(Layout.class);
    }

    protected void clickableViews(View[] views) {
        for (View view : views) {
            view.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvToolbarLeft:
               // onBackPressed();
                break;
        }
    }

    public void showProgressDialog(boolean show) {
        if (show) {
            showProgressDialog();
        } else {
            hideProgressDialog();
        }
    }

    public void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new Dialog(this);
        } else {
            return;
        }

        AppLoadingDialogBinding binding = DataBindingUtil.inflate(getActivity().getLayoutInflater(),
                R.layout.app_loading_dialog, null, false);

        Animation a1 = AnimationUtils.loadAnimation(this, R.anim.progress_anim);
        a1.setDuration(1500);
        binding.imageView1.startAnimation(a1);

        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.setContentView(binding.getRoot());
        Window window = progressDialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawable(ContextCompat.getDrawable(this, android.R.color.transparent));
        }
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    public final void hideProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    protected void setToolBarVisibility(int visibility) {
        activityBaseBinding.toolbar.toolbar.setVisibility(visibility);
    }

    protected void setTootBartVLeft(String title) {
        activityBaseBinding.toolbar.tvToolbarLeft.setVisibility(View.VISIBLE);
        activityBaseBinding.toolbar.tvToolbarLeft.setText(title);
    }

    protected void setTootBartVRight(String title) {
        activityBaseBinding.toolbar.tvToolbarRight.setVisibility(View.VISIBLE);
        activityBaseBinding.toolbar.tvToolbarRight.setText(title);
        activityBaseBinding.toolbar.tvToolbarRight.setOnClickListener(this);
    }


    protected void setTootBartVTitle(String title) {
        activityBaseBinding.toolbar.tvToolbarTitle.setVisibility(View.VISIBLE);
        activityBaseBinding.toolbar.tvToolbarTitle.setText(title);
    }


    public HashMap<String, String> getDefaultParameter() {
        HashMap<String, String> params = new HashMap<>();
        params.put(ApiParamConstant.DEVICE_TOKEN, AppUtils.getDeviceId(getActivity()));

        params.put(ApiParamConstant.DEVICE_TYPE, "android");

        return params;
    }

    protected void updateResources(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Resources res = context.getResources();
        Configuration config = new Configuration(res.getConfiguration());
        config.locale = locale;
        res.updateConfiguration(config, res.getDisplayMetrics());
    }

    protected void showAlerterBar(Activity activity, String message) {
        AppUtils.showToast(activity, message);
    }

    public void showImagePickerDialog(FilePickUtils.OnFileChoose onFileChoose) {
        filePickUtils = new FilePickUtils(this, onFileChoose);
        lifeCycleCallBackManager = filePickUtils.getCallBackManager();
        DialogPhotoSelectorBinding binding = DataBindingUtil.inflate(getActivity().getLayoutInflater(),
                R.layout.dialog_photo_selector, null, false);
        bottomSheetDialog = new BottomDialog(BaseActivity.this);
        bottomSheetDialog.setContentView(binding.getRoot());

        binding.tvCamera.setOnClickListener(onCameraListener);
        binding.tvGallery.setOnClickListener(onGalleryListener);
        bottomSheetDialog.show();
    }

    public void dismissImagePickerDialog() {
        bottomSheetDialog.dismiss();
    }


    private View.OnClickListener onCameraListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            filePickUtils.requestImageCamera(CAMERA_PERMISSION, false, true);
        }
    };

    private View.OnClickListener onGalleryListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            filePickUtils.requestImageGallery(STORAGE_PERMISSION_IMAGE, false, true);
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.activity_open_scale, R.anim.activity_close_translate);
    }


    public Activity getActivity() {
        return this;
    }

    protected void logOut() {
        AppUtils.logOut(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (lifeCycleCallBackManager != null) {
            lifeCycleCallBackManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (lifeCycleCallBackManager != null) {
            lifeCycleCallBackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

}
