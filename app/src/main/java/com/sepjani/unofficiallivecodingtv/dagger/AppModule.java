package com.sepjani.unofficiallivecodingtv.dagger;

import com.sepjani.unofficiallivecodingtv.LivecodingApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Valeriy Strucovskiy on 6/15/2016.
 */
@Module
public class AppModule {

    LivecodingApplication mApplication;

    public AppModule(LivecodingApplication application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    LivecodingApplication providesApplication() {
        return mApplication;
    }
}
