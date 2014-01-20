package com.kinnack.nthings.model;

import android.util.Log;

public class ExerciseSet {
    private int[] counts;
    private int[] rests;
    
    private int countIndex;
    private int restIndex;
    private boolean onCount;
    private boolean wrappedCounts;
    private boolean wrappedRests;
    
    public static final int TO_EXAUSTION = -1;
    
    public ExerciseSet(int[] counts_, int[] rests_) {
        Log.d("ExerciseSet:constructor","Creating ExerciseSet");
        counts = counts_;
        rests = rests_;
        countIndex = 0;
        restIndex = 0;
        onCount = true;
    }
    
    public int next() {
        int retval = -2;
        onCount = !onCount;
        if (!onCount) {
            Log.d("ExerciseSet:next","returning next count at index "+countIndex);
            retval = counts[countIndex];
            if(countIndex+1>=counts.length) {
                Log.d("ExerciseSet:next", (countIndex+1)+" is >= "+counts.length+" wrapping back to index 0");
                countIndex = 0;
                wrappedCounts = true;
            } else { 
                countIndex++; 
            }
            
        } else {
            retval = rests[restIndex];
            Log.d("ExerciseSet:next","returning next rest at index "+restIndex);
            if (restIndex+1>=rests.length) {
                restIndex = 0;
                wrappedRests = true;
            } else {
                restIndex++;
            }
        }
        return retval;
    }
    
    public boolean hasNext() {
        boolean stillHasCounts = (countIndex < counts.length) && !wrappedCounts;
        boolean stillHasRests = (restIndex < rests.length) && !wrappedRests;
        return stillHasCounts || stillHasRests;
    }
    
    public boolean isMax() {
        return !onCount && countIndex == 0 && wrappedCounts;
    }
    
    public int getSetsToGo() {
        int totalSets = (counts.length > rests.length)? counts.length: rests.length+1;
        return totalSets - getSetsDone();
    }
    
    public int getSetsDone() {
        return countIndex;
    }
    
    public int getCountLeft() {
        int totalCount = 0;
        for (int i = countIndex; i < counts.length; i++) {
            totalCount += counts[i];
        }
        return totalCount;
    }

    public int[] getCounts() {
        return counts;
    }
    
    public int[] getRests() {
        return rests;
    }
    
    public int[] getMinMaxCounts() {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for(int count : counts) {
            if (count > max) max = count;
            if (count < min) min = count;
        }
        
        Log.d("DGMT!ExerciseSet", "Found min ["+min+"] and max ["+max+"] for set");
        return new int[]{min,max};
    }
}
