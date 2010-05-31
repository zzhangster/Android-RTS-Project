package com.electrofear;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Bundle;

public class ElectroFearActivity extends Activity implements SensorEventListener{
    
    protected void onCreate(Bundle savedInstanceState) {
        
    }
    
    protected void onDestroy(){
        
    }
    
    
    
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // TODO Auto-generated method stub
        
    }

}
