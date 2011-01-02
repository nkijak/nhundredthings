package com.kinnack.nthings.model.level.situp;

import com.kinnack.nthings.model.level.EasyLevel;

public class ThirdEasyLevel extends EasyLevel {
    @Override
    public boolean checkLevel(int count_) {
        return isCountBetween(count_, 41, 50);
    }
    
    @Override
    public int getStartWeek() {
        return 5;
    }
}
