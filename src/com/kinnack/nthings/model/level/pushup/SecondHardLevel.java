package com.kinnack.nthings.model.level.pushup;

import android.util.Log;

import com.kinnack.nthings.model.level.HardLevel;

public class SecondHardLevel extends HardLevel {

    @Override
    public boolean checkLevel(int count_) {
        Log.d("","Checking if "+count_+" is > 25 "+(count_ > 25) );
        return (count_ > 25);
    }

    @Override
    public int getStartWeek() {
        return 3;
    }

}
