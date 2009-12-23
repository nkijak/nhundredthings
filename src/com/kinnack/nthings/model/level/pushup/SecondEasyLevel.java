package com.kinnack.nthings.model.level.pushup;

import com.kinnack.nthings.model.level.EasyLevel;

public class SecondEasyLevel extends EasyLevel {

    @Override
    public boolean checkLevel(int count_) {
        return (count_ > 15) & (count_ <=20);
    }

    @Override
    public int getStartWeek() {
        return 3;
    }

}
