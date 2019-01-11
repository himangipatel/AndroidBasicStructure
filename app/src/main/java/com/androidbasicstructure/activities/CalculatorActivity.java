package com.androidbasicstructure.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.androidbasicstructure.R;
import com.androidbasicstructure.annotations.Layout;
import com.androidbasicstructure.base.MVVMActivity;
import com.androidbasicstructure.databinding.ActivityCalculatorBinding;
import com.androidbasicstructure.viewmodel.SharedViewModel;

@Layout(R.layout.activity_calculator)
public class CalculatorActivity extends MVVMActivity<SharedViewModel> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityCalculatorBinding binding = getBinding();
        getViewModel().getInputNumber().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                binding.tvShowInput.setText("You input is".concat(": ").concat(String.valueOf(integer)));
            }
        });
    }

    @NonNull
    @Override
    public SharedViewModel createViewModel() {
        return ViewModelProviders.of(this).get(SharedViewModel.class);
    }


}
