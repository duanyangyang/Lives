package com.bwie.test.jufanlive.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

import com.bwie.test.jufanlive.R;

public class LogoActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);
        CountDownTimer cd=new CountDownTimer(5000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                Intent mIntent=new Intent(LogoActivity.this,LoginActivity.class);
                startActivity(mIntent);
                finish();
            }
        }.start();
    }
}
