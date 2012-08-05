package com.kinnack.nthings.model;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.kinnack.nthings.controller.PushupWorkoutController;
import com.kinnack.nthings.controller.SitupWorkoutController;
import com.kinnack.nthings.controller.WorkoutController;
import com.kinnack.nthings.fragments.BaseExcersiseSetFragment;
import com.kinnack.nthings.fragments.ChartingStartBlockFragment;
import com.kinnack.nthings.fragments.OverviewFragment;
import com.kinnack.nthings.fragments.TestSettingsFragment;
import com.kinnack.nthings.model.Workout.Type;
import com.viewpagerindicator.TitleProvider;

public class SetTitleAdapter extends FragmentPagerAdapter implements TitleProvider {
	
	private static BaseExcersiseSetFragment[] fragments;
	private static WorkoutController _possibleTestController;
	private static WorkoutController _possibleFinalController;
	private static SharedPreferences _preferences;
	
	public SetTitleAdapter(FragmentManager fragmentManager_,Type type_, DayAndWeek dayAndWeek_, SharedPreferences preferences_) {
		super(fragmentManager_);
		_preferences = preferences_;
		
		List<BaseExcersiseSetFragment> fragmentList = new ArrayList<BaseExcersiseSetFragment>();
		fragmentList.add(new OverviewFragment(newController(type_, dayAndWeek_)));
		_possibleTestController = newController(type_, dayAndWeek_.differentDayOfWeek(0));
		if (_possibleTestController.isTest()) fragmentList.add(new TestSettingsFragment(_possibleTestController));
		for (int i = 1; i < 4; i++) fragmentList.add(new ChartingStartBlockFragment(newController(type_, dayAndWeek_.differentDayOfWeek(i))));
		//if (_controller.isFinal()) fragmentList.add(new FinalSettingsFragment(newController(type_, dayAndWeek_)));
		fragments = fragmentList.toArray(new BaseExcersiseSetFragment[0]);
	}
	
	@Override
	public String getTitle(int position_) {
		final int last = fragments.length-1;
		WorkoutController controller = fragments[position_].getController();
		switch(position_){
			case 0: 
				return "Overview";
			case 1:
				if (controller.isTest()) return "Test";
			default:
				if (position_ == last && controller.isFinal()) return "Final";
				return "Day "+position_;
		}
	}
	
	@Override
	public int getCount() {
		int count = 4;
		if (_possibleTestController.isTest()) count++;
		//if (_controller.isFinal()) count++;
		return count;
	}
	
	@Override
	public Fragment getItem(int index_) {
		return fragments[index_];
	}
		
	protected WorkoutController newController(Type type_, DayAndWeek dayAndWeek_) {
		 WorkoutController controller = null;
		 switch (type_) {
	         case PUSHUP:
	             controller = new PushupWorkoutController();
	             break;
	         case SITUP:
	             controller = new SitupWorkoutController();
	             break;
		 }
		 controller.loadHistory(_preferences);
		 controller.setDayAndWeek(dayAndWeek_);
		 return controller;
	}
	
	
}
