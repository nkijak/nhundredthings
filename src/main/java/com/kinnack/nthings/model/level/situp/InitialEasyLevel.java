package com.kinnack.nthings.model.level.situp;

import com.kinnack.nthings.model.level.EasyLevel;


public class InitialEasyLevel extends EasyLevel {

    @Override
    public boolean checkLevel(int count_) {
        return count_ <= 10;
    }

    @Override
    public int getStartWeek() {
        return 1;
    }

}
