package com.kinnack.nthings.model;

import android.util.Log;

import com.kinnack.nthings.model.level.Level;

public class Workout {
    public enum Type {
        PUSHUP("Push Ups"),
        SITUP("Sit Ups"),
        SQUAT("Squats");
        
        private String label;
        
        Type(String label_) {
            label = label_;
        }
        
        public String getLabel() {
            return label;
        }
    }
    public static final int[] LONG_REST = new int[]{120000};
    public static final int[] MEDIUM_REST = new int[]{90000};
    public static final int[] STANDARD_REST = new int[]{60000};
    public static final int[] SHORT_REST = new int[]{45000};
    
    public static final int EASY = 0;
    public static final int MED = 1;
    public static final int HARD = 2;
    //[week][day][level][counts][rests]
    protected static final int[][][][][] PUSHUPS = new int[][][][][] {
        // week 1
        {
            // day 1 
            {
                // easy 
                {
                    { 2, 3, 2, 2, 3 }, STANDARD_REST
                },
                // med
                {
                    { 6, 6, 4, 4, 5 }, STANDARD_REST                    
                },
                // hard
                {
                   { 10, 12, 7, 7, 9 }, STANDARD_REST
                }
            },
            // day 2
            {
                // easy 
                {
                    { 3, 4, 2, 3, 4 }, MEDIUM_REST
                },
                // med
                {
                    { 6, 8, 6, 6, 7 }, MEDIUM_REST
                },
                // hard
                {
                    { 10, 12, 8, 8, 12 }, MEDIUM_REST
                }
            },
            // day 3
            {
                // easy
                {
                    { 4, 5, 4, 4, 5 }, LONG_REST                  
                },
                // med
                {
                    { 8, 10, 7, 7, 10 }, LONG_REST
                },
                // hard
                {                    
                    { 11, 15, 9, 9, 13}, LONG_REST
                }
            }
        },
        // week 2
        {
            // day 1 
            {
                // easy 
                {
                    { 4, 6, 4, 4, 6 }, STANDARD_REST
                },
                // med
                {
                    { 9, 11, 8, 8, 11 }, STANDARD_REST
                },
                // hard
                {
                    { 14, 14, 10, 10, 15 }, STANDARD_REST
                }
            },
            // day 2
            {
                // easy 
                {
                    { 5, 6, 4, 4, 7 }, MEDIUM_REST
                },
                // med
                {
                    { 10, 12, 9, 9, 13 }, MEDIUM_REST
                },
                // hard
                {
                    { 14, 16, 12, 12, 17 }, MEDIUM_REST
                }
            },
            // day 3
            {
                // easy
                {
                    { 5, 7, 5, 5, 8 }, LONG_REST
                },
                // med
                {
                    { 12, 13, 10, 10, 15 }, LONG_REST
                },
                // hard
                {                    
                   { 16, 17, 14,14, 20  }, LONG_REST
                }
            }
        },
        // week 3
        {
            // day 1 
            {
                // easy 
                {
                    { 10, 12, 7, 7, 9 }, STANDARD_REST
                },
                // med
                {
                    { 12, 17, 13, 13, 17 }, STANDARD_REST
                },
                // hard
                {
                    { 14, 18, 14, 14, 20  }, STANDARD_REST
                }
            },
            // day 2
            {
                // easy 
                {
                    { 10, 12, 8, 8, 12 }, MEDIUM_REST
                },
                // med
                {
                    { 14, 19, 14, 14, 19 }, MEDIUM_REST
                },
                // hard
                {
                    { 20, 25, 15, 15, 25 }, MEDIUM_REST
                }
            },
            // day 3
            {
                // easy
                {
                    { 11, 13, 9, 9, 13 }, LONG_REST
                },
                // med
                {
                    { 16, 21, 15, 15, 21 }, LONG_REST
                },
                // hard
                {                    
                    { 22, 30, 20, 20, 28 }, LONG_REST
                }
            }
        },
        // week 4
        {
            // day 1 
            {
                // easy 
                {
                    { 12, 14, 11, 10, 16 }, STANDARD_REST
                },
                // med
                {
                    { 18, 22, 16, 16, 25}, STANDARD_REST
                },
                // hard
                {
                    { 21, 25, 21, 21, 32 }, STANDARD_REST
                }
            },
            // day 2
            {
                // easy 
                {
                    { 14, 16, 12, 12, 18 }, MEDIUM_REST
                },
                // med
                {
                    { 20, 25, 20, 20, 25 }, MEDIUM_REST
                },
                // hard
                {
                    { 25, 29, 25, 25, 36 }, MEDIUM_REST
                }
            },
            // day 3
            {
                // easy
                {
                    { 16, 18, 13, 13, 20 }, LONG_REST
                },
                // med
                {
                    { 23, 28, 23, 23, 33 }, LONG_REST
                },
                // hard
                {                   
                    { 29, 33, 29, 29, 40 }, LONG_REST
                }
            }
        },
        
        
        
        
        // week 5
        {
            // day 1 
            {
                // easy 
                {
                    { 17, 19, 15, 15, 20 }, STANDARD_REST
                },
                // med
                {
                    { 28, 35, 25, 22, 35 }, STANDARD_REST
                },
                // hard
                {
                    { 36, 40, 30, 24, 40}, STANDARD_REST
                }
            },
            // day 2
            {
                // easy 
                {
                    { 10, 10, 13, 13, 10, 10, 9, 25 }, SHORT_REST
                },
                // med
                {
                    { 18, 18, 20, 20, 14, 14, 16, 40 }, SHORT_REST
                },
                // hard
                {
                    { 19, 19, 22, 22, 18, 18, 22, 45 }, SHORT_REST
                }
            },
            // day 3
            {
                // easy
                {
                    { 13, 13, 15, 15, 12, 12, 10, 30}, SHORT_REST
                },
                // med
                {
                    { 18, 18, 20, 20, 17, 17, 20, 45}, SHORT_REST
                },
                // hard
                {                    
                    { 20, 20, 24, 24, 20, 20, 22, 50 }, SHORT_REST
                }
            }
        },
        // week 6
        {
            // day 1 
            {
                // easy 
                {
                    { 25, 30, 20, 15, 40 }, STANDARD_REST
                },
                // med
                {
                    {40, 50, 25, 25, 50 }, STANDARD_REST
                },
                // hard
                {
                    { 45, 55, 35, 30, 55}, STANDARD_REST
                }
            },
            // day 2
            {
                // easy 
                {
                    { 14, 14, 15, 15, 14, 14, 10, 10, 44 }, SHORT_REST
                },
                // med
                {
                    { 20, 20, 23, 23, 20, 20, 18, 18, 53 }, SHORT_REST
                },
                // hard
                {
                    { 22, 22, 30, 30, 24, 24, 18, 18, 58 }, SHORT_REST
                }
            },
            // day 3
            {
                // easy
                {
                    { 13, 13, 17, 17, 16, 16, 14, 14, 50 }, SHORT_REST
                },
                // med
                {
                   { 22, 22, 30, 30, 24, 24, 18, 18, 55}, SHORT_REST
                },
                // hard
                {                    
                   { 26, 26, 33, 33, 26, 26, 22, 22, 60 }, SHORT_REST
                }
            }
        }
    };
    
  //[week][day][level][counts][rests]
    protected static final int[][][][][] SITUPS = new int[][][][][] {
        // week 1
        {
            // day 1 
            {
                
                // easy
                {
                    {3,4,3,3,5},STANDARD_REST
                },
                // med
                {
                    {9,9,6,6,8},STANDARD_REST
                },
                // hard
                {
                    {15,18,10,10,14},STANDARD_REST
                }
            },
         // day 2 
            {
                
                // easy
                {
                    {6,6,3,5,6}, STANDARD_REST
                },
                // med
                {
                    {9,12,9,9,10}, STANDARD_REST
                },
                // hard
                {
                    {15,18,15,15,18}, STANDARD_REST
                }
            },
         // day 3 
            {
                
                // easy
                {
                    {6,7,6,6,8}, STANDARD_REST
                },
                // med
                {
                    {12,15,11,11,15}, STANDARD_REST
                },
                // hard
                {
                    {17,22,14,14,20}, STANDARD_REST
                }
            }
        },
     // week 2
        {
            // day 1 
            {
                
                // easy
                {
                    {6,9,6,6,9},STANDARD_REST
                },
                // med
                {
                    {14,17,12,12,17},STANDARD_REST
                },
                // hard
                {
                    {21,21,15,15,22},STANDARD_REST
                }
            },
         // day 2 
            {
                
                // easy
                {
                    {7,9,6,6,11},STANDARD_REST
                },
                // med
                {
                    {15,18,14,14,20}, STANDARD_REST
                },
                // hard
                {
                    {21,24,18,18,26}, STANDARD_REST
                }
            },
         // day 3 
            {
                
                // easy
                {
                    {8,12,8,8,12}, STANDARD_REST
                },
                // med
                {
                    {18,20,15,15,23}, STANDARD_REST
                },
                // hard
                {
                    {24,25,21,21,30}, STANDARD_REST
                }
            }
        },
     // week 3
        {
            // day 1 
            {
                
                // easy
                {
                    {15,18,11,11,14}, STANDARD_REST
                },
                // med
                {
                    {18,25,19,19,25}, STANDARD_REST
                },
                // hard
                {
                    {21,27,21,21,30}, STANDARD_REST
                }
            },
         // day 2 
            {
                
                // easy
                {
                    {15,18,12,12,18}, STANDARD_REST
                },
                // med
                {
                    {21,18,21,21,28}, STANDARD_REST
                },
                // hard
                {
                    {30,38,23,23,38}, STANDARD_REST
                }
            },
         // day 3 
            {
                
                // easy
                {
                    {17,20,14,14,20}, STANDARD_REST
                },
                // med
                {
                    {24,32,23,23,32}, STANDARD_REST
                },
                // hard
                {
                    {33,42,30,30,45}, STANDARD_REST
                }
            }
        },
     // week 4
        {
            // day 1 
            {
                
                // easy
                {
                    {18,21,17,15,24}, STANDARD_REST
                },
                // med
                {
                    {27,33,24,24,38}, STANDARD_REST
                },
                // hard
                {
                    {32,38,32,32,48}, STANDARD_REST
                }
            },
         // day 2 
            {
                
                // easy
                {
                    {21,24,18,18,27}, STANDARD_REST
                },
                // med
                {
                    {30,38,30,30,42}, STANDARD_REST
                },
                // hard
                {
                    {38,45,38,38,54}, STANDARD_REST
                }
            },
         // day 3 
            {
                
                // easy
                {
                    {24,27,20,20,30}, STANDARD_REST
                },
                // med
                {
                    {35,42,35,35,50}, STANDARD_REST
                },
                // hard
                {
                    {45,50,45,45,60}, STANDARD_REST
                }
            }
        },
     // week 5
        {
            // day 1 
            {
                
                // easy
                {
                    {26,30,23,23,30}, STANDARD_REST
                },
                // med
                {
                    {42,52,38,33,52}, STANDARD_REST
                },
                // hard
                {
                    {54,60,45,36,60}, STANDARD_REST
                }
            },
         // day 2 
            {
                
                // easy
                {
                    {15,15,20,20,15,15,15,38},SHORT_REST
                },
                // med
                {
                    {27,27,30,30,21,21,24,60}, SHORT_REST
                },
                // hard
                {
                    {30,30,36,36,27,27,33}, SHORT_REST
                }
            },
         // day 3 
            {
                
                // easy
                {
                    {18,18,22,22,18,18,15,45}, SHORT_REST
                },
                // med
                {
                    {26,26,30,30,26,26,30,67}, SHORT_REST
                },
                // hard
                {
                    {30,30,36,36,30,30,40,75}, SHORT_REST
                }
            }
        },
     // week 6
        {
            // day 1 
            {
                
                // easy
                {
                    {38,45,30,22,60}, STANDARD_REST
                },
                // med
                {
                    {60,75,38,35,75}, STANDARD_REST
                },
                // hard
                {
                    {70,85,52,45,85}, STANDARD_REST
                }
            },
         // day 2 
            {
                
                // easy
                {
                    {21,21,23,23,21,21,15,15,66}, SHORT_REST
                },
                // med
                {
                    {30,30,35,35,30,30,27,27,80}, SHORT_REST
                },
                // hard
                {
                    {33,33,45,45,36,36,32,32,90}, SHORT_REST
                }
            },
         // day 3 
            {
                
                // easy
                {
                    {20,20,26,26,24,24,21,21,75}, SHORT_REST
                },
                // med
                {
                    {33,33,45,45,34,34,27,27,90}, SHORT_REST
                },
                // hard
                {
                    {39,39,50,50,39,39,33,33,105}, SHORT_REST
                }
            }
        }
    };
        
    public static ExerciseSet getPushupSetFor(int week, int day, Level level) {
        // TODO error handling, argument checking
    	if (week == 0 || day == 0) return null;
        Log.d("dgmt:Workout.getPushupSetFor", String.format("Getting pushups for week %d, day %d, level %s",week,day,level));
        int[][] countRest = PUSHUPS[week-1][day-1][level.getIndex()];
        return new ExerciseSet(countRest[0], countRest[1]);
    }
    
    public static ExerciseSet getSitupSetFor(int week, int day, Level level) {
     // TODO error handling, argument checking
    	if (week == 0 || day == 0) return null;
        Log.d("dgmt:Workout.getSitupSetFor", String.format("Getting situps for week %d, day %d, level %s",week,day,level));
        int[][] countRest = SITUPS[week-1][day-1][level.getIndex()];
        return new ExerciseSet(countRest[0], countRest[1]);
    }
        
    
}
