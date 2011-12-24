package com.kinnack.nthings.model.level.pushup;

import com.kinnack.nthings.model.level.MidLevel;

public class FourthMidLevel extends MidLevel {

    @Override
    public boolean checkLevel(int count_) {
        return (count_ > 50) && (count_ <= 60);
    }

    @Override
    public int getStartWeek() {
        return 6;
    }

}
