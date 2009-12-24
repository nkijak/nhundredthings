package com.kinnack.nthings.model;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.kinnack.nthings.model.level.Level;

public class History {
    private List<Integer> _testResults;
    private Level _currentLevel;
    private List<Log> _logs;
    private int _week;
    private int _day;
    private Workout.Type _type;
    
    class Log {
        private List<Integer> _counts;
        private int _week;
        private int _day;
        
        
        /**
         * @return the week
         */
        public int getWeek() {
            return _week;
        }
        /**
         * @param week_ the week to set
         */
        public void setWeek(int week_) {
            _week = week_;
        }
        /**
         * @return the day
         */
        public int getDay() {
            return _day;
        }
        /**
         * @param day_ the day to set
         */
        public void setDay(int day_) {
            _day = day_;
        }
        public void addCount(Integer count_) {
            _counts.add(count_);
        }
        /**
         * @return the counts
         */
        public List<Integer> getCounts() {
            return _counts;
        }

        /**
         * @param counts_ the counts to set
         */
        public void setCounts(List<Integer> counts_) {
            _counts = counts_;
        } 
        
        public JSONObject toJSON() throws JSONException {
            JSONObject self = new JSONObject();
            self.put("week", _week);
            self.put("day", _day);
            self.put("counts", new JSONArray(_counts));
            return self;
            
        }
    
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject self = new JSONObject();
        self.put("week",_week)
            .put("day", _day)
            .put("level", _currentLevel.toJSON())
            .put("testResults", new JSONArray(_testResults))
            .put("type", _type.toString());
        for(Log log : _logs) {
            self.accumulate("logs", log.toJSON());
        }
        return self;
    }
    

    /**
     * @return the testResults
     */
    public List<Integer> getTestResults() {
        return _testResults;
    }


    /**
     * @param testResults_ the testResults to set
     */
    public void setTestResults(List<Integer> testResults_) {
        _testResults = testResults_;
    }


    /**
     * @return the currentLevel
     */
    public Level getCurrentLevel() {
        return _currentLevel;
    }


    /**
     * @param currentLevel_ the currentLevel to set
     */
    public void setCurrentLevel(Level currentLevel_) {
        _currentLevel = currentLevel_;
    }


    /**
     * @return the logs
     */
    public List<Log> getLogs() {
        return _logs;
    }


    /**
     * @param logs_ the logs to set
     */
    public void setLogs(List<Log> logs_) {
        _logs = logs_;
    }


    /**
     * @return the week
     */
    public int getWeek() {
        return _week;
    }


    /**
     * @param week_ the week to set
     */
    public void setWeek(int week_) {
        _week = week_;
    }


    /**
     * @return the day
     */
    public int getDay() {
        return _day;
    }


    /**
     * @param day_ the day to set
     */
    public void setDay(int day_) {
        _day = day_;
    }
    
}
