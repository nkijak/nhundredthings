package com.kinnack.nthings;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kinnack.nthings.controller.WorkoutController;
import com.kinnack.nthings.fragments.ExcerciseSettingsFragment;
import com.kinnack.nthings.model.Workout.Type;
import com.viewpagerindicator.TitleProvider;

public class ViewPagerAdapter extends FragmentPagerAdapter implements TitleProvider {

    private static final Type[] POSITIONS = {Type.PUSHUP, Type.SITUP}; 
    private static final ExcerciseSettingsFragment[] TITLES = {ExcerciseSettingsFragment.newInstance(Type.PUSHUP),
                                                                ExcerciseSettingsFragment.newInstance(Type.SITUP)};
    
    public ViewPagerAdapter(FragmentManager fragmentManager_) {
        super(fragmentManager_);
        
        
    }
    
    @Override
    public String getTitle(int position_) {
        return TITLES[position_].getLabel();
    }

    @Override
    public int getCount() {
        return TITLES.length;
    }

 

    @Override
    public Fragment getItem(int position_) {
        return TITLES[position_];
    }
    
    public WorkoutController getWorkoutController(int position_) {
        return TITLES[position_].getWorkoutController();
    }
    
    public int getPositionForType(Type typeToFind_) {
        for (int i = 0; i < POSITIONS.length; i++) {
           if (POSITIONS[i] == typeToFind_){ return i; }
        }
        return 0;
    }

}
