package com.kinnack.nthings.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.kinnack.nthings.R;
import com.kinnack.nthings.controller.WorkoutController;
import com.kinnack.nthings.model.DayAndWeek;

public class BaseExcersiseSetFragment extends SherlockFragment {

	protected WorkoutController _controller;
	private View _view;



	public BaseExcersiseSetFragment() {
		super();
	}

	@Override
	public View onCreateView(LayoutInflater inflater_, ViewGroup container_, Bundle savedInstanceState_) {
		_view = inflater_.inflate(R.layout.charting_start_block, container_,false);
		
		dayWeekOrLevelChanged();
		return _view;
	}
	
	protected void dayWeekOrLevelChanged() {
        String count = (String) getResources().getText(R.string.count_for_test_msg);
        if (!_controller.shouldDisplayDayAsTest()) { count = _controller.totalCountLeft()+""; }
        ((TextView)_view.findViewById(R.id.count_for_settings)).setText("Drop and Give Me "+count+"!");
    }

	public WorkoutController getController() {
		return _controller;
	}

	public void setController(WorkoutController controller_) {
		_controller = controller_;
	}

	
	

}