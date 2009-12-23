package com.kinnack.nthings.model.level.pushup;

import com.kinnack.nthings.model.level.MidLevel;

public class SecondMidLevel extends MidLevel {
    @Override
    public boolean checkLevel(int count_) {
        return (count_ > 20) && (count_ < 25);
    }
    
    @Override
    public int getStartWeek() {
        return 3;
    }
}
