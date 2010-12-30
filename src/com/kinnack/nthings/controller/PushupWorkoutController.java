package com.kinnack.nthings.controller;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;

import com.kinnack.nthings.helper.CounterActivityManager;
import com.kinnack.nthings.model.DayAndWeek;
import com.kinnack.nthings.model.ExerciseSet;
import com.kinnack.nthings.model.History;
import com.kinnack.nthings.model.Logg;
import com.kinnack.nthings.model.Workout;
import com.kinnack.nthings.model.level.Level;

public class PushupWorkoutController {
    public static final String TAG = "nthings:PUSHUP-CONTROL";
    private CounterActivityManager counterActivityManager;
    private static final int COUNTER_INTENT = 100;
    private static final int TEST_INTENT = 150;
    private static final int REST_INTENT = 200;
    private static final int FINAL_TEST_INTENT=175;
    private static final int RESET_INTENT = 300;
    public static final String KEY_HISTORY = "history";
    
    private History pushupHistory;
    private ExerciseSet set;
    

    public void beginExercise(View target_) {
        Logg currentLog = new Logg(pushupHistory, pushupHistory.getWeek(),pushupHistory.getDay());
        pushupHistory.getLogs().add(currentLog);

        getThisWeekAndDaySet();
    }
    
    private void getThisWeekAndDaySet() {
        set = Workout.getPushupSetFor(pushupHistory.getWeek(), pushupHistory.getDay(), pushupHistory.getCurrentLevel());
    }
    
    
    
    /**
     * @param prefs
     * @return 
     */
    public void loadHistory(SharedPreferences prefs) {
        if (pushupHistory != null) { return; }
        try {
            pushupHistory = new History(prefs.getString(KEY_HISTORY, null));
        } catch (JSONException e) {
            Log.e(TAG, "Couldn't unmarshal history", e);
        } catch (NullPointerException npe) {
            Log.i(TAG, "No history to load");
        }
        if (pushupHistory == null) {
            pushupHistory = new History();
            pushupHistory.setType(Workout.Type.PUSHUP);
        }
        if (set == null && !isTest() && !isFinal()) {getThisWeekAndDaySet();}
    }
    
    
    
    public PushupWorkoutController addTestResult(int testCount_) {
        pushupHistory.getTestResults().add(testCount_);
        return this;
    }
    
    public PushupWorkoutController resetDay() {
        pushupHistory.setDay(1);
        return this;
    }
    
    public DayAndWeek advanceDate() {
        int day = pushupHistory.getDay();
        int week = pushupHistory.getWeek();
        if (day == 3) {
            day = (week==5 ? 0 : week%2);
            pushupHistory.setDay(day);
            pushupHistory.setWeek(pushupHistory.getWeek()+1);
        } else {
            pushupHistory.setDay(day+1);
        }
        
        return new DayAndWeek(day, week);
        
    }
    
    public int getWeek() {
        return pushupHistory == null ? 1 : pushupHistory.getWeek();
    }
    public int getDay() {
        return pushupHistory == null ? 0: pushupHistory.getDay();
    }
    public boolean isTest() {
        return pushupHistory == null || pushupHistory.isTest();
    }
    public boolean isFinal() {
        return pushupHistory.isFinal();
    }
    public String getLevelForDisplay() {
        return pushupHistory.getCurrentLevel().getLabel();
    }
    public boolean isFinalUnlocked() {
        return pushupHistory.isFinalUnlocked();
    }
    
    public boolean shouldDisplayDayAsTest() {
        return pushupHistory != null && (pushupHistory.isTest() || pushupHistory.isFinal());
    }
    public Level getCurrentLevel() {
        return pushupHistory.getCurrentLevel();
    }
    public Logg getCurrentLog() {
        return pushupHistory.getCurrentLog();
    }
    public History getHistory() {
        return pushupHistory;
    }
    
    public void markFinalComplete(){
        pushupHistory.setFinished(true);
    }
    public void resetToWorkoutForFinal() {
        pushupHistory.setWeek(6);
        resetDay();
    }
    
    public int nextSet() {
        return set.next();
    }
    
    public boolean hasNext() {
        return set.hasNext();
    }
    
    public boolean isMaxSet() {
        return set.isMax();
    }
    
    public int completedSets() {
        return set.getSetsDone();
    }
    
    public int incompleteSets() {
        return set.getSetsToGo();
    }
    
    public void setLastWorkout(Date lastWorkoutDate_) {
        pushupHistory.setLastWorkout(lastWorkoutDate_);
    }
    
    public JSONObject toJSON() throws JSONException {
        return pushupHistory.toJSON();
    }
    
    public void setDayAndWeek(DayAndWeek dayAndWeek_) {
        pushupHistory.setDay(dayAndWeek_.day);
        pushupHistory.setWeek(dayAndWeek_.week);
    }
    
    public Logg addCountAndTimeLog(int count, long time_) {
        Logg log = pushupHistory.getCurrentLog();
        log.addCountAndTime(count, time_);
        return log;
    }
    
    public void removeCurrentLog() {
        pushupHistory.removeCurrentLog();
    }
    
    public boolean setCurrentLevel(Level level_) {
        pushupHistory.setCurrentLevel(level_);
        if (pushupHistory.getDay() == 0) {
            pushupHistory.setDay(1);
            return true;
        }
        return false;
    }
    
    
    /**
     * @return the counterActivityManager
     */
    protected CounterActivityManager getCounterActivityManager() {
        return counterActivityManager;
    }

    /**
     * @param counterActivityManager_ the counterActivityManager to set
     */
    public void setCounterActivityManager(CounterActivityManager counterActivityManager_) {
        counterActivityManager = counterActivityManager_;
    }
}
