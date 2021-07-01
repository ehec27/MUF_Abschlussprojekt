package com.example.muf_abschlussprojekt;

import android.app.Application;

import androidx.room.Room;

public class SensorApplication extends Application {
    private SensorDatabase sensorDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        sensorDatabase = Room
                .databaseBuilder(this, SensorDatabase.class, "acceleration").build();
    }

    public SensorDatabase getSensorDatabase(){
        return sensorDatabase;
    }


}

