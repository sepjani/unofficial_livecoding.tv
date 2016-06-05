package com.sepjani.unofficiallivecodingtv;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by Valeriy Strucovskiy on 6/4/2016.
 */
public class AppLoadingIndicator {

    private static ProgressDialog mDialog;

    public static void show(Context context) {
        mDialog = new ProgressDialog(context);
        mDialog.setMessage("Please wait...");
        mDialog.setCancelable(false);
        mDialog.show();
    }

    public static void hide() {
        if (mDialog != null) {
            mDialog.hide();
        }
    }
}
