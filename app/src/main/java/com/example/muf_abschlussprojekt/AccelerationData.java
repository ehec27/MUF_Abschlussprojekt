package com.example.muf_abschlussprojekt;

import android.hardware.Sensor;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "sensor") // Tabellenname: sensor
public class AccelerationData {
    @PrimaryKey
    private Long id;
    @Ignore
    private Sensor sensor;
    private float x;
    private float y;
    private float z;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public void setxyz(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;

    }
}
