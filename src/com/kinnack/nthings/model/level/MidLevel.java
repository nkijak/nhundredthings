package com.kinnack.nthings.model.level;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class MidLevel implements Level {

    @Override
    public int getIndex() {
        return 1;
    }
    
    @Override
    public String toString() {
        return "MID on or after week "+getStartWeek();
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject self = new JSONObject();
        self.put("index", getIndex())
            .put("label", "EASY")
            .put("startWeek", getStartWeek());
        return self;
    }
}
