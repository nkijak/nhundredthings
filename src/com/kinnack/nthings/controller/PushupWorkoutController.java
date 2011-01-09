package com.kinnack.nthings.controller;



import com.kinnack.nthings.R;
import com.kinnack.nthings.model.Test;
import com.kinnack.nthings.model.Workout;
import com.kinnack.nthings.model.Workout.Type;
import com.kinnack.nthings.model.level.Level;

public class PushupWorkoutController extends WorkoutController {
    public static final String TAG = "nthings:PUSHUP-CONTROL";
    public static final String KEY_HISTORY = "history";
    
    @Override
    protected String getKeyForHistory() {
        return KEY_HISTORY;
    }
    
    @Override
    protected String getTag() {
        return TAG;
    }
    
    @Override
    protected Type getWorkoutType() {
        return Type.PUSHUP;
    }

    protected void getThisWeekAndDaySet() {
        set = Workout.getPushupSetFor(history.getWeek(), history.getDay(), history.getCurrentLevel());
    }

    @Override
    public int getFinalTestCount() {
        return 100;
    }

    @Override
    public int getLabelResource() {
        return R.string.pushups_label;
    }

    @Override
    public Level getLevelForTestResult(int testCount_) {
        return Test.getLevelForTestResultsByWeek(testCount_, getWeek());
    }
    
}
