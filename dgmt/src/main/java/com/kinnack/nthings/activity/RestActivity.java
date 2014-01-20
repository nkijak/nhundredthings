package com.kinnack.nthings.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import com.kinnack.nthings.R;
import com.kinnack.nthings.helper.PrettyDateAndTime;
import com.kinnack.nthings.model.SoundAlert;

import java.util.Date;

import static android.os.PowerManager.ON_AFTER_RELEASE;
import static android.os.PowerManager.SCREEN_DIM_WAKE_LOCK;

public class RestActivity extends Activity {
    private long rest_milliseconds = -1;
    private float warning_percent = 0.17f;
    private long millisRestLeft = 0;
    private CountDownTimer countDownTimer;
    private PowerManager.WakeLock wakeLock;
    private SoundAlert soundAlert;
    
    public static final String REST_LENGTH = "com.kinnack.nthings.rest_length"; 
    public static final String WARNING_PERCENT = "com.kinnack.nthings.warning_percent";
    public static final String SETS_DONE = "com.kinnack.nthings.sets_done";
    
    private static final String REST_DATA = "rest_data";
    private static final String REST_RESUME_TIME_MILLIS = "rest_resume";
    public static final String SETS_TO_GO = "com.kinnack.nthings.sets_to_go";
    public static final String COUNT_TO_GO = "com.kinnack.nthings.count_to_go";
    
    @Override
    protected void onCreate(Bundle savedInstanceState_) {
        super.onCreate(savedInstanceState_);
        requestWindowFeature(Window.FEATURE_PROGRESS);
        setContentView(R.layout.rest);
        Bundle extras = getIntent().getExtras();
        rest_milliseconds = extras.getInt(REST_LENGTH);
        int setsToGo = extras.getInt(SETS_TO_GO);
        if (setsToGo > 1) {
            ((TextView)findViewById(R.id.CompletionRatio)).setText(setsToGo+" more sets");
        } else {
            ((TextView)findViewById(R.id.CountForTest)).setVisibility(View.VISIBLE);
            ((TextView)findViewById(R.id.CompletionRatio)).setVisibility(View.GONE);
        }
        
        int countToGo = extras.getInt(COUNT_TO_GO);
        ((TextView)findViewById(R.id.CountToGo)).setText(getResources().getString(R.string.count_to_go,countToGo));
        soundAlert = new SoundAlert(PreferenceManager.getDefaultSharedPreferences(this),this);
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        
        keepScreenAlive();
        
        determineRestTimeRemaining();

        if (rest_milliseconds <= 0) { finish(); }
        
        final TextView timeLeft = (TextView) findViewById(R.id.TimeLeft);
        timeLeft.setText(PrettyDateAndTime.formatMillis(rest_milliseconds));
        
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
                timeLeft.setText(PrettyDateAndTime.formatMillis(millisRestLeft));
                getWindow().setFeatureInt(Window.FEATURE_PROGRESS, (int)(1-millisRestLeft/(rest_milliseconds))*10000);
                if (millisRestLeft <= 10999 && !warned) {
                    Log.i("nthings:RestActivity", "Ending in 10 seconds");
                    timeLeft.setTextColor(Color.RED);
                    
                    soundAlert.vibrate();
                    warned = true;
                }
            }
            
            @Override
            public void onFinish() {
                soundAlert.cleanup();
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
