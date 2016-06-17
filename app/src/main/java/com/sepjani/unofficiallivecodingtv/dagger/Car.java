package com.sepjani.unofficiallivecodingtv.dagger;

import com.sepjani.unofficiallivecodingtv.LivecodingApplication;

import javax.inject.Inject;

/**
 * Created by Valeriy Strucovskiy on 6/14/2016.
 */
public class Car {

    @Inject
    public Engine engine;

    @Inject
    public Wheel wheel;

    public Car() {
        LivecodingApplication.getComponent().inject(this);
    }

    public String makeActionCar() {
        return "start car";
    }
}
