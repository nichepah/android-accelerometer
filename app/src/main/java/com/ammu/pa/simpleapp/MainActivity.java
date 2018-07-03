package com.ammu.pa.simpleapp;
/* Get reading from accelerometer using SensorEventListener and SensorManager
 * Written by Aneesh PA on 01 July 2018
 */
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
// general sensor manager and sensor
private SensorManager sensorManager = null;
private Sensor accelerometer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // trying the general version
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null){
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }
        else{
            Toast.makeText(this, "No accelerometer", Toast.LENGTH_SHORT).show();
            return;
        }

    }

    //register listener for accelerometer using sensor manager
    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);

    }

    // onSensorChanged and onAccuracyChanged are to be implemented
    @Override
    public void onSensorChanged(SensorEvent event) {
        float xAcceleration = event.values[0];
        float yAcceleration = event.values[1];
        float zAcceleration = event.values[2];

        System.out.println(xAcceleration+":"+yAcceleration+":"+zAcceleration);
        //Toast.makeText(this, String.valueOf(xAcceleration)+" yAccel: "+ String.valueOf(yAcceleration)+" zAccel: "+String.valueOf(zAcceleration) , Toast.LENGTH_LONG);

        TextView xVal = findViewById(R.id.xVal);
        TextView yVal = findViewById(R.id.yVal);
        TextView zVal = findViewById(R.id.zVal);
        xVal.setText(String.valueOf(xAcceleration));
        yVal.setText(String.valueOf(yAcceleration));
        zVal.setText(String.valueOf(zAcceleration));
    }

    // unregister so that battery life is optimized
    @Override
    protected void onPause(){
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        if(sensor == accelerometer){
            switch (accuracy){
                case 0:
                    System.out.println("Unreliable accelerometer");
                    Toast.makeText(this, "Unreliable accelerometer", Toast.LENGTH_SHORT);
                    break;
                case 1:
                    Toast.makeText(this, "Low accuracy accelerometer", Toast.LENGTH_SHORT);
                    System.out.println("Low accuracy accelerometer");
                    break;
                case 2:
                    Toast.makeText(this, "Medium accuracy accelerometer", Toast.LENGTH_SHORT);
                    System.out.println("Medium accuracy accelerometer");
                    break;
                case 3:
                    Toast.makeText(this, "High accuracy accelerometer", Toast.LENGTH_SHORT);
                    System.out.println("High accuracy accelerometer");
                    break;

            }
        }


    }
}
