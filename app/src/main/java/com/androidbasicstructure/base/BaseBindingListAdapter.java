package com.androidbasicstructure.base;

import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidbasicstructure.interfaces.ItemClickListener;

/**
 * Created by Himangi on 9/1/19
 */
public abstract class BaseBindingListAdapter<I> extends ListAdapter<I, BaseBindingViewHolder>
        implements BaseBindingViewHolder.ClickListener {

    private ItemClickListener<I> itemClickListener;

    protected BaseBindingListAdapter(@NonNull DiffUtil.ItemCallback<I> diffCallback) {
        super(diffCallback);


    }

    @Override
    public void onViewClick(int position, View view) {
        if (itemClickListener != null && getItemCount() != 0) {
            itemClickListener.onClick(getItem(position), position, view);
        }
    }

    @NonNull
    @Override
    public BaseBindingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = bind(inflater, parent, viewType);
        return new BaseBindingViewHolder(binding, this);
    }

    protected abstract ViewDataBinding bind(LayoutInflater inflater, ViewGroup parent, int viewType);


    public BaseBindingListAdapter setItemClickListener(ItemClickListener<I> itemClickListener) {
        this.itemClickListener = itemClickListener;
        return this;
    }

}
