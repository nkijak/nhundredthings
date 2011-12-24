package com.kinnack.nthings.activity;

import com.kinnack.nthings.R;

import android.app.TabActivity;
import android.os.Bundle;
import android.widget.TabHost;


public class WorkoutSelectionTabActivity extends TabActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState_) {
        super.onCreate(savedInstanceState_);
        setContentView(R.layout.workout_selection_tabbed);
        
        TabHost tabHost = getTabHost();
        
        tabHost.addTab(tabHost.newTabSpec("EASY").setIndicator("Easy"));
        tabHost.addTab(tabHost.newTabSpec("MID").setIndicator("Medium"));
        tabHost.addTab(tabHost.newTabSpec("HARD").setIndicator("Hard"));
        
    }
}
