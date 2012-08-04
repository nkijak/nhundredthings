package com.kinnack.nthings.model;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.kinnack.nthings.controller.WorkoutController;
import com.kinnack.nthings.fragments.ChartingStartBlockFragment;
import com.kinnack.nthings.fragments.OverviewFragment;
import com.viewpagerindicator.TitleProvider;

public class SetTitleAdapter extends FragmentPagerAdapter implements TitleProvider {
	
	private static Fragment[] fragments;
	
	public SetTitleAdapter(FragmentManager fragmentManager_, WorkoutController controller_) {
		super(fragmentManager_);
		fragments = new Fragment[]{OverviewFragment.newInstance(null), ChartingStartBlockFragment.newInstance(controller_), ChartingStartBlockFragment.newInstance(controller_), ChartingStartBlockFragment.newInstance(controller_)};
	}
	
	@Override
	public String getTitle(int position_) {
		if (position_ == 0) return "Overview";
		return "Day "+position_;
	}
	
	@Override
	public int getCount() {
		return 4;
	}
	
	@Override
	public Fragment getItem(int index_) {
		return fragments[index_];
	}
}
