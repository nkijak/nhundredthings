package com.kinnack.nthings.model.level.situp;

import com.kinnack.nthings.model.level.MidLevel;

public class FourthMidLevel extends MidLevel {

    @Override
    public boolean checkLevel(int count_) {
        return isCountBetween(count_, 91, 110);
    }

    @Override
    public int getStartWeek() {
        return 6;
    }

}
