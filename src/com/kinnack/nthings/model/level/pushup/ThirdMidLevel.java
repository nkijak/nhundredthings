package com.kinnack.nthings.model.level.pushup;

import com.kinnack.nthings.model.level.MidLevel;

public class ThirdMidLevel extends MidLevel {

    @Override
    public boolean checkLevel(int count_) {
        return (count_ > 35) && (count_ <= 40);
    }

    @Override
    public int getStartWeek() {
        return 5;
    }

}
