package com.abc.wcapp;

import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        SystemClock.sleep(3000);
        startActivity(new Intent(SplashActivity.this,MainActivity.class));
        finish();
    }
}
