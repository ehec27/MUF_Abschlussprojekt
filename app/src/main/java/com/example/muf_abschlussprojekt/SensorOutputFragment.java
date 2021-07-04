package com.example.muf_abschlussprojekt;

import android.os.Bundle;
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

public class SensorOutputFragment extends Fragment {
    private MainViewModel mainViewModel;
    private LiveData<AccelerationData> accelerationDataLiveData;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainViewModel = new ViewModelProvider(getActivity(), // unsicher ob wir das an dieser Stelle brauchen
                ViewModelProvider.AndroidViewModelFactory
                        .getInstance(getActivity().getApplication())).get(MainViewModel.class);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sensoroutput, container, false);
        final TextView name = v.findViewById(R.id.name);
        final TextView x = v.findViewById(R.id.x);
        final TextView y = v.findViewById(R.id.y);
        final TextView z = v.findViewById(R.id.z);
        mainViewModel.accelerationLiveData.observe(getViewLifecycleOwner(), accelerationData -> {
            name.setText("Name" + accelerationData.getSensor().getName()); // Sensorvideo Minute 42; Output ggf anpassen!
            x.setText("x Wert: " + accelerationData.getX()+ " m/s²");
            y.setText("y Wert: " + accelerationData.getY() + " m/s²");
            z.setText("z Wert: " + accelerationData.getZ() + " m/s²");
            mainViewModel.insertAccelerationData(accelerationData);
//hier runnable rein über viewmodel
        });
        return v;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        final NavController controller = Navigation.findNavController(view);
        view.findViewById(R.id.button_output).setOnClickListener(buttonOutput -> {
            controller.navigate(SensorOutputFragmentDirections.actionSensorOutputFragmentToSensorMonitoringFragment());
// übergabe von Sensordaten?
        });
    }
}
