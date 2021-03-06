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
    
    @Override
    public boolean equals(Object o_) {
        if (!(o_ instanceof DayAndWeek)) {return false;}
        DayAndWeek other = (DayAndWeek)o_;
        return other.day == day && other.week == week;
    }
}