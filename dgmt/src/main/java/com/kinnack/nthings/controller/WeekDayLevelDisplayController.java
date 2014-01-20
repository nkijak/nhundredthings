package com.kinnack.nthings.controller;

import com.kinnack.nthings.model.DayAndWeek;

import android.widget.Spinner;

public class WeekDayLevelDisplayController {
    public Spinner weekDaySpinner;
    public Spinner levelSpinner;
    
    public WeekDayLevelDisplayController(Spinner weekDaySpinner_, Spinner levelSpinner_) {
        weekDaySpinner = weekDaySpinner_;
        levelSpinner = levelSpinner_;
    }
    
    public WeekDayLevelDisplayController setDayAndWeek(DayAndWeek dayAndWeek_){
        return this;
    }

    
    
    
}
