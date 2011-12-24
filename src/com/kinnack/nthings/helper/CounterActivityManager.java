package com.kinnack.nthings.helper;

import java.util.Arrays;

import android.content.Context;
import android.content.SharedPreferences;

import com.kinnack.nthings.activity.CounterActivity;
import com.kinnack.nthings.activity.ManualEntryCounterActivity;
import com.kinnack.nthings.activity.ProximityCounterActivity;
import com.kinnack.nthings.activity.SitupAccelCounterActivity;
import com.kinnack.nthings.model.Workout.Type;


public class CounterActivityManager {
    public enum CountMode {
        MANUAL(ManualEntryCounterActivity.class), 
        COUNT(CounterActivity.class), 
        PROXIMITY(ProximityCounterActivity.class, Type.PUSHUP),
        ACCELEROMETER(SitupAccelCounterActivity.class,Type.SITUP);
    
        
        private Type[] typesToHandle;
        private Class<? extends CounterActivity> klass;
        
        private CountMode(Class<? extends CounterActivity> class_) {
            klass = class_;
            typesToHandle = new Type[]{Type.PUSHUP,Type.SITUP};
        }
        private CountMode(Class<? extends CounterActivity> class_,Type... typesToHandle_) {
            klass = class_;
            typesToHandle = typesToHandle_;
        }
        
        public boolean canHandle(Type type_) {
            for(int i = 0; i < typesToHandle.length; i++) {
                if (typesToHandle[i] == type_) { return true;}
            }
            return false;
        }
        
        public Class<? extends CounterActivity> getActivity() { return klass; }
    };
    public CountMode mode = CountMode.COUNT;
    public CountMode situpMode = CountMode.ACCELEROMETER;
    
    public CounterActivityManager(SharedPreferences preferences_, Context context_) {
        //String soundSettingKey = context_.getResources().getString(R.string.entry_type_key);
        mode = CountMode.valueOf(preferences_.getString("INPUT_MODE", "COUNT"));
        situpMode = CountMode.valueOf(preferences_.getString("SITUP_INPUT_MODE", "ACCELEROMETER"));
    }
    
    public Class<? extends CounterActivity> getActivity(Type activeMode_) {
        switch(activeMode_) {
            case PUSHUP: return mode.getActivity();
            case SITUP: return situpMode.getActivity();
            default: return mode.getActivity();
        }
    }

   
    
    
}
