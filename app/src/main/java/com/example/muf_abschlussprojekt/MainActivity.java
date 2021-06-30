package com.example.muf_abschlussprojekt;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class MainActivity extends AppCompatActivity {
    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_sensoroutput);
        mainViewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(MainViewModel.class);


        final TextView name = findViewById(R.id.name);
        final TextView xyz = findViewById(R.id.xyz);
        mainViewModel.accelerationLiveData.observe(this, (accelerationData) -> {
            name.setText("Name" + accelerationData.getSensor().getName()); // Sensorvideo Minute 42; Output ggf anpassen!
            xyz.setText("x Wert: " + accelerationData.getX() + "y Wert: " + accelerationData.getY() + "z Wert: " + accelerationData.getZ());

        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.fragment_sensormonitoring);
        mainViewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(MainViewModel.class);

        final TextView vendor = findViewById(R.id.vendor);
        final TextView version = findViewById(R.id.version);
        final TextView resolution = findViewById(R.id.resolution);
        final TextView maxrange = findViewById(R.id.maxrange);
        final TextView power = findViewById(R.id.power);
        mainViewModel.accelerationLiveData.observe(this, (accelerationData) -> {
            vendor.setText("Vendor: " + accelerationData.getSensor().getVendor());
            version.setText("Version: " + accelerationData.getSensor().getVersion());
            resolution.setText("Resolution: " + accelerationData.getSensor().getResolution());
            maxrange.setText("Maximum Range: " + accelerationData.getSensor().getMaximumRange());
            power.setText("Power: " + accelerationData.getSensor().getPower());


        });

    }
}
