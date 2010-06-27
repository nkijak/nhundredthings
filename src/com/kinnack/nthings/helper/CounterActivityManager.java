package com.kinnack.nthings.helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.kinnack.nthings.R;
import com.kinnack.nthings.activity.CounterActivity;
import com.kinnack.nthings.activity.ManualEntryCounterActivity;

public class CounterActivityManager {
    public boolean manualEntry = false;
    
    public CounterActivityManager(SharedPreferences preferences_, Context context_) {
        String soundSettingKey = context_.getResources().getString(R.string.entry_type_key);
        manualEntry = preferences_.getBoolean(soundSettingKey, false);
    }
    
    public Class<? extends CounterActivity> getActivity() {
        if (manualEntry) { return ManualEntryCounterActivity.class; }
        return CounterActivity.class;
    }
    
}
