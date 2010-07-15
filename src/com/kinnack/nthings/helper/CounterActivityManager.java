package com.kinnack.nthings.helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.kinnack.nthings.activity.CounterActivity;
import com.kinnack.nthings.activity.ManualEntryCounterActivity;
import com.kinnack.nthings.activity.ProximityCounterActivity;


public class CounterActivityManager {
    public enum CountMode {MANUAL, COUNT, PROXIMITY};
    public CountMode mode = CountMode.COUNT;
    
    public CounterActivityManager(SharedPreferences preferences_, Context context_) {
        //String soundSettingKey = context_.getResources().getString(R.string.entry_type_key);
        mode = CountMode.valueOf(preferences_.getString("INPUT_MODE", "COUNT"));
    }
    
    public Class<? extends CounterActivity> getActivity() {
        switch(mode)
        {   
        case MANUAL: return ManualEntryCounterActivity.class;
        case COUNT: return CounterActivity.class;
        case PROXIMITY: return ProximityCounterActivity.class;
        default: return CounterActivity.class;
        }
    }
    
}
