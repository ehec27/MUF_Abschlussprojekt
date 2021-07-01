package com.example.muf_abschlussprojekt;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class SensorMonitoringFragment extends Fragment {
    private MainViewModel mainViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainViewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(MainViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_sensormonitoring, container, false);

        final TextView vendor = v.findViewById(R.id.vendor);
        final TextView version = v.findViewById(R.id.version);
        final TextView resolution = v.findViewById(R.id.resolution);
        final TextView maxrange = v.findViewById(R.id.maxrange);
        final TextView power = v.findViewById(R.id.power);
        mainViewModel.accelerationLiveData.observe(getViewLifecycleOwner(), (accelerationData) -> {
            vendor.setText("Vendor: " + accelerationData.getSensor().getVendor()); // vllt noch anders ausgeben
            version.setText("Version: " + accelerationData.getSensor().getVersion());
            resolution.setText("Resolution: " + accelerationData.getSensor().getResolution());
            maxrange.setText("Maximum Range: " + accelerationData.getSensor().getMaximumRange());
            power.setText("Power: " + accelerationData.getSensor().getPower());


        });
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
