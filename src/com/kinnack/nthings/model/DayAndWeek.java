package com.kinnack.nthings.model;


public class DayAndWeek {
    public int day;

    public int week;

    public DayAndWeek(int day_,int week_) {
        day = day_;
        week = week_;
    }
    
    
    public static DayAndWeek notFound() {
        return new DayAndWeek(-1,-1);
    }
    
    public boolean wasFound() {
        return day > 0 && week > 0;
    }
}