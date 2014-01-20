package com.kinnack.nthings.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;

import com.kinnack.nthings.R;
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

    final View[] WEEKS = new View[]{_week1, _week2, _week3, _week4, _week5, _week6};
    final View[] DAYS = new View[] {_day1, _day2, _day3};
    final View[] LEVELS = new View[] { _levelEasy, _levelMid, _levelHard};

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

    }

    public void selectDay(int day_) {
        Log.d(TAG, "Selected day "+day_);
    }

    public void selectLevel(Level level_){
        Log.d(TAG, "Selected level "+level_);
    }


    private void setupOnClickHandlers() {
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
        _levelEasy.setOnClickListener(new HoldThisForMeOnClickListener<Level>(new InitialMidLevel()) {
            @Override
            public void onClick(View view) {
                LevelSelectorFragment.this.selectLevel(_heldThing);
            }
        });
        _levelEasy.setOnClickListener(new HoldThisForMeOnClickListener<Level>(new InitialHardLevel()) {
            @Override
            public void onClick(View view) {
                LevelSelectorFragment.this.selectLevel(_heldThing);
            }
        });

    
    }

    abstract class HoldThisForMeOnClickListener<T> implements View.OnClickListener {
        T _heldThing;
        public HoldThisForMeOnClickListener(T holdThis_) { _heldThing = holdThis_; }
    }
}
