package com.mzw.zhihudaily;

import android.app.Application;
import android.content.Context;

import com.mzw.zhihudaily.util.L;

/**
 * Created by M on 2015/11/9.
 */
public class App extends Application {
    private static final String TAG = L.makeLogTag(App.class);

    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
    }

    public static Context getContext() {
        return sContext;
    }
}
