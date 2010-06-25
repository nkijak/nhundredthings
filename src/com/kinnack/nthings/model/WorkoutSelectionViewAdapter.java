package com.kinnack.nthings.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kinnack.nthings.R;

import android.content.Context;
import android.util.Log;
import android.widget.SimpleAdapter;

public class WorkoutSelectionViewAdapter extends SimpleAdapter{
    public static final String WEEK="week";
    public static final String DAY="day";
    
    public WorkoutSelectionViewAdapter(Context context_) {
        super(context_, workoutToViewMap(),R.layout.current_summary ,
                    new String[]{WEEK,DAY},
                    new int[]{R.id.HomeCurrentWeek,R.id.HomeCurrentDay});
    }
    
    private static List<Map<String, Object>> workoutToViewMap() {
        List<Map<String,Object>> workoutMap = new ArrayList<Map<String,Object>>();
        for (int week = 0; week < Workout.PUSHUPS.length; week++) {
            int days = Workout.PUSHUPS[week].length;
            for(int day = 0; day < days; day++){
                Map<String,Object> entry = new HashMap<String,Object>();
                entry.put(WEEK,week+1);
                entry.put(DAY,day+1);
                workoutMap.add(entry);
            }
        }
        return workoutMap;
    }
    
    public int getPositionForWeekDay(int week_, int day_) {
        int position = 0;
        search:
        for (int week = 0; week < Workout.PUSHUPS.length; week++) {
            int days = Workout.PUSHUPS[week].length;
            for(int day = 0; day < days; day++){
                position++;
                if (week_ == week+1 && day_ == day+1) { break search; }
            }
        }
        return position;
    }
}
