package com.example.muf_abschlussprojekt;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.ArrayList;

@Dao
public abstract class SensorDao {

    @Query("SELECT * FROM sensor")
    public abstract LiveData<ArrayList<AccelerationData>> getSensorData (float xyz); //wharscheinlich, unsicher

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract long insert (AccelerationData accelerationData);

}
