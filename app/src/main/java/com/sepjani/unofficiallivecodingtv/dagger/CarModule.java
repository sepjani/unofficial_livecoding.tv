package com.sepjani.unofficiallivecodingtv.dagger;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Valeriy Strucovskiy on 6/14/2016.
 */

@Module
public class CarModule {

    String name;


    public CarModule(String name) {
        this.name = name;
    }

    @Provides
    public Car getCar() {
        return new Car();
    }

    @Provides
    public Engine getEngine() {
        return new Engine();
    }

    @Provides
    public Wheel getWheel() {
        return new Wheel();
    }
}
