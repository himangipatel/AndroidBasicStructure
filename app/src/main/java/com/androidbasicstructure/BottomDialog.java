package com.androidbasicstructure;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;
import android.view.ViewGroup;

/**
 * Base class for {@link android.app.Dialog}s styled as a bottom sheet.
 */
public class BottomDialog extends BottomSheetDialog {

    public BottomDialog(@NonNull Context context) {
        super(context);
    }


    @Override
    public void setContentView(@NonNull View view) {
        super.setContentView(view);
        ((View) view.getParent())
                .setBackgroundColor(getContext().getResources().getColor(android.R.color.transparent));

        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) ((View) view.getParent())
                .getLayoutParams();
        layoutParams.setMargins(50, 0, 50, 0);
        ((View) view.getParent()).setLayoutParams(layoutParams);
    }

    @Override
    public void setContentView(@NonNull View view, ViewGroup.LayoutParams params) {
        super.setContentView(view);
    }

}