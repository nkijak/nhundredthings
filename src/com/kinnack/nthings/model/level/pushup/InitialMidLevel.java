package com.kinnack.nthings.model.level.pushup;

import com.kinnack.nthings.model.level.EasyLevel;

public class InitialMidLevel extends EasyLevel {

    @Override
    public boolean checkLevel(int count_) {
        return (count_ > 5) && (count_ <= 10);
    }

    @Override
    public int getStartWeek() {
        return 1;
    }

}
