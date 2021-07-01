package com.example.muf_abschlussprojekt;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public abstract class BaseViewModel extends AndroidViewModel {

    public BaseViewModel(@NonNull Application application) {
        super(application);
    }

    public SensorDatabase getDatabase(){
        return ((SensorApplication)getApplication()).getSensorDatabase();
    }
}
