package com.kinnack.nthings.controller;

import org.json.JSONException;

import android.content.SharedPreferences;
import android.util.Log;

import com.kinnack.nthings.helper.CounterActivityManager;
import com.kinnack.nthings.model.ExerciseSet;
import com.kinnack.nthings.model.History;
import com.kinnack.nthings.model.Logg;
import com.kinnack.nthings.model.Workout.Type;
import com.kinnack.nthings.model.level.Level;

public abstract class FullWorkoutController {
	public abstract ExerciseSet getSetForDay(int day_);
	protected abstract int numberOfDays();
	protected abstract String getTag();
	protected abstract String getKeyForHistory();
	protected abstract Type getWorkoutType();
	
	private Level level;
	private int week;
	private int overallMaxCount = -1;
	private History _history;
	private CounterActivityManager _counterActivityManager;
	
	
	public FullWorkoutController(Level level_, int week_) {
		week = week_;
		level = level_;
	}
	
	public void invalidate() {
		overallMaxCount = -1;
	}
	
	public int totalCountForSet(int set_) {
		return getSetForDay(set_).getCountLeft();
	}
	
	public int getOverallMaxCount() {
		if (overallMaxCount < 0) {
			ExerciseSet set;
			int max = Integer.MIN_VALUE;
			for(int i = 1; i <= numberOfDays(); i++) {
				set = getSetForDay(i);
				max = Math.max(max, set.getMinMaxCounts()[1]);
				Log.d(getTag(), "Max count for week "+week+" is now "+max);
			}
			overallMaxCount = max;
		}
		return overallMaxCount;
	}
	
	public void loadHistory(SharedPreferences prefs) {
        if (_history != null) { return; }
        forceReloadHistory(prefs);
    }
    
    public void forceReloadHistory(SharedPreferences prefs){
        try {
            _history = new History(prefs.getString(getKeyForHistory(), null));
            Log.d(getTag(),"Loaded history as "+prefs.getString(getKeyForHistory(), "[Not found]"));
        } catch (JSONException e) {
            Log.e(getTag(), "Couldn't unmarshal history", e);
        } catch (NullPointerException npe) {
            Log.i(getTag(), "No history to load");
        }
        if (_history == null) {
            _history = new History();
            _history.setType(getWorkoutType());
        }
        //if (set == null && !isTest() && !isFinal()) {getThisWeekAndDaySet();}
        level = _history.getCurrentLevel();
    }
	
    
    
	public int[] countsForSet(int set) {
		return getSetForDay(set).getCounts();
	}
	
	public boolean hasTest() {
		return week != 5 && week%2==0;
	}
	
	public boolean hasFinal() {
		//FIXME put in real logic
		return week == 5;
	}
	
	public History getHistory() {
		return _history;
	}
	
	public Logg getCurrentLog() {
		return _history.getCurrentLog();
	}
	
	public int getTotalCount() {
		return _history.getTotalCount();
	}
	
	public Level getLevel() {
		return level;
	}
	public void setLevel(Level level_) {
		level = level_;
		invalidate();
	}
	public int getWeek() {
		return week;
	}
	public void setWeek(int week_) {
		week = week_;
		invalidate();
	}
	public CounterActivityManager getCounterActivityManager() {
		return _counterActivityManager;
	}
	public void setCounterActivityManager(
			CounterActivityManager counterActivityManager_) {
		_counterActivityManager = counterActivityManager_;
	}
	
	
}
