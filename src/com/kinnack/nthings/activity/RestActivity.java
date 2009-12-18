package com.kinnack.nthings.activity;

import com.kinnack.nthings.R;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

public class RestActivity extends Activity {
    private int rest_milliseconds;
    private float warning_percent = 0.17f;
    
    public static final String REST_LENGTH = "com.kinnack.nthings.rest_length"; 
    public static final String WARNING_PERCENT = "com.kinnack.nthings.warining_percent";
    
    @Override
    protected void onCreate(Bundle savedInstanceState_) {
        super.onCreate(savedInstanceState_);
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
                if (millisUntilFinished_ <= 10000 && !warned) {
                    Log.i("nthings:RestActivity", "Ending in 10 seconds");
                    timeLeft.setTextColor(Color.RED);
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
