package com.kinnack.nthings.model.level;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class EasyLevel implements Level {
    @Override
    public int getIndex() {
        return 0;
    }

    @Override
    public String toString() {
        return "EASY on or after week "+getStartWeek();
    }
    
    public JSONObject toJSON() throws JSONException {
        JSONObject self = new JSONObject();
        self.put("index", getIndex())
            .put("label", "EASY")
            .put("startWeek", getStartWeek());
        return self;
    }
}
