package com.kinnack.nthings.model.level.pushup;

import com.kinnack.nthings.model.level.HardLevel;

public class FourthHardLevel extends HardLevel {

    @Override
    public boolean checkLevel(int count_) {
        return count_ > 60;
    }

    @Override
    public int getStartWeek() {
        return 6;
    }

}
