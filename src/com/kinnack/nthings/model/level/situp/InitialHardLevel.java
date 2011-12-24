package com.kinnack.nthings.model.level.situp;

import com.kinnack.nthings.model.level.HardLevel;

public class InitialHardLevel extends HardLevel {

    @Override
    public boolean checkLevel(int count_) {
        return isCountBetween(count_, 21, 30);
    }

    @Override
    public int getStartWeek() {
        return 1;
    }

}
