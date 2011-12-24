package com.kinnack.nthings.model.level.situp;

import com.kinnack.nthings.model.level.MidLevel;

public class ThirdMidLevel extends MidLevel {

    @Override
    public boolean checkLevel(int count_) {
        return isCountBetween(count_, 51, 60);
    }

    @Override
    public int getStartWeek() {
        return 5;
    }

}
