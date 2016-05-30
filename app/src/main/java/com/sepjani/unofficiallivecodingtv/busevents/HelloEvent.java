package com.sepjani.unofficiallivecodingtv.busevents;

/**
 * Created by Valeriy Strucovskiy on 5/26/2016.
 */
public class HelloEvent {

    String message;

    public HelloEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
