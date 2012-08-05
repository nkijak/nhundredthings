package com.kinnack.nthings.fragments;

import java.util.Date;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kinnack.nthings.R;
import com.kinnack.nthings.controller.WorkoutController;
import com.kinnack.nthings.helper.PrettyDateAndTime;

public class OverviewFragment extends BaseExcersiseSetFragment {
	
	private TextView pushupLastUse;
    private TextView pushupLastCount;
    private TextView pushupTotalValue;
    private WorkoutController controller;
    
	public static OverviewFragment newInstance(WorkoutController controller_) {
		return new OverviewFragment(controller_);
	}
	
	public OverviewFragment(WorkoutController controller_) {
		controller = controller_;
	}
	
	private View view;
	@Override
	public View onCreateView(LayoutInflater inflater_, ViewGroup container_,
			Bundle savedInstanceState_) {
		view = inflater_.inflate(R.layout.overview, container_, false);
		pushupLastUse = (TextView)view.findViewById(R.id.lastUse);
        pushupLastCount = (TextView)view.findViewById(R.id.lastCount);
        pushupTotalValue = (TextView)view.findViewById(R.id.totalValue);
		return view;
	}
	
	
	@Override
	public void onResume() {
		super.onResume();
		pushupLastUse.setText(formatDate(controller.getHistory().getLastWorkout()));
        pushupLastCount.setText(controller.getCurrentLog().getTotalCount()+"");
        pushupTotalValue.setText(controller.getTotalCount()+"");
	}
	
	private String formatDate(Date date_) {
        if (date_ == null) { return "?"; }
        return PrettyDateAndTime.format(date_);
    }
}
