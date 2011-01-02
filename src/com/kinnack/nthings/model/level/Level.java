package com.kinnack.nthings.model.level;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class Level {
    public abstract boolean checkLevel(int count_);
    public abstract int getStartWeek();
    public abstract int getIndex();
    public abstract String getLabel();
    public JSONObject toJSON() throws JSONException {
        JSONObject self = new JSONObject();
        self.put("index", getIndex())
            .put("label", getLabel())
            .put("startWeek", getStartWeek());
        return self;
    }
    
    protected boolean isCountBetween(int count_, int start_, int end_) {
        return count_ >= start_ && count_ <= end_;
    }
}
