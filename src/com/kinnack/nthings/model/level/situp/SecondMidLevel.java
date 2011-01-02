package com.kinnack.nthings.model.level.situp;

import android.util.Log;

import com.kinnack.nthings.model.level.MidLevel;

public class SecondMidLevel extends MidLevel {
    @Override
    public boolean checkLevel(int count_) {
        Log.d("", "Checking if "+count_+" is >20 and < 25 "+((count_ > 20) && (count_ < 25)));
        return isCountBetween(count_, 31, 40);
    }
    
    @Override
    public int getStartWeek() {
        return 3;
    }
}
