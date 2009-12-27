package com.kinnack.nthings.activity;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;

import com.kinnack.nthings.R;

public class RestActivity extends Activity {
    private int rest_milliseconds;
    private float warning_percent = 0.17f;
    
    public static final String REST_LENGTH = "com.kinnack.nthings.rest_length"; 
    public static final String WARNING_PERCENT = "com.kinnack.nthings.warning_percent";
    
    @Override
    protected void onCreate(Bundle savedInstanceState_) {
        super.onCreate(savedInstanceState_);
        requestWindowFeature(Window.FEATURE_PROGRESS);
        setContentView(R.layout.rest);
        Bundle extras = getIntent().getExtras();
        rest_milliseconds = extras.getInt(REST_LENGTH);
        if (rest_milliseconds == 0) { rest_milliseconds = 60000;}
        final TextView timeLeft = (TextView) findViewById(R.id.TimeLeft);
        timeLeft.setText(rest_milliseconds/1000+"s");
        
        new CountDownTimer(rest_milliseconds,1000) {
            private boolean warned = false;
            @Override
            public void onTick(long millisUntilFinished_) {
                timeLeft.setText(millisUntilFinished_/1000+"s");
                getWindow().setFeatureInt(Window.FEATURE_PROGRESS, (int)(1-millisUntilFinished_/(rest_milliseconds))*10000);
                if (millisUntilFinished_/1000 <= 10 && !warned) {
                    Log.i("nthings:RestActivity", "Ending in 10 seconds");
                    timeLeft.setTextColor(Color.RED);
                    NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    Notification vibration = new Notification();
                    vibration.defaults |= Notification.DEFAULT_VIBRATE;
                    vibration.vibrate = new long[]{0,500,250,500};
                    notificationManager.notify(1, vibration);
                    warned = true;
                }
            }
            
            @Override
            public void onFinish() {
                finish();
            }
        }.start();
    }
}
