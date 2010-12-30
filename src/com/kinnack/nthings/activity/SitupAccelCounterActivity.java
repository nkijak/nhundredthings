package com.kinnack.nthings.activity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

public class SitupAccelCounterActivity extends CounterActivity  implements SensorEventListener {
    private boolean layDown, satUp;
    
    @Override
    protected void onResume() {
        super.onResume();
        SensorManager sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_UI);
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        ((SensorManager)getSystemService(SENSOR_SERVICE)).unregisterListener(this);
    }
    
    @Override
    public void onAccuracyChanged(Sensor sensor_, int accuracy_) {
        // Do nothing?
    }

    @Override
    public void onSensorChanged(SensorEvent event_) {
        if (event_.sensor.getType() != Sensor.TYPE_ACCELEROMETER) return;
        float y = Math.abs(event_.values[1]);
        float z = event_.values[2];
        
        
        
        if (y > 8 && z < 2) satUp = true;
        if (z > 8 && y < 2) {layDown = true; satUp = false; } //reset
        
        if (layDown && satUp) {
            count();
            layDown = false;
            soundAlert.progressBeep();
        }
        
        
    }

}
