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
        Map<String,Object> entry = new HashMap<String,Object>();
        entry.put(WEEK,7);
        entry.put(DAY,"Final");
        workoutMap.add(entry);
        return workoutMap;
    }
    
    public static int getPositionForWeekDay(int searchedForWeek_, int searchedForDay_) {
        boolean searchingForTest = (searchedForDay_ == 0);
        int position = 0;
        search:
        for (int week = 0; week < Workout.PUSHUPS.length; week++) {
            int days = Workout.PUSHUPS[week].length;
            for(int day = 0; day < days; day++){
                if(searchingForTest && searchedForWeek_ == week+1) {break search;}
                if (searchedForWeek_ == week+1 && searchedForDay_ == day+1) { break search; }
                position++;
            }
        }
        return position;
    }
    
    public static DayAndWeek getDayAndWeekByPosition(int position_) {
        int count = 0;
        for (int week = 0; week < Workout.PUSHUPS.length; week++) {
            int days = Workout.PUSHUPS[week].length;
            for(int day = 0; day < days; day++){
                if (count == position_) { return new DayAndWeek(day+1,week+1); }
                count++;
            }
        }
        return DayAndWeek.notFound();
    }
    
    
}
