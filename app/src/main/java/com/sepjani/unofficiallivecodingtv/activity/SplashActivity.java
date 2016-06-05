package com.sepjani.unofficiallivecodingtv.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Valeriy Strucovskiy on 6/4/2016.
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
