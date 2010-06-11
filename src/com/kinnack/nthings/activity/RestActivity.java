package com.kinnack.nthings.activity;

import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.PowerManager;
import android.os.Vibrator;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;

import static android.os.PowerManager.*;

import com.kinnack.nthings.R;

public class RestActivity extends Activity {
    private long rest_milliseconds = -1;
    private float warning_percent = 0.17f;
    private long millisRestLeft = 0;
    private CountDownTimer countDownTimer;
    private PowerManager.WakeLock wakeLock;
    
    public static final String REST_LENGTH = "com.kinnack.nthings.rest_length"; 
    public static final String WARNING_PERCENT = "com.kinnack.nthings.warning_percent";
    public static final String SETS_DONE = "com.kinnack.nthings.sets_done";
    
    private static final String REST_DATA = "rest_data";
    private static final String REST_RESUME_TIME_MILLIS = "rest_resume";
    public static final String SETS_TO_GO = "com.kinnack.nthings.sets_to_go";
    
    @Override
    protected void onCreate(Bundle savedInstanceState_) {
        super.onCreate(savedInstanceState_);
        requestWindowFeature(Window.FEATURE_PROGRESS);
        setContentView(R.layout.rest);
        Bundle extras = getIntent().getExtras();
        rest_milliseconds = extras.getInt(REST_LENGTH);
        int setsToGo = extras.getInt(SETS_TO_GO);
        ((TextView)findViewById(R.id.CompletionRatio)).setText(setsToGo
                                                                +" more set"
                                                                +(setsToGo > 1?"s":""));
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        
        keepScreenAlive();
        
        determineRestTimeRemaining();

        if (rest_milliseconds <= 0) { finish(); }
        
        final TextView timeLeft = (TextView) findViewById(R.id.TimeLeft);
        timeLeft.setText(rest_milliseconds/1000+"s");
        
        createAndStartCountDownTimer(timeLeft);
    }

    private void keepScreenAlive() {
        PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
        wakeLock = pm.newWakeLock(SCREEN_DIM_WAKE_LOCK|ON_AFTER_RELEASE, "RestActivity.screenOn");
        wakeLock.acquire();
    }
    
    /**
     * 
     */
    private void determineRestTimeRemaining() {
        if (rest_milliseconds == 0) { rest_milliseconds = 60000;}
        if (rest_milliseconds == -1) {loadRestMillisecondsFromStorage();}
    }

    /**
     * @param timeLeft
     */
    private void createAndStartCountDownTimer(final TextView timeLeft) {
        countDownTimer = new CountDownTimer(rest_milliseconds,1000) {
            private boolean warned = false;
            @Override
            public void onTick(long millisUntilFinished_) {
                millisRestLeft = millisUntilFinished_;
                timeLeft.setText(millisRestLeft/1000+"s");
                getWindow().setFeatureInt(Window.FEATURE_PROGRESS, (int)(1-millisRestLeft/(rest_milliseconds))*10000);
                if (millisRestLeft <= 10999 && !warned) {
                    Log.i("nthings:RestActivity", "Ending in 10 seconds");
                    timeLeft.setTextColor(Color.RED);
                    
                    Vibrator vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                    vibrator.vibrate(new long[]{0,500,250,500}, -1);
                    warned = true;
                }
            }
            
            @Override
            public void onFinish() {
                finish();
            }
        }.start();
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        Log.d("DGMT!RestActivity", "Pausing Rest activity");
        Editor preferenceEditor = getSharedPreferences(REST_DATA, Context.MODE_PRIVATE).edit();
        preferenceEditor.putLong(REST_RESUME_TIME_MILLIS, new Date().getTime()+millisRestLeft);
        preferenceEditor.commit();
        if (countDownTimer != null) { countDownTimer.cancel(); }
        wakeLock.release();
        rest_milliseconds = -1;
    }
    
    private void loadRestMillisecondsFromStorage() {
        Log.d("DGMT!RestActivity", "Loading Rest time from storage");
        SharedPreferences prefs = getSharedPreferences(REST_DATA, Context.MODE_PRIVATE);
        long restEndTimeMillis = prefs.getLong(REST_RESUME_TIME_MILLIS, new Date().getTime()+60000);
        rest_milliseconds = restEndTimeMillis - new Date().getTime();
        
    }
}
