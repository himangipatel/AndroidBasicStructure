package com.androidbasicstructure.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidbasicstructure.R;
import com.androidbasicstructure.annotations.Layout;
import com.androidbasicstructure.utils.AppUtils;
import com.imagepicker.FilePickUtils;

import java.util.HashMap;

import butterknife.ButterKnife;

/**
 * Created by Himangi on 7/6/18
 */
public class BaseFragment extends Fragment {
    private Dialog progressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = null;
        if (getLayout() != null) {
            view = inflater.inflate(getLayout().value(), container, false);
            ButterKnife.bind(this, view);
        }
        getActivity().overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
        return view;
    }

    public Layout getLayout() {
        return getClass().getAnnotation(Layout.class);
    }

    public void updateResources(Context context, String language) {
        if (getActivity() != null) {
            ((BaseActivity) getActivity()).updateResources(context, language);
        }
    }

    public void showBottomSheetDialog(FilePickUtils.OnFileChoose onFileChoose) {
        if (getActivity() != null) {
            ((BaseActivity) getActivity()).showImagePickerDialog(onFileChoose);
        }
    }

    public void dismissImagePickerDialog() {
        if (getActivity() != null) {
            ((BaseActivity) getActivity()).dismissImagePickerDialog();
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
        if (getActivity() != null) {
            ((BaseActivity) getActivity()).showProgressDialog(true);
        }
    }

    public void hideProgressDialog() {
        if (getActivity() != null) {
            ((BaseActivity) getActivity()).hideProgressDialog();
        }
    }

    protected void setTootBartVRight(String title) {
        ((BaseActivity) getActivity()).setTootBartVRight(title);
    }

    protected void setTootBartVLeft(String title) {
        ((BaseActivity) getActivity()).setTootBartVLeft(title);
    }

    protected void setTootBartVTitle(String title) {
        ((BaseActivity) getActivity()).setTootBartVTitle(title);
    }

    public HashMap<String, String> getDefaultParameter() {
        if (getActivity() != null) {
            return ((BaseActivity) getActivity()).getDefaultParameter();
        }
        return new HashMap<>();
    }


    protected void showAlerterBar(Activity activity, String message) {
        AppUtils.showToast(activity, message);
    }
}
