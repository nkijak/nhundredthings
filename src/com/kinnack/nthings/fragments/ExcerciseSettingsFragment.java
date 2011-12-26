package com.kinnack.nthings.fragments;

import com.kinnack.nthings.R;
import com.kinnack.nthings.controller.PushupWorkoutController;
import com.kinnack.nthings.controller.SitupWorkoutController;
import com.kinnack.nthings.controller.WorkoutController;
import com.kinnack.nthings.model.Workout.Type;

import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ExcerciseSettingsFragment extends Fragment {
    private static final String WORKOUT_TYPE = "workout-type";
    
    private WorkoutController workoutController;
    private Editor prefEditor;
    private Type type;
    
    
    public static ExcerciseSettingsFragment newInstance(Type workoutType_) {
        ExcerciseSettingsFragment fragment = new ExcerciseSettingsFragment();
        
        Bundle args = new Bundle();
        args.putString(WORKOUT_TYPE, workoutType_.toString());
        fragment.setArguments(args);
        
        return fragment;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState_) {
        super.onCreate(savedInstanceState_);
        
        if (savedInstanceState_ == null) savedInstanceState_= getArguments();
        
        type = Type.valueOf(savedInstanceState_.getString(WORKOUT_TYPE));
        
        switch (type) {
            case PUSHUP:
                workoutController = new PushupWorkoutController();
                break;
            case SITUP:
                workoutController = new SitupWorkoutController();
                break;
        }
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater_, ViewGroup container_, Bundle savedInstanceState_) {
        View view = inflater_.inflate(R.layout.excercise_settings, container_, false);
        
        return view;
    }
    
    public String getLabel() { return type.getLabel(); }
    public WorkoutController getWorkoutController() { return workoutController; }
}
