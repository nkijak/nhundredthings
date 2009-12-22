package com.kinnack.nthings;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.kinnack.nthings.activity.RestActivity;
import com.kinnack.nthings.model.ExerciseSet;
import com.kinnack.nthings.model.Workout;

public class Home extends Activity {
    public static final String TAG = "nthings:HOME";
    
    private static final int COUNTER_INTENT = 100;
    private static final int REST_INTENT = 200;
    
    private ExerciseSet set;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
    }
    
    public void doPushups(View target_) {
        set = Workout.getPushupSetFor(1, 1, Workout.EASY);
        startCounterActivity();
        
    }

    /**
     * 
     */
    private void startCounterActivity() {
        Intent counterIntent = new Intent(this, CounterActivity.class);
        Log.d(TAG,"About to launch intent for "+CounterActivity.class.getName());
        counterIntent.putExtra(CounterActivity.INIT_COUNT_KEY, set.next());
        counterIntent.putExtra(CounterActivity.SHOW_DONE, set.isMax());
        Log.d(TAG, "Intent about to start");
        startActivityForResult(counterIntent, COUNTER_INTENT);
        Log.d(TAG, "Intent started and returned");
    }
    
    private void startRestActivity() {
        Intent restIntent = new Intent(this, RestActivity.class);
        Log.d(TAG, "About to launch intnet for "+RestActivity.class.getName());
        restIntent.putExtra(RestActivity.REST_LENGTH, set.next());
        Log.d(TAG, "Rest about to start");
        startActivityForResult(restIntent, REST_INTENT);
    }
    
    @Override
    protected void onActivityResult(int requestCode_, int resultCode_, Intent data_) {
        switch (requestCode_) {
        case COUNTER_INTENT:
            if (!set.hasNext()) return;
            startRestActivity();
            break;
        case REST_INTENT:
            startCounterActivity();
            break;
        default:
            Log.d(TAG, "Got an unknown activity result. request["+requestCode_+"], result["+resultCode_+"]");
            break;
        }
    }
}