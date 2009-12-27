package com.kinnack.nthings.model;

import java.util.ArrayList;
import java.util.List;

import com.kinnack.nthings.model.level.Level;
import com.kinnack.nthings.model.level.pushup.InitialEasyLevel;
import com.kinnack.nthings.model.level.pushup.InitialHardLevel;
import com.kinnack.nthings.model.level.pushup.InitialMidLevel;
import com.kinnack.nthings.model.level.pushup.SecondEasyLevel;
import com.kinnack.nthings.model.level.pushup.SecondHardLevel;
import com.kinnack.nthings.model.level.pushup.SecondMidLevel;

public class Test {
    private static final Level[] ALL_LEVELS = new Level[] {
        new InitialEasyLevel(),
        new InitialMidLevel(),
        new InitialHardLevel(),
        new SecondEasyLevel(),
        new SecondMidLevel(),
        new SecondHardLevel()
    };
    
    private static final Level[] INITIAL_TEST_LIST = new Level[]{
        ALL_LEVELS[0],
        ALL_LEVELS[1],
        ALL_LEVELS[2],
        ALL_LEVELS[4],
        ALL_LEVELS[5]
    };
    
    private static final Level[] SECOND_TEST_LIST = new Level[] {
        ALL_LEVELS[3],
        ALL_LEVELS[4],
        ALL_LEVELS[5]
    } ;
    
    public static Level initialTestLevel(int count_) {
        for(Level level : INITIAL_TEST_LIST) {
            if (level.checkLevel(count_)) {return level;}
        }
        return INITIAL_TEST_LIST[0];
    }
    
    public static Level secondTestLevel(int count_) {
        for(Level level: SECOND_TEST_LIST) {
            if (level.checkLevel(count_)) { return level;}
        }
        return SECOND_TEST_LIST[0];
    }
    
    public static Level findLevelForWeekByIndex(int week_, int index_) {
        for (Level level : ALL_LEVELS) {
            if (week_ >= level.getStartWeek() && index_ == level.getIndex()) return level;
        }
        return ALL_LEVELS[0];
    }
}
