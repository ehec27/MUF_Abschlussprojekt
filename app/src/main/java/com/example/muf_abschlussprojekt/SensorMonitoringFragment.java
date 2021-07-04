package com.example.muf_abschlussprojekt;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import java.util.List;

public class SensorMonitoringFragment extends Fragment {
    private MainViewModel mainViewModel;
    private LiveData<AccelerationData> accelerationDataLiveData;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainViewModel = new ViewModelProvider(getActivity(),
                ViewModelProvider.AndroidViewModelFactory
                        .getInstance(getActivity().getApplication())).get(MainViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sensormonitoring, container, false);

        final TextView vendor = v.findViewById(R.id.vendor);
        final TextView version = v.findViewById(R.id.version);
        final TextView resolution = v.findViewById(R.id.resolution);
        final TextView maxrange = v.findViewById(R.id.maxrange);
        final TextView power = v.findViewById(R.id.power);
        final TextView monitoring_xyz = v.findViewById(R.id.monitoring_xyz);
        mainViewModel.accelerationLiveData.observe(getViewLifecycleOwner(), accelerationData -> {
            vendor.setText("Vendor: " + accelerationData.getSensor().getVendor()); // vllt noch anders ausgeben
            version.setText("Version: " + accelerationData.getSensor().getVersion());
            resolution.setText("Resolution: " + accelerationData.getSensor().getResolution());
            maxrange.setText("Maximum Range: " + accelerationData.getSensor().getMaximumRange());
            power.setText("Power: " + accelerationData.getSensor().getPower());
        });

       mainViewModel.getAccelerationData().observe(getViewLifecycleOwner(), accelerationData ->  {
           monitoring_xyz.setText("x: " + accelerationData.get(0).getX() +
                   " m/s² y: " + accelerationData.get(0).getY() +
                   " m/s² z: " + accelerationData.get(0).getZ() +" m/s²");
       });
         // vllt noch ande ;*/


        return v;

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        final NavController controller = Navigation.findNavController(view);
        view.findViewById(R.id.button_monitoring).setOnClickListener(buttonOutput -> {
            controller.navigate(SensorMonitoringFragmentDirections.actionSensorMonitoringFragmentToSensorOutputFragment());
        });
    }
}
