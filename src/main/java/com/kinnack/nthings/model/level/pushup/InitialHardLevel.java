package com.kinnack.nthings.model.level.pushup;

import android.util.Log;

import com.kinnack.nthings.model.level.HardLevel;

public class InitialHardLevel extends HardLevel {

    @Override
    public boolean checkLevel(int count_) {
        Log.d("", "Checking if "+count_+" is >10 and <=20 "+((count_ > 10) && (count_ <= 20)));
        return (count_ > 10) && (count_ <= 20);
    }

    @Override
    public int getStartWeek() {
        return 1;
    }

}
