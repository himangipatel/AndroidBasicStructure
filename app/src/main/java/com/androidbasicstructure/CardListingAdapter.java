package com.androidbasicstructure;

import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.androidbasicstructure.base.BaseBindingAdapter;
import com.androidbasicstructure.base.BaseBindingViewHolder;
import com.androidbasicstructure.databinding.ItemAdapterSampleBinding;

public class CardListingAdapter extends BaseBindingAdapter<Object> {

    @Override
    protected ViewDataBinding bind(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return ItemAdapterSampleBinding.inflate(inflater, parent, false);
    }

    @Override
    public void onBindViewHolder(BaseBindingViewHolder holder, int position) {
        ItemAdapterSampleBinding binding = (ItemAdapterSampleBinding) holder.binding;
        binding.setUser(new UserModel("Himangi Patel"));
    }
}
