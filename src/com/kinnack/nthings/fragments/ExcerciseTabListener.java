package com.kinnack.nthings.fragments;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.kinnack.nthings.activity.WorkoutActions;
import com.kinnack.nthings.activity.WorkoutSettingsActivity;
import com.kinnack.nthings.controller.FullWorkoutController;
import com.kinnack.nthings.controller.WorkoutController;
import com.kinnack.nthings.model.Workout.Type;

public class ExcerciseTabListener implements ActionBar.TabListener {
    private ExcerciseSettingsFragment mFragment;
    private final String mTag;
    private final Type type;
    private final WorkoutSettingsActivity activity;

    public ExcerciseTabListener(WorkoutSettingsActivity activity_, Type type_) {
        mTag = type_.toString();
        type = type_;
        activity = activity_;
    }

    /* The following are each of the ActionBar.TabListener callbacks */

    public void onTabSelected(Tab tab, FragmentTransaction ft) {
        // Check if the fragment is already initialized
        ft = activity.getSupportFragmentManager().beginTransaction();
        if (mFragment == null) {
            // If not, instantiate and add it to the activity
            mFragment = ExcerciseSettingsFragment.newInstance(type, (WorkoutActions)activity);
            ft.add(android.R.id.content, mFragment, mTag);
        } else {
            // If it exists, simply attach it in order to show it
            ft.show(mFragment);
        }
        ft.commit();
        activity.setFullWorkoutController(getWorkoutController());
    }

    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
        if (mFragment != null) {
            // Detach the fragment, because another one is being attached
            ft = activity.getSupportFragmentManager().beginTransaction();
            ft.hide(mFragment);
            ft.commit();
        }
    }

    public void onTabReselected(Tab tab, FragmentTransaction ft) {
        // User selected the already selected tab. Usually do nothing.
    }
    
    public FullWorkoutController getWorkoutController() {
    	return mFragment.getFullWorkoutController();
    }
}