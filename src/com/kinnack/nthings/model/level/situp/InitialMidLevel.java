package com.kinnack.nthings.model.level.situp;

import com.kinnack.nthings.model.level.MidLevel;

public class InitialMidLevel extends MidLevel {

    @Override
    public boolean checkLevel(int count_) {
        return count_>= 11 && count_ <= 20;
    }

    @Override
    public int getStartWeek() {
        return 1;
    }

}
