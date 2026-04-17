package com.example.spacecandygame;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class DoctorHouseFragment extends Fragment {

    private CrewMember selectedDoctor;

    public DoctorHouseFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_doctor_house, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button startButton = view.findViewById(R.id.goToStartButton);
        Button healButton = view.findViewById(R.id.healButton);
        TextView statsText = view.findViewById(R.id.soldierStatsText);
        LinearLayout doctorContainer = view.findViewById(R.id.soldierContainer);

        startButton.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main, new HomeFragment())
                    .addToBackStack(null)
                    .commit();
        });

        GameTracker gameTracker = MainActivity.getGameTracker();

        for (CrewMember crewMember : gameTracker.getCrewList()) {
            if (crewMember.getCrewType() == CrewType.DOCTOR) {

                Button doctorButton = new Button(getContext());
                doctorButton.setText(crewMember.getName());

                doctorButton.setOnClickListener(v -> {
                    selectedDoctor = crewMember;
                    statsText.setText(
                            "Name: " + crewMember.getName() +
                                    "\nColor: " + crewMember.getColor() +
                                    "\nXP: " + crewMember.getXP() +
                                    "\nEnergy: " + crewMember.getEnergy()
                    );
                });

                doctorContainer.addView(doctorButton);
            }
        }

        healButton.setOnClickListener(v -> {
            if (selectedDoctor != null) {
                selectedDoctor.healed();
                statsText.setText(
                        "Name: " + selectedDoctor.getName() +
                                "\nColor: " + selectedDoctor.getColor() +
                                "\nXP: " + selectedDoctor.getXP() +
                                "\nEnergy: " + selectedDoctor.getEnergy() +
                                "\nStatus: Healed"
                );
            } else {
                statsText.setText("Please select a doctor first.");
            }
        });
    }
}