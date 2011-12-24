package com.kinnack.nthings.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Rep {
    /**
     * 
     */
    private final History rep;
    public int _count;
    public long _avgTime;
    
    public Rep(History history_, int count_,long avgTime_) {
        rep = history_;
        _count = count_;
        _avgTime = avgTime_;
    }
    
    public JSONObject toJSON() throws JSONException {
        JSONObject self = new JSONObject();
        self.put("count", _count)
            .put("avgTime", _avgTime);
        return self;
    }
    
    public Rep(History history_, JSONObject json_) throws JSONException {
        rep = history_;
        _count = json_.getInt("count");
        _avgTime = json_.getLong("avgTime");
    }
}