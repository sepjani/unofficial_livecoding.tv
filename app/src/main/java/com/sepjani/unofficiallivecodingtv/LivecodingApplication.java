package com.sepjani.unofficiallivecodingtv;

import android.app.Application;
import android.content.Context;

/**
 * Created by Valeriy Strucovskiy on 4/24/2016.
 */
public class LivecodingApplication extends Application {

    public static final String TAG = "Android LiveCoding.tv";
    private static Context context;

    public void onCreate(){
        super.onCreate();
        LivecodingApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return LivecodingApplication.context;
    }
}
