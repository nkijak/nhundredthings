package com.kinnack.nthings.model.level.pushup;

import com.kinnack.nthings.model.level.HardLevel;

public class InitialHardLevel extends HardLevel {

    @Override
    public boolean checkLevel(int count_) {
        return (count_ > 10) && (count_ <= 20);
    }

    @Override
    public int getStartWeek() {
        return 1;
    }

}
