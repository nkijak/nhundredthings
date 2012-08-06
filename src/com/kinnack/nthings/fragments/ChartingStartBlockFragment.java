package com.kinnack.nthings.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kinnack.nthings.R;
import com.kinnack.nthings.controller.WorkoutController;
import com.kinnack.nthings.model.DayAndWeek;
import com.kinnack.nthings.widget.SetOverviewChart;

public class ChartingStartBlockFragment extends BaseExcersiseSetFragment {
	
	public ChartingStartBlockFragment(WorkoutController controller_) {
		_controller = controller_;
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater_, ViewGroup container_,
			Bundle savedInstanceState_) {
		View view = super.onCreateView(inflater_, container_, savedInstanceState_);
		SetOverviewChart chart = (SetOverviewChart)view.findViewById(R.id.setOverviewChart);
		
		chart.setExercseSet(_controller.getSet());
		
		return view;
		
	}
}
