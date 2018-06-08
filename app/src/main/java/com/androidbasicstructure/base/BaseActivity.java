package com.androidbasicstructure.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidbasicstructure.BottomDialog;
import com.androidbasicstructure.R;
import com.androidbasicstructure.annotations.Layout;
import com.androidbasicstructure.connection.constant.ApiParamConstant;
import com.androidbasicstructure.utils.AppUtils;
import com.imagepicker.FilePickUtils;
import com.imagepicker.LifeCycleCallBackManager;

import java.util.HashMap;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

import static com.imagepicker.FilePickUtils.CAMERA_PERMISSION;
import static com.imagepicker.FilePickUtils.STORAGE_PERMISSION_IMAGE;

/**
 * Created by Himangi on 7/6/18
 */
public class BaseActivity extends AppCompatActivity {

    @Nullable
    @BindView(R.id.toolbar)
    public Toolbar toolbar;
    @Nullable
    @BindView(R.id.ivToolbarLeft)
    public ImageView ivToolbarLeft;
    @Nullable
    @BindView(R.id.tvToolbarLeft)
    public TextView tvToolbarLeft;
    @Nullable
    @BindView(R.id.tvToolbarRight)
    public TextView tvToolbarRight;
    @Nullable
    @BindView(R.id.tvToolbarTitle)
    public TextView tvToolbarTitle;
    private Dialog progressDialog;
    private FilePickUtils filePickUtils;

    private BottomDialog bottomSheetDialog;
    private LifeCycleCallBackManager lifeCycleCallBackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);
        if (getLayout() != null) {
            setContentView(getLayout().value());
            ButterKnife.bind(this);
        }
        overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);

    }

    public Layout getLayout() {
        return getClass().getAnnotation(Layout.class);
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
        View view = LayoutInflater.from(this).inflate(R.layout.app_loading_dialog,
                null, false);

        ImageView imageView1 = view.findViewById(R.id.imageView2);
        Animation a1 = AnimationUtils.loadAnimation(this, R.anim.progress_anim);
        a1.setDuration(1500);
        imageView1.startAnimation(a1);

        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.setContentView(view);
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
        assert toolbar != null;
        toolbar.setVisibility(visibility);
    }

    protected void setToolBariVLeft(int visibility) {
        assert ivToolbarLeft != null;
        ivToolbarLeft.setVisibility(visibility);
    }

    protected void setTootBartVLeft(String title) {
        assert tvToolbarLeft != null;
        tvToolbarLeft.setVisibility(View.VISIBLE);
        tvToolbarLeft.setText(title);
    }

    protected void setTootBartVRight(String title) {
        assert tvToolbarRight != null;
        tvToolbarRight.setVisibility(View.VISIBLE);
        tvToolbarRight.setText(title);
    }


    protected void setTootBartVTitle(String title) {
        assert tvToolbarTitle != null;
        tvToolbarTitle.setVisibility(View.VISIBLE);
        tvToolbarTitle.setText(title);
    }

    @Optional
    @OnClick({R.id.ivToolbarLeft, R.id.tvToolbarLeft})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivToolbarLeft:
            case R.id.tvToolbarLeft:
                onBackPressed();
                break;
        }
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
        View bottomSheetView = getLayoutInflater().inflate(R.layout.dialog_photo_selector, null);
        bottomSheetDialog = new BottomDialog(BaseActivity.this);
        bottomSheetDialog.setContentView(bottomSheetView);
        final TextView tvCamera = bottomSheetView.findViewById(R.id.tvCamera);
        final TextView tvGallery = bottomSheetView.findViewById(R.id.tvGallery);
        tvCamera.setOnClickListener(onCameraListener);
        tvGallery.setOnClickListener(onGalleryListener);
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
