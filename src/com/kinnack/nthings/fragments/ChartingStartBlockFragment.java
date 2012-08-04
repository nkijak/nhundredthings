package com.kinnack.nthings.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kinnack.nthings.R;
import com.kinnack.nthings.controller.WorkoutController;

public class ChartingStartBlockFragment extends Fragment {
	private WorkoutController _controller;
	private View _view;
	
	public static ChartingStartBlockFragment newInstance(WorkoutController controller) {
		return new ChartingStartBlockFragment(controller);
	}
	
	public ChartingStartBlockFragment(WorkoutController controller_) {
		_controller = controller_;
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater_, ViewGroup container_,
			Bundle savedInstanceState_) {
		_view = inflater_.inflate(R.layout.charting_start_block, container_,false);
		return _view;
	}
}
