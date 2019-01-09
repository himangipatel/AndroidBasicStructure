package com.androidbasicstructure.base;

import android.app.Application;

/**
 * Created by Himangi on 7/6/18
 */
public class App extends Application {

    private static App app = null;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }

    public static App getAppContext() {
       return app;
    }
}
