package com.androidbasicstructure.interfaces;

import android.widget.DatePicker;
import android.widget.TimePicker;

/**
 * Created by Himangi on 16/5/18
 */
public interface OnDateChange {
    void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth);
    void onTimeSet(TimePicker view, int hourOfDay, int minute);
}
