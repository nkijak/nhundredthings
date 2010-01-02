package com.kinnack.nthings.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.kinnack.nthings.model.level.GenericLevel;
import com.kinnack.nthings.model.level.Level;

public class History {
    private List<Integer> _testResults = new ArrayList<Integer>();
    private Level _currentLevel;
    private List<Log> _logs = new ArrayList<Log>();
    private int _week;
    private int _day;
    private Workout.Type _type;
    private Date _lastWorkout;
    
    public History() {};
    
    public class Log {
        private List<Integer> _counts = new ArrayList<Integer>();
        private int _week;
        private int _day;
        
       
        
        public Log(int week_, int day_) {
            _week = week_;
            _day = day_;
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
        
        public Log(JSONObject json_) throws JSONException{
            _week = json_.getInt("week");
            _day = json_.getInt("day");
            JSONArray counts = json_.getJSONArray("counts");
            for(int i =0,len = counts.length(); i < len; i++){
                _counts.add(counts.getInt(i));
            }
        }
    
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject self = new JSONObject();
        self.put("week",_week)
            .put("day", _day)
            .put("level", _currentLevel.toJSON())
            .put("testResults", new JSONArray(_testResults))
            .put("type", _type.toString());
        self.put("logs", new JSONArray());
        for(Log log : _logs) {
            self.accumulate("logs", log.toJSON());
        }
        return self;
    }
    
    public History(String jsonHistory_) throws JSONException {
        JSONObject json = new JSONObject(jsonHistory_);
        setDay(json.getInt("day"));
        setWeek(json.getInt("week"));
        JSONArray testResults = json.getJSONArray("testResults");
        for(int i = 0,len = testResults.length(); i < len; i++) {
            getTestResults().add(testResults.getInt(i));
        }
        setType(Workout.Type.valueOf(json.getString("type")));
        setCurrentLevel(new GenericLevel(json.getJSONObject("level")));
        JSONArray logs = json.getJSONArray("logs");
        for (int i=0,len = logs.length(); i < len; i++) {
            getLogs().add(new Log(logs.getJSONObject(i)));
        }
    }
    
    public Log getCurrentLog() {
        if (_logs.size() == 0) {
            _logs.add(new Log(_week,_day));
        }
        return _logs.get(_logs.size()-1);
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


    /**
     * @return the type
     */
    public Workout.Type getType() {
        return _type;
    }


    /**
     * @param type_ the type to set
     */
    public void setType(Workout.Type type_) {
        _type = type_;
    }

    /**
     * @return the lastWorkout
     */
    public Date getLastWorkout() {
        return _lastWorkout;
    }

    /**
     * @param lastWorkout_ the lastWorkout to set
     */
    public void setLastWorkout(Date lastWorkout_) {
        _lastWorkout = lastWorkout_;
    }
    
}
