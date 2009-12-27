package com.kinnack.nthings;

import org.json.JSONException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.kinnack.nthings.activity.RestActivity;
import com.kinnack.nthings.model.ExerciseSet;
import com.kinnack.nthings.model.History;
import com.kinnack.nthings.model.Test;
import com.kinnack.nthings.model.Workout;
import com.kinnack.nthings.model.level.Level;

public class Home extends Activity {
    public static final String TAG = "nthings:HOME";
    
    private static final int COUNTER_INTENT = 100;
    private static final int TEST_INTENT = 150;
    private static final int REST_INTENT = 200;
    
    public static final String PREFS = "prefs_config";
    public static final String KEY_CURRENT_WEEK = "current_week";
    public static final String KEY_CURRENT_DAY = "current_day";
    public static final String KEY_CURRENT_LEVEL = "current_level";
    public static final String KEY_HISTORY = "history";
    
    private ExerciseSet set;

    private Editor prefEditor;
    private History pushupHistory;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        SharedPreferences prefs = getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        prefEditor = prefs.edit();
        
        Log.d(TAG,"Loaded history as "+prefs.getString(KEY_HISTORY, "[Not found]"));
        
        try {
            pushupHistory = new History(prefs.getString(KEY_HISTORY, null));
        } catch (JSONException e) {
            Log.e(TAG, "Couldn't unmarshal history", e);
        } catch (NullPointerException npe) {
            Log.i(TAG, "No history to load");
        }
        
        setWeekText();
        
    }

    /**
     * 
     */
    private void setWeekText() {
        TextView currentWeek = (TextView)findViewById(R.id.HomeCurrentWeek);
        String value = (pushupHistory == null ? "1" : ""+pushupHistory.getWeek());
        currentWeek.setText(value);
    }
    
    public void doPushups(View target_) {
        if (pushupHistory == null) {
            pushupHistory = new History();
            pushupHistory.setDay(0);
            pushupHistory.setWeek(1);
            pushupHistory.setType(Workout.Type.PUSHUP);
        }
        if (pushupHistory.getDay() == 0) { startTestActivity(); return;}
        History.Log newLog = pushupHistory.new Log(pushupHistory.getWeek(),pushupHistory.getDay());
        
        pushupHistory.getLogs().add(newLog);
        set = Workout.getPushupSetFor(pushupHistory.getWeek(), pushupHistory.getDay(), pushupHistory.getCurrentLevel());
        
        
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
    
    private void startTestActivity() {
        Intent counterIntent = new Intent(this, CounterActivity.class);
        Log.d(TAG,"About to launch intent for "+CounterActivity.class.getName());
        counterIntent.putExtra(CounterActivity.INIT_COUNT_KEY, 0);
        counterIntent.putExtra(CounterActivity.SHOW_DONE, true);
        Log.d(TAG, "Intent about to start");
        startActivityForResult(counterIntent, TEST_INTENT);
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
            int count = data_.getExtras().getInt(CounterActivity.MAX_COUNT);
            pushupHistory.getCurrentLog().addCount(count);
            if (!set.hasNext()) { 
                advanceDate();
                saveHistory(); 
                return; 
            }
            startRestActivity();
            break;
        case REST_INTENT:
            startCounterActivity();
            break;
        case TEST_INTENT:
            int test_count = data_.getExtras().getInt(CounterActivity.MAX_COUNT);
            Level level = Test.initialTestLevel(test_count);
            pushupHistory.getTestResults().add(0,test_count);
            pushupHistory.setCurrentLevel(level);
            pushupHistory.setDay(1);
            saveHistory();
            Toast.makeText(this, level.toString(), Toast.LENGTH_SHORT).show();
            break;
        default:
            Log.d(TAG, "Got an unknown activity result. request["+requestCode_+"], result["+resultCode_+"]");
            break;
        }
        
    }
    
    private void advanceDate() {
        int day = pushupHistory.getDay();
        if (day == 3) {
            pushupHistory.setDay(1);
            pushupHistory.setWeek(pushupHistory.getWeek()+1);
            setWeekText();
        } else {
            pushupHistory.setDay(day+1);
        }
        
    }
    
    private void saveHistory() {
        
        try {
            prefEditor.putString(KEY_HISTORY, pushupHistory.toJSON().toString());
            Log.d(TAG, "Saved history as "+pushupHistory.toJSON().toString());
        } catch (JSONException e) {
            Log.e(TAG,"Couldn't convert history to JSON! ",e);
            Toast.makeText(this, "Error saving history", Toast.LENGTH_SHORT);
        }
        prefEditor.commit();
    }
}