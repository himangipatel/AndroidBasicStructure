package com.androidbasicstructure.adapter;

import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.androidbasicstructure.base.BaseBindingListAdapter;
import com.androidbasicstructure.base.BaseBindingViewHolder;
import com.androidbasicstructure.databinding.ItemAdapterSampleBinding;
import com.androidbasicstructure.models.User;

/**
 * Created by Himangi on 9/1/19
 */
public class UserListAdapter extends BaseBindingListAdapter<User> {

    public UserListAdapter() {
        super(User.userItemCallback);
    }

    @Override
    protected ViewDataBinding bind(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return ItemAdapterSampleBinding.inflate(inflater, parent, false);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseBindingViewHolder holder, int position) {
        ItemAdapterSampleBinding binding = (ItemAdapterSampleBinding) holder.binding;
        binding.setUser(getItem(position));
    }


}
