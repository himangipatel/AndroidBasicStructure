package com.androidbasicstructure.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidbasicstructure.R;
import com.androidbasicstructure.annotations.Layout;
import com.androidbasicstructure.base.MVVMFragment;
import com.androidbasicstructure.databinding.FragmentOutputBinding;
import com.androidbasicstructure.viewmodel.SharedViewModel;

import java.util.Objects;

/**
 * Created by Himangi on 11/1/19
 */
@Layout(R.layout.fragment_output)
public class OutputFragment extends MVVMFragment<SharedViewModel> {

    private FragmentOutputBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = (FragmentOutputBinding) getBinding();
        getViewModel().getInputNumber().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                binding.tvOutput.setText(String.valueOf(integer));
            }
        });
    }

    @NonNull
    @Override
    public SharedViewModel createViewModel() {
        return ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(SharedViewModel.class);
    }
}
