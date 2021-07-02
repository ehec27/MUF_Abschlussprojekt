package com.example.muf_abschlussprojekt;

import android.app.Application;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class MainViewModel extends BaseViewModel {
    //final LiveData<AccelerationData> accelerationLiveData; //auskommentieren vllt zum updaten
    private Handler handler = new Handler(Looper.getMainLooper());
    final AccelerationLiveData accelerationLiveData;


    public MainViewModel(@NonNull Application application) {
        super(application);
        accelerationLiveData = new AccelerationLiveData(application.getApplicationContext());
    }

    public void insertAccelerationData(AccelerationData accelerationData) { //muss hier auch eine List ausgegeben werden?
        Runnable r = () -> {
            getDatabase().getSensorDao().insert(accelerationData);
        };
        Thread t = new Thread(r);
        t.start();
    }

    public LiveData<List<AccelerationData>> getAccelerationData() {
        return getDatabase().getSensorDao().getSensorData();//es muss eine List zurückgegeben werden

    }

    public LiveData<AccelerationData> AccelerationDataInserted() {
        return accelerationLiveData;
    }


    public class AccelerationLiveData extends LiveData<AccelerationData> {
        private AtomicBoolean active = new AtomicBoolean();
        private final AccelerationData accelerationData = new AccelerationData();
        private SensorManager sensorManager;
        private Sensor accelerometer; // Beschleunigungssensor benannt
        private Sensor gravitySensor; // Schwerkraftsensor benannt
        private float[] gravity;

 /*       public void insertSensor(AccelerationData accelerationData) {
            Runnable r = () -> {
                getDatabase().getSensorDao().insert(accelerationData);
                if (active.get()) {
                    handler.post(() -> {
                        setValue(accelerationData);
                    });
                }
            };
            Thread t = new Thread(r);
            t.start();
        }*/

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

        // Muss vermutlich noch in der Klasse integriert werden, ansnsten doppelt...
        AccelerationLiveData(Context context) {
            sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
            if (sensorManager != null) {
                gravitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY); //Zugriff auf Schwerkraftsensor
                accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER); //Zugriff auf Beschleunigungssensor
            } else {
                throw new RuntimeException("Nope! Try again...");
            }
        }


        @Override
        protected void onActive() {
            super.onActive();
            sensorManager.registerListener(listener, gravitySensor, SensorManager.SENSOR_DELAY_NORMAL); // Ausgabe Geschwindigkeit
            sensorManager.registerListener(listener, accelerometer, SensorManager.SENSOR_DELAY_NORMAL); // Manuell anpassen?
            active.set(true);

        }

        @Override
        protected void onInactive() {
            super.onInactive();
            sensorManager.unregisterListener(listener);
            active.set(false);
        }

        // Schwerkraft abziehen
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
