package com.androidbasicstructure.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.androidbasicstructure.R;
import com.androidbasicstructure.annotations.Layout;
import com.androidbasicstructure.base.MVVMFragment;
import com.androidbasicstructure.databinding.FragmentInputBinding;
import com.androidbasicstructure.viewmodel.SharedViewModel;

import java.util.Objects;

@Layout(R.layout.fragment_input)
public class InputFragment extends MVVMFragment<SharedViewModel> {

    private FragmentInputBinding binding;

    @NonNull
    @Override
    public SharedViewModel createViewModel() {
        return ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(SharedViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = (FragmentInputBinding) getBinding();

        binding.etInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().isEmpty()) {
                    getViewModel().setInputNumber(Integer.valueOf(editable.toString()));
                }
            }
        });
    }
}
