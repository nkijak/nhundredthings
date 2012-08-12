package com.kinnack.nthings.controller;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import org.json.JSONException;

import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View.OnClickListener;

import com.kinnack.nthings.fragments.BaseExcersiseSetFragment;
import com.kinnack.nthings.fragments.ChartingStartBlockFragment;
import com.kinnack.nthings.fragments.OverviewFragment;
import com.kinnack.nthings.fragments.TestSettingsFragment;
import com.kinnack.nthings.helper.CounterActivityManager;
import com.kinnack.nthings.model.ExerciseSet;
import com.kinnack.nthings.model.History;
import com.kinnack.nthings.model.Logg;
import com.kinnack.nthings.model.Workout.Type;
import com.kinnack.nthings.model.level.Level;
import com.viewpagerindicator.TitleProvider;

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
	private BaseExcersiseSetFragment[] _fragments;
	private WorkoutTitlePagerAdapter _adapter;
	private int _currentSet;
	
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
	
	public void setCurrentSet(int setNumber_) {
		_currentSet = setNumber_;
	}
	public OnClickListener getCurrentStartListener() {
		return (OnClickListener)_adapter.getItem(_currentSet);
	}
	public BaseExcersiseSetFragment[] getFragments() {
		if (_fragments == null) {
			List<BaseExcersiseSetFragment> fragmentList = new ArrayList<BaseExcersiseSetFragment>();
			fragmentList.add(new OverviewFragment(this));
			
			if (hasTest()) fragmentList.add(new TestSettingsFragment(this));
			for (int i = 1; i < 4; i++) fragmentList.add(new ChartingStartBlockFragment(this, i));
			//if (_controller.isFinal()) fragmentList.add(new FinalSettingsFragment(newController(type_, dayAndWeek_)));
			_fragments = fragmentList.toArray(new BaseExcersiseSetFragment[0]);
		}
		return _fragments;
	}
	
	public PagerAdapter getPagerAdapter(FragmentManager fragmentManager_) {
		if (_adapter == null) {
			_adapter = new WorkoutTitlePagerAdapter(fragmentManager_);
		}
		return _adapter;
	}
	
	public class WorkoutTitlePagerAdapter extends FragmentPagerAdapter implements TitleProvider {

		public WorkoutTitlePagerAdapter(FragmentManager fragmentManager_) {
			super(fragmentManager_);
		}
		
		@Override
		public String getTitle(int position_) {
			return getFragments()[position_].getTitle();
		}

		@Override
		public Fragment getItem(int arg0_) {
			return getFragments()[arg0_];
		}

		@Override
		public int getCount() {
			return getFragments().length;
		}
		
	}
}
