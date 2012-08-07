package com.kinnack.nthings.controller;

import com.kinnack.nthings.model.ExerciseSet;
import com.kinnack.nthings.model.Workout;
import com.kinnack.nthings.model.Workout.Type;
import com.kinnack.nthings.model.level.Level;

public class PushupFullWorkoutController extends FullWorkoutController {
	private static final String TAG = "DGMT!PFWC";
	private static final String KEY_FOR_HISTORY = "history";
	public PushupFullWorkoutController(){
		super(null,1);
	}
	
	public PushupFullWorkoutController(Level level_, int week_) {
		super(level_, week_);
	}

	@Override
	public ExerciseSet getSetForDay(int day_) {
		return Workout.getPushupSetFor(getWeek(), day_, getLevel());
	}

	@Override
	protected int numberOfDays() {
		return Workout.daysForPushupWeek(getWeek());
	}

	@Override
	protected String getTag() {
		return TAG;
	}

	@Override
	protected String getKeyForHistory() {
		return KEY_FOR_HISTORY;
	}

	@Override
	protected Type getWorkoutType() {
		return Type.PUSHUP;
	}

}
