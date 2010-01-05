package com.kinnack.nthings.model.level.pushup;

import com.kinnack.nthings.model.level.HardLevel;

public class ThirdHardLevel extends HardLevel {

    @Override
    public boolean checkLevel(int count_) {
        return (count_ > 40);
    }

    @Override
    public int getStartWeek() {
        return 5;
    }

}
