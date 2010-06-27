package com.kinnack.nthings.helper;

import com.kinnack.nthings.R;
import com.kinnack.nthings.activity.CounterActivity;
import com.kinnack.nthings.activity.ManualEntryCounterActivity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

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
