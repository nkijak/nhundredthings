package com.kinnack.nthings.model.level.situp;

import com.kinnack.nthings.model.level.Level;

public class SitupTest {
    private static final Level[] ALL_LEVELS = new Level[] {
        new InitialEasyLevel(),
        new InitialMidLevel(),
        new InitialHardLevel(),
        new InitialSkipWeekHardLevel(),
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
        ALL_LEVELS[3]
    };
    
    private static final Level[] WEEK_3_TEST_LIST = new Level[] {
        ALL_LEVELS[4],
        ALL_LEVELS[5],
        ALL_LEVELS[6]
    } ;
    
    private static final Level[] WEEK_5_TEST_LIST = new Level[] {
        ALL_LEVELS[7],
        ALL_LEVELS[8],
        ALL_LEVELS[9]
    };
    
    private static final Level[] WEEK_6_TEST_LIST = new Level[] {
        ALL_LEVELS[10],
        ALL_LEVELS[11],
        ALL_LEVELS[12]
    };
    
    public static Level getLevelForTestResultsByWeek(int results_,int week_) {
        Level level = null;
        switch(week_) {
            case 1:
                level = initialTestLevel(results_);
                break;
            case 3:
                level = secondTestLevel(results_);
                break;
            case 5:
                level = thirdTestLevel(results_);
            case 6:                   
                level = fourthTestLevel(results_);
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
    
   

}
