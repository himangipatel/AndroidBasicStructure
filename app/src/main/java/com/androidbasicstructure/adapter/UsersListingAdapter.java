package com.androidbasicstructure.adapter;

import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.androidbasicstructure.base.BaseBindingAdapter;
import com.androidbasicstructure.base.BaseBindingViewHolder;
import com.androidbasicstructure.databinding.ItemAdapterSampleBinding;
import com.androidbasicstructure.models.User;

public class UsersListingAdapter extends BaseBindingAdapter<User> {

    @Override
    protected ViewDataBinding bind(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return ItemAdapterSampleBinding.inflate(inflater, parent, false);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseBindingViewHolder holder, int position) {
        ItemAdapterSampleBinding binding = (ItemAdapterSampleBinding) holder.binding;
        binding.setUser(getItems().get(position));
    }
}
