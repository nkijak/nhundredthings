package com.kinnack.nthings.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Logg {
    /**
     * 
     */
    private final History log;
    private List<Rep> _counts = new ArrayList<Rep>();
    private int _week;
    private int _day;

    
    public Logg(History history_, int week_, int day_) {
        log = history_;
        _week = week_;
        _day = day_;
    }
    
    public Logg(History history_, JSONObject json_) throws JSONException{
        log = history_;
        _week = json_.getInt("week");
        _day = json_.getInt("day");
        JSONArray counts = json_.getJSONArray("counts");
        for(int i =0,len = counts.length(); i < len; i++){
            _counts.add(new Rep(log, counts.getJSONObject(i)));
        }
    }
    
    /**
     * @return the counts
     */
    public List<Integer> getCounts() {
        List<Integer> counts = new ArrayList<Integer>();
        for(Rep rep : _counts) { counts.add(rep._count); }
        return counts;
    }
    
    public int getTotalCount() {
        int total = 0;
        for(Rep rep : _counts) { total += rep._count;}
        return total;
    }
    
    public long getAverageMillisPerPushup() {
        long totalAverage = 0;
        for(Rep rep : _counts) { totalAverage += rep._avgTime; }
        return totalAverage/_counts.size();
    }
    
    public double getAveragePushupFrequency() {
        long totalAverage = 0;
        for(Rep rep : _counts) { totalAverage += rep._avgTime; }
        return _counts.size()*1.0/totalAverage;
    }
    
    public JSONObject toJSON() throws JSONException {
        JSONObject self = new JSONObject();
        self.put("week", _week);
        self.put("day", _day);
        self.put("counts", new JSONArray());
        for (Rep rep : _counts) {
            self.accumulate("counts", rep.toJSON());
        }
        
        return self;
        
    }
   
    public Rep addCountAndTime(int count_, long time_) {
        Rep rep = new Rep(log, count_,time_);
        _counts.add(rep);
        return rep;
    }

    public boolean isFor(int week_, int day_) {
        return _week == week_ && _day == day_;
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
     * @param counts_ the counts to set
     */
    public void setCounts(List<Rep> counts_) {
        _counts = counts_;
    }
    
    
    
    


}