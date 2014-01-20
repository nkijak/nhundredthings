package com.kinnack.nthings.activity;

import android.app.ListActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import com.kinnack.nthings.controller.PushupWorkoutController;
import com.kinnack.nthings.model.HistoricViewAdapter;
import com.kinnack.nthings.model.History;
import com.kinnack.nthings.model.Workout;
import org.json.JSONException;

/**
* This isn't used anymore. It was a list of entries. Eventually to allow for editing records.
**/
public class HistoryActivity extends ListActivity {
    private History pushupHistory;
    private final static String TAG = "DGMT:HistoryAction";
    
    @Override
    protected void onCreate(Bundle savedInstanceState_) {
        super.onCreate(savedInstanceState_);
        
        SharedPreferences prefs = getSharedPreferences(WorkoutSettingsActivity.PREFS, Context.MODE_PRIVATE);
        loadPushupHistory(prefs);
        
        ListAdapter adapter = new HistoricViewAdapter(this, pushupHistory);
        setListAdapter(adapter);
    }
    
    
    /**
     * @param prefs
     */
    private void loadPushupHistory(SharedPreferences prefs) {
        if (pushupHistory != null) { return; }
        try {
            pushupHistory = new History(prefs.getString(PushupWorkoutController.KEY_HISTORY, null));
        } catch (JSONException e) {
            Log.e(TAG, "Couldn't unmarshal history", e);
        } catch (NullPointerException npe) {
            Log.i(TAG, "No history to load");
        }
        if (pushupHistory == null) {
            pushupHistory = new History();
            pushupHistory.setDay(0);
            pushupHistory.setWeek(1);
            pushupHistory.setType(Workout.Type.PUSHUP);
        }
    }
}
