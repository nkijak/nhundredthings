package com.kinnack.nthings.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.support.v7.widget.GridLayout;

import com.kinnack.nthings.R;
import com.kinnack.nthings.controller.WorkoutController;
import com.kinnack.nthings.model.DayAndWeek;
import com.kinnack.nthings.model.level.EasyLevel;
import com.kinnack.nthings.model.level.Level;
import com.kinnack.nthings.model.level.pushup.InitialEasyLevel;
import com.kinnack.nthings.model.level.pushup.InitialHardLevel;
import com.kinnack.nthings.model.level.pushup.InitialMidLevel;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class LevelSelectorFragment extends Fragment {
    @InjectView(R.id.week1) Button _week1;
    @InjectView(R.id.week2) Button _week2;
    @InjectView(R.id.week3) Button _week3;
    @InjectView(R.id.week4) Button _week4;
    @InjectView(R.id.week5) Button _week5;
    @InjectView(R.id.week6) Button _week6;

    @InjectView(R.id.day1) Button _day1;
    @InjectView(R.id.day2) Button _day2;
    @InjectView(R.id.day3) Button _day3;

    @InjectView(R.id.levelEasy) Button _levelEasy;
    @InjectView(R.id.levelMid) Button _levelMid;
    @InjectView(R.id.levelHard) Button _levelHard;


    private WorkoutController workoutController;


    private LevelSelectChangeListener levelSelectorChangeListener;

    final static String TAG = "DGMT:LevelSelectorFragment";

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.level_selector_fragment, container, false);
        GridLayout layout = (GridLayout) view.findViewById(R.id.selectorGridLayout);
        ButterKnife.inject(this, view);
        setupOnClickHandlers();
        return view;
    }

    public void selectWeek(int week_) {
        Log.d(TAG, "Selected week "+week_);

        DayAndWeek dayAndWeek = workoutController.getDayAndWeek().changeWeek(week_);
        updateDayAndWeek(dayAndWeek);
    }
    public void selectDay(int day_) {
        Log.d(TAG, "Selected day "+day_);
        DayAndWeek dayAndWeek = workoutController.getDayAndWeek().changeDay(day_);
        updateDayAndWeek(dayAndWeek);
    }

    private void updateDayAndWeek(DayAndWeek dayAndWeek) {
        boolean dayOrWeekChanged = !dayAndWeek.equals(workoutController.getDayAndWeek());
        if (dayAndWeek.wasFound() && !workoutController.isTest() && dayOrWeekChanged ) {
            Log.d("dgmt!dayWeekSelectorItemSelect","day and week has changed");
            workoutController.setDayAndWeek(dayAndWeek);
            levelSelectorChangeListener.dayWeekOrLevelChanged();
        }
    }

    public void selectLevel(Level level_){
        Log.d(TAG,"Level selected "+level_);

        boolean levelChanged = !level_.equals(workoutController.getCurrentLevel());
        Log.d("dgmt!levelSelectorItemSelect","Level changed?"+levelChanged+". current="+workoutController.getCurrentLevel()+"] selected="+level_);
        //TODO how handle tests?
//        if(position_ != 3 && workoutController.setCurrentLevel(level)) {
//            Log.d("dgmt!levelSelectorItemSelect", "Level has actually changed");
//            findViewById(R.id.dayWeekSelector).setEnabled(true);
//        }
        if (levelChanged) {levelSelectorChangeListener.dayWeekOrLevelChanged();}
    }

    protected void configureButtons() {
        int levelIndex = workoutController.getCurrentLevel().getIndex();

        _levelEasy.setEnabled(levelIndex >= 0);
        _levelMid.setEnabled(levelIndex > 1);
        _levelHard.setEnabled(levelIndex > 2);

        DayAndWeek dayAndWeek = workoutController.getDayAndWeek();
        _day1.setEnabled(dayAndWeek.day >= 0);
        _day2.setEnabled(dayAndWeek.day > 1);
        _day3.setEnabled(dayAndWeek.day > 2);

        _week1.setEnabled(dayAndWeek.week >= 0);
        _week2.setEnabled(dayAndWeek.week > 1);
        _week3.setEnabled(dayAndWeek.week > 2);
        _week4.setEnabled(dayAndWeek.week > 3);
        _week5.setEnabled(dayAndWeek.week > 4);
        _week6.setEnabled(dayAndWeek.week > 5);
    }


    private void setupOnClickHandlers() {
        final View[] WEEKS = new View[]{_week1, _week2, _week3, _week4, _week5, _week6};
        final View[] DAYS = new View[] {_day1, _day2, _day3};

        for (int i = 0, len = WEEKS.length; i < len; i++) {
            WEEKS[i].setOnClickListener(new HoldThisForMeOnClickListener<Integer>(i){
                @Override
                public void onClick(View view) {
                    LevelSelectorFragment.this.selectWeek(_heldThing + 1);
                }
            });
        }
        for (int i = 0, len = DAYS.length; i < len; i++) {
            DAYS[i].setOnClickListener(new HoldThisForMeOnClickListener<Integer>(i) {
                @Override
                public void onClick(View view) {
                    LevelSelectorFragment.this.selectDay(_heldThing + 1);
                }
            });
        }


        _levelEasy.setOnClickListener(new HoldThisForMeOnClickListener<Level>(new InitialEasyLevel()) {
            @Override
            public void onClick(View view) {
                LevelSelectorFragment.this.selectLevel(_heldThing);
            }
        });
        _levelMid.setOnClickListener(new HoldThisForMeOnClickListener<Level>(new InitialMidLevel()) {
            @Override
            public void onClick(View view) {
                LevelSelectorFragment.this.selectLevel(_heldThing);
            }
        });
        _levelHard.setOnClickListener(new HoldThisForMeOnClickListener<Level>(new InitialHardLevel()) {
            @Override
            public void onClick(View view) {
                LevelSelectorFragment.this.selectLevel(_heldThing);
            }
        });



    }

    public void setWorkoutController(WorkoutController workoutController_) {
        this.workoutController = workoutController_;
        configureButtons();
    }
    public void setLevelSelectorChangeListener(LevelSelectChangeListener levelSelectorChangeListener_) {
        this.levelSelectorChangeListener = levelSelectorChangeListener_;
    }

    abstract class HoldThisForMeOnClickListener<T> implements View.OnClickListener {
        T _heldThing;
        public HoldThisForMeOnClickListener(T holdThis_) { _heldThing = holdThis_; }
    }

    public interface LevelSelectChangeListener {
        void dayWeekOrLevelChanged();
    }
}
