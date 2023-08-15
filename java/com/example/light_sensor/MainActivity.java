package com.example.light_sensor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {


    private SensorManager sensorManager;
    private Sensor lightSensor;
    private TextView brightnessLevel;
    private TextView brightnessDescription;
    private View background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        brightnessLevel = findViewById(R.id.brightness_level);
        brightnessDescription = findViewById(R.id.brightness_description);
        background = findViewById(android.R.id.content);
    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        float lux = event.values[0];
        int brightness = (int) (lux / 255 * 100);
        brightnessLevel.setText("Brightness level: " + brightness + "%");

        if (lux < 500) {
            brightnessDescription.setText("Low Brightness");
            brightnessDescription.setTextColor(Color.WHITE);
            background.setBackgroundColor(Color.BLACK);
        } else if (lux < 1000) {
            brightnessDescription.setText("Moderate Brightness");
            brightnessDescription.setTextColor(Color.WHITE);
            background.setBackgroundColor(Color.GRAY);
        } else {
            brightnessDescription.setText("High Brightness");
            brightnessDescription.setTextColor(Color.BLACK);
            background.setBackgroundColor(Color.WHITE);
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}