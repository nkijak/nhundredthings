package com.kinnack.nthings.model.level.pushup;

import android.util.Log;

import com.kinnack.nthings.model.level.MidLevel;

public class SecondMidLevel extends MidLevel {
    @Override
    public boolean checkLevel(int count_) {
        Log.d("", "Checking if "+count_+" is >20 and < 25 "+((count_ > 20) && (count_ < 25)));
        return (count_ > 20) && (count_ < 25);
    }
    
    @Override
    public int getStartWeek() {
        return 3;
    }
}
