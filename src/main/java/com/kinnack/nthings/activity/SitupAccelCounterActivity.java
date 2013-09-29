package com.kinnack.nthings.activity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import com.kinnack.nthings.R;

public class SitupAccelCounterActivity extends CounterActivity  implements SensorEventListener {
    private boolean layDown, satUp;
    private TextView directions;
    
    @Override
    public void onCreate(Bundle savedInstanceState_) {
        super.onCreate(savedInstanceState_);
        directions = (TextView)findViewById(R.id.Directions);
    }
    
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
    protected int getLayout() {
        return R.layout.accelerometer_count;
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
        
        
        
        if (z < 5.5) satUp = true;
        if (z > 9) {layDown = true; satUp = false; directions.setText("UP");} //reset
        
        if (layDown && satUp) {
            count();
            layDown = false;
            directions.setText("DOWN");
            soundAlert.progressBeep();
        }
        
        
    }

}
