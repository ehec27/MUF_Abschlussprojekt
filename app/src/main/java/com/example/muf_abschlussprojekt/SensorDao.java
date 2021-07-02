package com.example.muf_abschlussprojekt;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
public abstract class SensorDao {

    @Query("SELECT * FROM sensor LIMIT 1") // Abfrage von Tabelle
    public abstract LiveData<List<AccelerationData>> getSensorData (); //warscheinlich, unsicher -> input argument entfernt

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract long insert (AccelerationData accelerationData);

}
