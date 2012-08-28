package com.kinnack.nthings.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kinnack.nthings.R;
import com.kinnack.nthings.controller.FullWorkoutController;
import com.kinnack.nthings.widget.SetOverviewChart;

public class ChartingStartBlockFragment extends BaseExcersiseSetFragment {
	private SetOverviewChart _chart;
	private boolean createViewCalled = false;
	
	public ChartingStartBlockFragment(FullWorkoutController controller_, int setNumber_) {
		super(setNumber_);
		_controller = controller_;
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater_, ViewGroup container_,
			Bundle savedInstanceState_) {
		View view = super.onCreateView(inflater_, container_, savedInstanceState_);
		_chart = (SetOverviewChart)view.findViewById(R.id.setOverviewChart);
		
		resetViews();
		createViewCalled = true;
		return view;
		
	}
	
	@Override
	public void dayWeekOrLevelChanged() {
		super.dayWeekOrLevelChanged();
		resetViews();
	}
	
	public void resetViews() {
		if (_chart != null ) {
			_chart.setExercseSet(_controller.getSetForDay(_setNumber));
			_chart.setMaxX(_controller.getOverallMaxCount());
		}
	}
	
	@Override
	public String getTitle() {
		return super.getTitle()+" ["+(createViewCalled ? "YES" : "NO")+"]";
	}
	
	
}
