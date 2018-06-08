package com.androidbasicstructure.base;

import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public class BaseBindingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public final ViewDataBinding binding;
    private final ClickListener clickListener;

    public BaseBindingViewHolder(ViewDataBinding binding, @NonNull ClickListener clickListener) {
        super(binding.getRoot());
        binding.getRoot().setOnClickListener(this);
        this.binding = binding;
        this.clickListener = clickListener;
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                clickViews(BaseBindingViewHolder.this);
            }
        }, 1000);
    }

    private void clickViews(BaseBindingViewHolder bindingViewHolder) {
        View view = bindingViewHolder.binding.getRoot();
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                viewGroup.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        clickListener.onViewClick(getAdapterPosition(), view);
                    }
                });
            }
        }
    }

    @Override
    public void onClick(View v) {
        clickListener.onViewClick(getAdapterPosition(), v);
    }

    public interface ClickListener {
        void onViewClick(int position, View view);
    }
}