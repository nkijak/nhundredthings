package com.kinnack.nthings.model.level.pushup;

import com.kinnack.nthings.model.level.EasyLevel;

public class ThirdEasyLevel extends EasyLevel {
    @Override
    public boolean checkLevel(int count_) {
        return (count_ > 30) && (count_ <= 35);
    }
    
    @Override
    public int getStartWeek() {
        return 5;
    }
}
