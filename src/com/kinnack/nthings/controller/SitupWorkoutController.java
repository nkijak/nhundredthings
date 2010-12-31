package com.kinnack.nthings.controller;

import com.kinnack.nthings.model.Workout;
import com.kinnack.nthings.model.Workout.Type;

public class SitupWorkoutController extends WorkoutController {

    @Override
    protected String getTag() {
        return "dgmt:SITUP-CONTROL";
    }

    @Override
    protected String getKeyForHistory() {
        return "situp-history";
    }

    @Override
    protected Type getWorkoutType() {
        return Type.SITUP;
    }
    
    protected void getThisWeekAndDaySet() {
        set = Workout.getSitupSetFor(history.getWeek(), history.getDay(), history.getCurrentLevel());
    }

}
