package com.kinnack.nthings.model;

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
            retval = counts[countIndex];
            if(countIndex+1>=counts.length) {
                countIndex = 0;
                wrappedCounts = true;
            } else { 
                countIndex++; 
            }
            
        } else {
            retval = rests[restIndex];
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
    
    
    
}
