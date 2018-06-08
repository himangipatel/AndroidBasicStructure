package com.androidbasicstructure.utils;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.text.format.DateFormat;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.androidbasicstructure.interfaces.OnDateChange;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtils {

    public static final String DD_MM_YYYY = "dd/MM/yyyy";
    public static final String YYYY_DD_MM = "yyyy-MM-dd";
    public static final String DD_MMM_YYYY = "dd MMM yyyy";

    public static DatePickerDialog openDatePickerDialog(Context context, Calendar calendar,
                                                        final OnDateChange onDateChange) {

        return new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                onDateChange.onDateSet(view, year, monthOfYear, dayOfMonth);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
    }

    public static TimePickerDialog openTimePickerDialog(Context context, Calendar calendar,
                                                        final OnDateChange onDateChange) {
        return new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                onDateChange.onTimeSet(timePicker, selectedHour, selectedMinute);
            }
        }, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), true);
    }


    public static int getDaysBetweenTwoDates(Timestamp start, Timestamp end) {
        boolean negative = false;
        if (end.before(start)) {
            negative = true;
            Timestamp temp = start;
            start = end;
            end = temp;
        }

        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(start);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        GregorianCalendar calEnd = new GregorianCalendar();
        calEnd.setTime(end);
        calEnd.set(Calendar.HOUR_OF_DAY, 0);
        calEnd.set(Calendar.MINUTE, 0);
        calEnd.set(Calendar.SECOND, 0);
        calEnd.set(Calendar.MILLISECOND, 0);

        if (cal.get(Calendar.YEAR) == calEnd.get(Calendar.YEAR)) {
            if (negative) {
                return (calEnd.get(Calendar.DAY_OF_YEAR) - cal.get(Calendar.DAY_OF_YEAR)) * -1;
            }
            return calEnd.get(Calendar.DAY_OF_YEAR) - cal.get(Calendar.DAY_OF_YEAR);
        }

        int days = 0;
        while (calEnd.after(cal)) {
            cal.add(Calendar.DAY_OF_YEAR, 1);
            days++;
        }
        if (negative) {
            return days * -1;
        }
        return days;
    }

    public static int getDaysBetween(Timestamp end) {
        return getDaysBetweenTwoDates(new Timestamp(System.currentTimeMillis()), end);
    }


    public static boolean validTime(String dateTime) {
        Date reminderDate = DateUtils.getDateHour(dateTime);
        Date currentDate = Calendar.getInstance().getTime();
        return reminderDate.after(currentDate);
    }

    public static String get12HourTime(String time) {
        SimpleDateFormat displayFormat = new SimpleDateFormat("h:mm aa", Locale.getDefault());
        displayFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        SimpleDateFormat parseFormat = new SimpleDateFormat("H:mm", Locale.getDefault());
        parseFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = null;
        try {
            date = parseFormat.parse(time);
        } catch (ParseException e) {
            date = new Date();
        }
        return displayFormat.format(date);
    }


    public static String get24HourTime(String time) {

        SimpleDateFormat displayFormat = new SimpleDateFormat("H:mm", Locale.getDefault());
        displayFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        SimpleDateFormat parseFormat = new SimpleDateFormat("h:mm aa", Locale.getDefault());
        parseFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = null;
        try {
            date = parseFormat.parse(time);
        } catch (ParseException e) {
            date = new Date();
        }
        return displayFormat.format(date);
    }


    public static Date getDate(String d) {
        SimpleDateFormat format = new SimpleDateFormat(DD_MM_YYYY, Locale.getDefault());
        Date date = null;
        try {
            date = format.parse(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time);
        return DateFormat.format(DD_MM_YYYY, cal).toString();
    }

    private static Date getDateHour(String d) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        Date date = null;
        try {
            date = format.parse(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    public static String parseDate(String pFormat, String dFormat, String date) {
        SimpleDateFormat displayFormat = new SimpleDateFormat(dFormat, Locale.getDefault());
        displayFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        SimpleDateFormat parseFormat = new SimpleDateFormat(pFormat, Locale.getDefault());
        parseFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date d = null;
        try {
            d = parseFormat.parse(date);
        } catch (ParseException e) {
            d = new Date();
        }
        return displayFormat.format(d);
    }

    public static String getCalculatedDate(String startDate, int days) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat s = new SimpleDateFormat(DD_MM_YYYY, Locale.getDefault());
        Date date = null;
        try {
            date = s.parse(startDate);
        } catch (ParseException e) {
            date = new Date();
        }
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_YEAR, days);
        return s.format(new Date(cal.getTimeInMillis()));
    }

    public static ArrayList<String> getPreviousDays(int previousDays, int nextDays) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM", Locale.getDefault());
        ArrayList<String> daysList = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        for (int i = 0; i < previousDays; i++) {
            cal.add(Calendar.DAY_OF_MONTH, -1);

            String date = sdf.format(cal.getTime());
            daysList.add(date);
        }
        Collections.reverse(daysList);
        cal = Calendar.getInstance();
        String d = sdf.format(cal.getTime());
        daysList.add(d);

        for (int i = 0; i < nextDays; i++) {
            cal.add(Calendar.DAY_OF_MONTH, 1);
            String date = sdf.format(cal.getTime());
            daysList.add(date);
        }
        //  Collections.reverse(daysList);
        return daysList;
    }


}