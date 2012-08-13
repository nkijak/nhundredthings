package com.kinnack.nthings.controller;

import com.kinnack.nthings.R;
import com.kinnack.nthings.activity.WorkoutActions;
import com.kinnack.nthings.model.ExerciseSet;
import com.kinnack.nthings.model.Test;
import com.kinnack.nthings.model.Workout;
import com.kinnack.nthings.model.Workout.Type;
import com.kinnack.nthings.model.level.Level;

public class PushupFullWorkoutController extends FullWorkoutController {
	private static final String TAG = "DGMT!PFWC";
	private static final String KEY_FOR_HISTORY = "history";

	public PushupFullWorkoutController(WorkoutActions actions_) {
		super(actions_,null,1);
	}
	
	public PushupFullWorkoutController(WorkoutActions actions_, Level level_, int week_) {
		super(actions_, level_, week_);
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

	@Override
    public int getLabelResource() {
        return R.string.pushups_label;
    }

    @Override
    public Level getLevelForTestResult(int testCount_) {
        return Test.getLevelForTestResultsByWeek(testCount_, getWeek());
    }

	@Override
	public int getFinalTestCount() {
		return 100;
	}
}
