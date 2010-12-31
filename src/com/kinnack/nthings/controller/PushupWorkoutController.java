package com.kinnack.nthings.controller;



import com.kinnack.nthings.model.Workout;
import com.kinnack.nthings.model.Workout.Type;

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
    
}
