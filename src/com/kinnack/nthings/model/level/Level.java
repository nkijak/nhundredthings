package com.kinnack.nthings.model.level;

import org.json.JSONException;
import org.json.JSONObject;

public interface Level {
    public boolean checkLevel(int count_);
    public int getStartWeek();
    public int getIndex();
    public JSONObject toJSON() throws JSONException;
}
