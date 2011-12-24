package com.kinnack.nthings.model.level.situp;

import com.kinnack.nthings.model.level.HardLevel;

public class InitialSkipWeekHardLevel extends HardLevel {

    @Override
    public boolean checkLevel(int count_) {
        return count_ > 30;
    }

    @Override
    public int getStartWeek() {
        return 3;
    }

}
