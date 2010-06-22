package com.kinnack.nthings.activity;

import static com.kinnack.nthings.R.xml.settings;
import android.os.Bundle;
import android.preference.PreferenceActivity;

public class SettingsActivity extends PreferenceActivity {

    
    @Override
    protected void onCreate(Bundle savedInstanceState_) {
        super.onCreate(savedInstanceState_);
        addPreferencesFromResource(settings);
        
        
    }
}
