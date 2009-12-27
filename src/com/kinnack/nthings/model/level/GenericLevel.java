package com.kinnack.nthings.model.level;

import org.json.JSONException;
import org.json.JSONObject;

public class GenericLevel implements Level {
    private int _index;
    private int _startWeek;
    private String _label;
    
    
    public GenericLevel(int index_, int startWeek_, String label_) {
        _index = index_;
        _startWeek = startWeek_;
        _label = label_;
    }
    
    public GenericLevel(JSONObject json_) throws JSONException{
        _index = json_.getInt("index");
        _label = json_.getString("label");
        _startWeek = json_.getInt("startWeek");
    }
    
    @Override
    public boolean checkLevel(int count_) {
        return false;
    }

    @Override
    public int getIndex() {
        return _index;
    }

    @Override
    public int getStartWeek() {
        return _startWeek;
    }

    @Override
    public JSONObject toJSON() throws JSONException {
        JSONObject self = new JSONObject();
        self.put("index", getIndex())
            .put("label", _label)
            .put("startWeek", getStartWeek());
        return self;
    }

}
