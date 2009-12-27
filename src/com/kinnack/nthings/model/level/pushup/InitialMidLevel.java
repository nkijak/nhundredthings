package com.kinnack.nthings.model.level.pushup;

import android.util.Log;

import com.kinnack.nthings.model.level.MidLevel;

public class InitialMidLevel extends MidLevel {

    @Override
    public boolean checkLevel(int count_) {
        Log.d("","checking if "+count_+" is >5 and <=10 "+((count_ > 5) && (count_ <= 10)));
        return (count_ > 5) && (count_ <= 10);
    }

    @Override
    public int getStartWeek() {
        return 1;
    }

}
