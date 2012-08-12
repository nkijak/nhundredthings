package com.kinnack.nthings.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.kinnack.nthings.R;
import com.kinnack.nthings.controller.FullWorkoutController;
import com.kinnack.nthings.controller.WorkoutController;

public class BaseExcersiseSetFragment extends SherlockFragment implements OnClickListener {

	protected FullWorkoutController _controller;
	private View _view;
	protected int _setNumber;



	public BaseExcersiseSetFragment(int setNumber) {
		super();
		_setNumber = setNumber;
	}

	@Override
	public View onCreateView(LayoutInflater inflater_, ViewGroup container_, Bundle savedInstanceState_) {
		_view = inflater_.inflate(R.layout.charting_start_block, container_,false);
		
		dayWeekOrLevelChanged();
		return _view;
	}
	
	protected void dayWeekOrLevelChanged() {
        String count = (String) getResources().getText(R.string.count_for_test_msg);
        if (_setNumber > 0) { count = _controller.totalCountForSet(_setNumber)+""; }
        ((TextView)_view.findViewById(R.id.count_for_settings)).setText("Drop and Give Me "+count+"!");
    }

	public FullWorkoutController getController() {
		return _controller;
	}

	public void setController(FullWorkoutController controller_) {
		_controller = controller_;
	}

	
	
	@Override
	public void onClick(View v_) {
		Log.d("DGMT!BESF", "Starting for set number "+_setNumber);
		
	}

}