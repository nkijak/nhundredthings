package com.kinnack.nthings.model.level.pushup;

import com.kinnack.nthings.model.level.EasyLevel;

public class FourthEasyLevel extends EasyLevel {

    @Override
    public boolean checkLevel(int count_) {
        return (count_ > 45) && (count_ <= 50);
    }

    @Override
    public int getStartWeek() {
        return 6;
    }

}
