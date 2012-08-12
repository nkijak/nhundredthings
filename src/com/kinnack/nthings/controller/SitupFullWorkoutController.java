package com.kinnack.nthings.controller;

import com.kinnack.nthings.model.ExerciseSet;
import com.kinnack.nthings.model.Workout;
import com.kinnack.nthings.model.Workout.Type;

public class SitupFullWorkoutController extends FullWorkoutController {
	private static final String TAG = "DGMT!SFWC";
	private static final String KEY_FOR_HISTORY = "situp-history";

	public SitupFullWorkoutController() {
		super(null, 1);
	}

	@Override
	public ExerciseSet getSetForDay(int day_) {
		return Workout.getSitupSetFor(getWeek(), day_, getLevel());
	}

	@Override
	protected int numberOfDays() {
		return Workout.daysForSitupWeek(getWeek());
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
		return Type.SITUP;
	}

}
