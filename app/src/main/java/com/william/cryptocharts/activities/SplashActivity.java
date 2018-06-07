package com.william.cryptocharts.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.william.cryptocharts.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by William on 2018-05-31.
 */

public class SplashActivity extends AppCompatActivity{
    private static int SPLASH_TIME_OUT = 5000;
    @BindView(R.id.splash_screen_date)
    TextView date;
    String currentDate;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat simpleFormat = new SimpleDateFormat("MMM/dd/yyyy");
        currentDate = simpleFormat.format(cal.getTime());

        date.setText(currentDate);
        new Handler().postDelayed(new Runnable(){ // Delay Splash Screen before starting Main Screen
            @Override
            public void run(){
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIME_OUT);

    }
}
