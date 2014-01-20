package com.kinnack.nthings.model.level.situp;

import com.kinnack.nthings.model.level.HardLevel;

public class FourthHardLevel extends HardLevel {

    @Override
    public boolean checkLevel(int count_) {
        return count_ > 110;
    }

    @Override
    public int getStartWeek() {
        return 6;
    }

}
