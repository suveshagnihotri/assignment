package com.nira.android.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.nira.android.R;
import com.nira.android.utils.ActivityRouter;

/**
 * Created by Suvesh on 20/08/2017 AD.
 */

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                navigateToNext();
                finish();
            }
        }, 3000);
    }

    private void navigateToNext(){
        ActivityRouter.navigateToLogin(SplashActivity.this);
    }
}
