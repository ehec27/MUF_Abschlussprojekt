package com.example.muf_abschlussprojekt;

import android.app.Application;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class MainViewModel extends AndroidViewModel {
    final LiveData<AccelerationData> accelerationLiveData;

    public MainViewModel(@NonNull Application application) {
        super(application);
        accelerationLiveData = new AccelerationLiveData(application.getApplicationContext());
    }

    private final static class AccelerationLiveData extends LiveData<AccelerationData> {
        private final AccelerationData accelerationData = new AccelerationData();
        private SensorManager sensorManager;
        private Sensor accelerometer;
        private Sensor gravitySensor;
        private float[] gravity;
        private SensorEventListener listener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                switch (event.sensor.getType()) {
                    case Sensor.TYPE_ACCELEROMETER:
                        float[] values = removeGravity(gravity, event.values);
                        accelerationData.setxyz(values[0], values[1], values[2]);
                        accelerationData.setSensor(event.sensor);
                        setValue(accelerationData);
                        break;
                    case Sensor.TYPE_GRAVITY:
                        gravity = event.values;
                        break;
                    default:
                        break;
                }

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

        AccelerationLiveData(Context context) {
            sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
            if (sensorManager != null) {
                gravitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
                accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            } else {
                throw new RuntimeException("Nope! Try again...");
            }
        }

        @Override
        protected void onActive() {
            super.onActive();
            sensorManager.registerListener(listener, gravitySensor, SensorManager.SENSOR_DELAY_UI);
            sensorManager.registerListener(listener, accelerometer, SensorManager.SENSOR_DELAY_UI);

        }

        @Override
        protected void onInactive() {
            super.onInactive();
            sensorManager.unregisterListener(listener);
        }

        private float[] removeGravity(float[] gravity, float[] values) {
            if (gravity == null) {
                return values;
            }
            final float alpha = 0.8f;
            float g[] = new float[3];

            // Isolate the force of gravity with the low-pass filter.
            g[0] = alpha * gravity[0] + (1 - alpha) * values[0];
            g[1] = alpha * gravity[1] + (1 - alpha) * values[1];
            g[2] = alpha * gravity[2] + (1 - alpha) * values[2];

            return new float[]{
                    values[0] - g[0],
                    values[1] - g[1],
                    values[1] - g[2]
            };

        }
    }
}
