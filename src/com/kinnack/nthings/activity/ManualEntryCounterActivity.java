package com.kinnack.nthings.activity;

import com.kinnack.nthings.R;
import com.kinnack.nthings.StopWatch;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class ManualEntryCounterActivity extends CounterActivity {
    @Override
    public void onCreate(Bundle savedInstanceState_) {
        super.onCreate(savedInstanceState_);
        stopWatch = new StopWatch();
    }
    
    public void startTimer(View target_) {
        findViewById(R.id.startBtn).setEnabled(false);
        stopWatch.start();
        findViewById(R.id.endBtn).setEnabled(true);
        
    }
    
    public void endTimer(View target_) {
        stopWatch.stop();
        findViewById(R.id.endBtn).setEnabled(false);
        findViewById(R.id.Done).setEnabled(true);
        findViewById(R.id.manualEntry).setEnabled(true);
        sumTimeBetweenCounts = stopWatch.getElapsedTime();
        if (increment == -1) {
            setResult(RESULT_OK,createIntentWithStats(neededCount));
            finish();
        }
    }
    
    public void done(View target_) {
        EditText manualEntry = (EditText) findViewById(R.id.manualEntry);
        int reps = Integer.parseInt(manualEntry.getText().toString());
        setResult(RESULT_OK, createIntentWithStats(reps));
        finish();
    }
    
    protected int getLayout() {
        return R.layout.manual_entry_with_timer;
    }
    
    protected void showQuitingOptions() {
        super.showQuitingOptions();
        findViewById(R.id.manualEntry).setVisibility(View.VISIBLE);
    }
}
