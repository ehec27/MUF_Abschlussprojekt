package com.example.muf_abschlussprojekt;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {AccelerationData.class}, version = 1)
public abstract class SensorDatabase extends RoomDatabase {
    public abstract SensorDao getSensorDao();

}
