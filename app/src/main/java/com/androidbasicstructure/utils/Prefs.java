package com.androidbasicstructure.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import com.androidbasicstructure.base.App;

import java.util.Map;
import java.util.Set;

/**
 * @author Alejandro Rodriguez <https://github.com/Alexrs95/Prefs>
 * <p/>
 * Wrapper over the Android Preferences which provides a fluid syntax
 */
public class Prefs {

    private static final String TAG = "Prefs";

    private static Prefs singleton = null;

    private static SharedPreferences preferences;

    private static SharedPreferences.Editor editor;

    Prefs(Context context) {
        preferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.apply();
    }

    public static Prefs with() {
        if (singleton == null) {
            singleton = new Builder(App.getAppContext()).build();
        }
        return singleton;
    }

    public void save(String key, boolean value) {
        editor.putBoolean(key, value).apply();
    }

    public void save(String key, String value) {
        editor.putString(key, value).apply();
    }

    public void save(String key, int value) {
        editor.putInt(key, value).apply();
    }

    public void save(String key, float value) {
        editor.putFloat(key, value).apply();
    }

    public void save(String key, long value) {
        editor.putLong(key, value).apply();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void save(String key, Set<String> value) {
        editor.putStringSet(key, value).apply();
    }

    public boolean getBoolean(String key, boolean defValue) {
        return preferences.getBoolean(key, defValue);
    }

    public String getString(String key, String defValue) {
        return preferences.getString(key, defValue);
    }

    public int getInt(String key, int defValue) {
        return preferences.getInt(key, defValue);
    }

    public float getFloat(String key, float defValue) {
        return preferences.getFloat(key, defValue);
    }

    public long getLong(String key, long defValue) {
        return preferences.getLong(key, defValue);
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public Set<String> getStringSet(String key, Set<String> defValue) {
        return preferences.getStringSet(key, defValue);
    }

    public Map<String, ?> getAll() {
        return preferences.getAll();
    }

    public void remove(String key) {
        editor.remove(key).apply();
    }

    public void clearAll() {
        editor.clear();
        editor.apply();
    }


    private static class Builder {

        private final Context context;

        Builder(Context context) {
            if (context == null) {
                throw new IllegalArgumentException("Context must not be null.");
            }
            this.context = context.getApplicationContext();
        }

        Prefs build() {
            return new Prefs(context);
        }
    }
}
