package com.sepjani.unofficiallivecodingtv.chat;

import android.os.Binder;

import java.lang.ref.WeakReference;

/**
 * Created by Valeriy Strucovskiy on 6/4/2016.
 */
public class LocalBinder<S> extends Binder {
    private final WeakReference<S> mService;

    public LocalBinder(final S service) {
        mService = new WeakReference<S>(service);
    }

    public S getService() {
        return mService.get();
    }

}