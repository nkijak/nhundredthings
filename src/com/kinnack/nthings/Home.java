package com.kinnack.nthings;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.kinnack.nthings.model.ExerciseSet;

public class Home extends Activity {
    public static final String TAG = "nthings:HOME";
    
    private static final int COUNTER_INTENT = 100;
    private ExerciseSet set;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
    }
    
    public void doPushups(View target_) {
        set = new ExerciseSet(new int[] {6,6,4,4,3}, new int[] {60});
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
        Log.d(TAG, "Skipping rest of "+set.next());
        Log.d(TAG, "Intent about to start");
        startActivityForResult(counterIntent, COUNTER_INTENT);
        Log.d(TAG, "Intent started and returned");
    }
    
    @Override
    protected void onActivityResult(int requestCode_, int resultCode_, Intent data_) {
        switch (requestCode_) {
        case COUNTER_INTENT:
            if (!set.hasNext()) return;
            startCounterActivity();
            break;
        default:
            Log.d(TAG, "Got an unknown activity result. request["+requestCode_+"], result["+resultCode_+"]");
            break;
        }
    }
}