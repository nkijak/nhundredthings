package com.kinnack.nthings;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.kinnack.nthings.model.ExerciseSet;

public class Home extends Activity {
    public static final String TAG = "nthings:HOME";
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    
    public void doPushups(View target_) {
        ExerciseSet set = new ExerciseSet(new int[] {6,6,4,4,ExerciseSet.TO_EXAUSTION}, new int[] {60});
        Intent counterIntent = new Intent(this, CounterActivity.class);
        Log.d(TAG,"About to launch intent for "+CounterActivity.class.getName());
        counterIntent.putExtra(CounterActivity.INIT_COUNT_KEY, set.next());
        Log.d(TAG, "Skipping rest of "+set.next());
        Log.d(TAG, "Intent about to start");
        startActivity(counterIntent);
        Log.d(TAG, "Intent started and returned");
        
    }
}