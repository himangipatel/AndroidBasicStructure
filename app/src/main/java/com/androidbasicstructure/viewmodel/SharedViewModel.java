package com.androidbasicstructure.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.androidbasicstructure.base.BaseViewModel;

/**
 * Created by Himangi on 11/1/19
 */
public class SharedViewModel extends BaseViewModel {

    private MutableLiveData<Integer> inputNumber = new MutableLiveData<>();

    public SharedViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Integer> getInputNumber() {
        return inputNumber;
    }

    public void setInputNumber(int inputNumber) {
        this.inputNumber.setValue(inputNumber);
    }
}
