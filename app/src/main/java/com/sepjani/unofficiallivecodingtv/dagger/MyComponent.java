package com.sepjani.unofficiallivecodingtv.dagger;

import com.sepjani.unofficiallivecodingtv.activity.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Valeriy Strucovskiy on 6/15/2016.
 */

@Singleton
@Component(modules = { AppModule.class, CarModule.class})
public interface MyComponent {
    void inject(MainActivity activity);

    void inject(Car car);
}
