package com.kinnack.nthings.model.level;


public abstract class EasyLevel extends Level {
    @Override
    public int getIndex() {
        return 0;
    }

    @Override
    public String toString() {
        return "EASY on or after week "+getStartWeek();
    }
    
    @Override
    public String getLabel() {
        return "EASY";
    }

}
