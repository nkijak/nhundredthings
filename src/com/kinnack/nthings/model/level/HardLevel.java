package com.kinnack.nthings.model.level;

public abstract class HardLevel implements Level {

    @Override
    public int getIndex() {
        return 2;
    }
    
    @Override
    public String toString() {
        return "HARD on or after week "+getStartWeek();
    }

}
