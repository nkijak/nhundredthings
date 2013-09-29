package com.kinnack.nthings.model.level.situp;

import com.kinnack.nthings.model.level.EasyLevel;

public class FourthEasyLevel extends EasyLevel {

    @Override
    public boolean checkLevel(int count_) {
        return isCountBetween(count_, 75, 90);
    }

    @Override
    public int getStartWeek() {
        return 6;
    }

}
