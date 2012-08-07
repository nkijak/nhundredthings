package com.kinnack.nthings.model;

import java.util.ArrayList;
import java.util.List;

import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.kinnack.nthings.controller.FullWorkoutController;
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
	private static FullWorkoutController _controller;
	
	public SetTitleAdapter(FragmentManager fragmentManager_,Type type_, FullWorkoutController fullWorkoutController_) {
		super(fragmentManager_);
		_controller = fullWorkoutController_;
		
		List<BaseExcersiseSetFragment> fragmentList = new ArrayList<BaseExcersiseSetFragment>();
		fragmentList.add(new OverviewFragment(fullWorkoutController_));
		
		if (fullWorkoutController_.hasTest()) fragmentList.add(new TestSettingsFragment(fullWorkoutController_));
		for (int i = 1; i < 4; i++) fragmentList.add(new ChartingStartBlockFragment(fullWorkoutController_, i));
		//if (_controller.isFinal()) fragmentList.add(new FinalSettingsFragment(newController(type_, dayAndWeek_)));
		fragments = fragmentList.toArray(new BaseExcersiseSetFragment[0]);
	}
	
	@Override
	public String getTitle(int position_) {
		final int last = fragments.length-1;
		FullWorkoutController controller = fragments[position_].getController();
		switch(position_){
			case 0: 
				return "Overview";
			case 1:
				if (controller.hasTest()) return "Test";
			default:
				if (position_ == last && controller.hasFinal()) return "Final";
				int day = position_;
				if (controller.hasTest()) day-=1;
				return "Day "+day;
		}
	}
	
	@Override
	public int getCount() {
		int count = 4;
		if (_controller.hasTest()) count++;
		//if (_controller.isFinal()) count++;
		return count;
	}
	
	@Override
	public Fragment getItem(int index_) {
		return fragments[index_];
	}
		
	
	
	
}
