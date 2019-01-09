package com.androidbasicstructure.interfaces;

import android.view.View;

public interface ItemClickListener<I> {
        void onClick(I item, int position, View view);
    }