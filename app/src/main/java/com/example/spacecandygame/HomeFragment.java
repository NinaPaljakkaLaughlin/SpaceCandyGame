package com.example.spacecandygame;
//AI use declaration: AI was used to generate the background image

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button startButton = view.findViewById(R.id.goToStartButton);
        Button soldierHouseButton = view.findViewById(R.id.soldierHouseButton);
        Button scientistHouseButton = view.findViewById(R.id.scientistHouseButton);
        Button engineerHouseButton = view.findViewById(R.id.engineerHouseButton);
        Button dragonHouseButton = view.findViewById(R.id.dragonHouseButton);
        Button doctorHouseButton = view.findViewById(R.id.doctorHouseButton);

        startButton.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        soldierHouseButton.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main, new SoldierHouseFragment())
                    .addToBackStack(null)
                    .commit();
        });

        scientistHouseButton.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main, new ScientistHouseFragment())
                    .addToBackStack(null)
                    .commit();
        });

        engineerHouseButton.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main, new EngineerHouseFragment())
                    .addToBackStack(null)
                    .commit();
        });

        dragonHouseButton.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main, new DragonHouseFragment())
                    .addToBackStack(null)
                    .commit();
        });

        doctorHouseButton.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main, new DoctorHouseFragment())
                    .addToBackStack(null)
                    .commit();
        });
    }
}
