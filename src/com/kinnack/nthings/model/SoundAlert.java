package com.kinnack.nthings.model;

import java.io.StringReader;

import com.kinnack.nthings.R;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.ToneGenerator;

// XXX This is a total hack in need of the state(ful?) pattern
public class SoundAlert {
    boolean enabled = false;
    private ToneGenerator _toneGenerator;
    public SoundAlert(SharedPreferences preferences_, Context context_) {
        String soundSettingKey = context_.getResources().getString(R.string.sound_setting_key);
        enabled = preferences_.getBoolean(soundSettingKey, true);
        if (enabled) _toneGenerator = new ToneGenerator(AudioManager.STREAM_MUSIC, ToneGenerator.MAX_VOLUME);
    }
    
    public void progressBeep() {
        if (enabled) _toneGenerator.startTone(ToneGenerator.TONE_PROP_BEEP);
    }
    
    public void finishedBeep() {
        if (enabled) _toneGenerator.startTone(ToneGenerator.TONE_PROP_ACK);
    }
    
    public void vibrate() {
        
    }
    
    public void cleanup() {
        if (enabled) _toneGenerator.release();
    }
}
