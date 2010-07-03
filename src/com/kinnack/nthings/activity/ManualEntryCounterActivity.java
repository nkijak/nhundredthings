package com.kinnack.nthings.activity;

import com.kinnack.nthings.R;
import com.kinnack.nthings.StopWatch;
import com.kinnack.nthings.helper.RangedIntTextWatcher;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

public class ManualEntryCounterActivity extends CounterActivity {
    private RangedIntTextWatcher rangedIntTextWatcher;
    @Override
    public void onCreate(Bundle savedInstanceState_) {
        super.onCreate(savedInstanceState_);
        stopWatch = new StopWatch();
        rangedIntTextWatcher = new RangedIntTextWatcher(0, 300);
        EditText manualEntry = (EditText)findViewById(R.id.manualEntry);
        manualEntry.addTextChangedListener(rangedIntTextWatcher);
        // XXX Hack that depends on TextChangedListeners to be called in the order they are added!!
        manualEntry.addTextChangedListener(new TextWatcher() {
            
            @Override
            public void onTextChanged(CharSequence s_, int start_, int before_, int count_) {
                View endButton = findViewById(R.id.Done);
                if (rangedIntTextWatcher.isValid()) {
                    endButton.setEnabled(false);                    
                } else {
                    endButton.setEnabled(true);  
                }
            }
            
            @Override
            public void beforeTextChanged(CharSequence s_, int start_, int count_, int after_) {
                // TODO Auto-generated method stub
                
            }
            
            @Override
            public void afterTextChanged(Editable s_) {
                // TODO Auto-generated method stub
                
            }
        });
    }
    
    public void startTimer(View target_) {
        findViewById(R.id.startBtn).setEnabled(false);
        stopWatch.start();
        findViewById(R.id.endBtn).setEnabled(true);
        
    }
    
    public void endTimer(View target_) {
        stopWatch.stop();
        // DONE button only enabled after validation
        findViewById(R.id.endBtn).setEnabled(false);
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
