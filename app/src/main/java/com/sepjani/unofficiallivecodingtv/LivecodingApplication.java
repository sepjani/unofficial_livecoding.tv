package com.sepjani.unofficiallivecodingtv;

import android.app.Application;
import android.content.Context;

import com.sepjani.unofficiallivecodingtv.dagger.CarModule;
import com.sepjani.unofficiallivecodingtv.dagger.DaggerMyComponent;
import com.sepjani.unofficiallivecodingtv.dagger.MyComponent;

/**
 * Created by Valeriy Strucovskiy on 4/24/2016.
 */
public class LivecodingApplication extends Application {

    public static final String TAG = "Android LiveCoding.tv";
    private static Context context;
    MyComponent component;
    private static LivecodingApplication app;

    public void onCreate() {
        super.onCreate();
        LivecodingApplication.context = getApplicationContext();
        this.app = this;


        component = DaggerMyComponent.builder()
                .carModule(new CarModule("t145"))
                .build();
    }

    public static MyComponent getComponent() {
        return LivecodingApplication.getApp().component;
    }

    public static LivecodingApplication getApp() {
        return app;
    }

    public static Context getAppContext() {
        return LivecodingApplication.context;
    }
}
