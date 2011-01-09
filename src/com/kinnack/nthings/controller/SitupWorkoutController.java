package com.kinnack.nthings.controller;

import com.kinnack.nthings.R;
import com.kinnack.nthings.model.Test;
import com.kinnack.nthings.model.Workout;
import com.kinnack.nthings.model.Workout.Type;
import com.kinnack.nthings.model.level.EasyLevel;
import com.kinnack.nthings.model.level.Level;
import com.kinnack.nthings.model.level.situp.InitialEasyLevel;
import com.kinnack.nthings.model.level.situp.SitupTest;

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

    @Override
    public int getFinalTestCount() {
        return 200;
    }

    @Override
    public int getLabelResource() {
        return R.string.situps_label;
    }

    @Override
    public Level getLevelForTestResult(int testCount_) {
        return SitupTest.getLevelForTestResultsByWeek(testCount_, getWeek());
    }

}
