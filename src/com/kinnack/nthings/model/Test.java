package com.kinnack.nthings.model;

import android.util.Log;

import com.kinnack.nthings.model.level.Level;
import com.kinnack.nthings.model.level.pushup.FourthEasyLevel;
import com.kinnack.nthings.model.level.pushup.FourthHardLevel;
import com.kinnack.nthings.model.level.pushup.FourthMidLevel;
import com.kinnack.nthings.model.level.pushup.InitialEasyLevel;
import com.kinnack.nthings.model.level.pushup.InitialHardLevel;
import com.kinnack.nthings.model.level.pushup.InitialMidLevel;
import com.kinnack.nthings.model.level.pushup.SecondEasyLevel;
import com.kinnack.nthings.model.level.pushup.SecondHardLevel;
import com.kinnack.nthings.model.level.pushup.SecondMidLevel;
import com.kinnack.nthings.model.level.pushup.ThirdEasyLevel;
import com.kinnack.nthings.model.level.pushup.ThirdHardLevel;
import com.kinnack.nthings.model.level.pushup.ThirdMidLevel;

public class Test {
    private static final Level[] ALL_LEVELS = new Level[] {
        new InitialEasyLevel(),
        new InitialMidLevel(),
        new InitialHardLevel(),
        new SecondEasyLevel(),
        new SecondMidLevel(),
        new SecondHardLevel(),
        new ThirdEasyLevel(),
        new ThirdMidLevel(),
        new ThirdHardLevel(),
        new FourthEasyLevel(),
        new FourthMidLevel(),
        new FourthHardLevel()
    };
    
    private static final Level[] INITIAL_TEST_LIST = new Level[]{
        ALL_LEVELS[0],
        ALL_LEVELS[1],
        ALL_LEVELS[2],
        ALL_LEVELS[4],
        ALL_LEVELS[5]
    };
    
    private static final Level[] WEEK_3_TEST_LIST = new Level[] {
        ALL_LEVELS[3],
        ALL_LEVELS[4],
        ALL_LEVELS[5]
    } ;
    
    private static final Level[] WEEK_5_TEST_LIST = new Level[] {
        ALL_LEVELS[6],
        ALL_LEVELS[7],
        ALL_LEVELS[8]
    };
    
    private static final Level[] WEEK_6_TEST_LIST = new Level[] {
        ALL_LEVELS[9],
        ALL_LEVELS[10],
        ALL_LEVELS[11]
    };
    
    public static Level getLevelForTestResultsByWeek(int results_,int week_) {
        Level level = null;
        switch(week_) {
            case 1:
                level = Test.initialTestLevel(results_);
                break;
            case 3:
                level = Test.secondTestLevel(results_);
                break;
            case 5:
                level = Test.thirdTestLevel(results_);
            case 6:                   
                level = Test.fourthTestLevel(results_);
                break;
        }
        return level;
    }
    
    public static Level initialTestLevel(int count_) {
        return findLevel(INITIAL_TEST_LIST, count_);
    }
    
    public static Level secondTestLevel(int count_) {
        return findLevel(WEEK_3_TEST_LIST,count_);
    }
    
    public static Level thirdTestLevel(int count_) {
        return findLevel(WEEK_5_TEST_LIST, count_);
    }
    
    public static Level fourthTestLevel(int count_) {
        return findLevel(WEEK_6_TEST_LIST, count_);
    }
    protected static Level findLevel(Level[] testList_, int count_) {
        for(Level level: testList_) {
            if (level.checkLevel(count_)) { return level;}
        }
        return testList_[0];
    }
    
    public static Level findLevelForWeekByIndex(int week_, int index_) {
        for (Level level : ALL_LEVELS) {
            if (week_ >= level.getStartWeek() && index_ == level.getIndex()) return level;
        }
        return ALL_LEVELS[0];
    }
}
