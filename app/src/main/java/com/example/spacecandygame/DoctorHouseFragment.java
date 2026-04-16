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
        Button trainButton = view.findViewById(R.id.trainButton);
        Button battleButton = view.findViewById(R.id.battleButton);
        TextView statsText = view.findViewById(R.id.soldierStatsText);
        LinearLayout doctorContainer = view.findViewById(R.id.soldierContainer);

        startButton.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
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

        trainButton.setOnClickListener(v -> {
            if (selectedDoctor != null) {
                selectedDoctor.addXP(3);
                statsText.setText(
                        "Name: " + selectedDoctor.getName() +
                                "\nColor: " + selectedDoctor.getColor() +
                                "\nXP: " + selectedDoctor.getXP() +
                                "\nEnergy: " + selectedDoctor.getEnergy() +
                                "\nStatus: Trained"
                );
            } else {
                statsText.setText("Please select a doctor first.");
            }
        });

        battleButton.setOnClickListener(v -> {
            if (selectedDoctor != null) {
                selectedDoctor.takeBattleDamage();
                statsText.setText(
                        "Name: " + selectedDoctor.getName() +
                                "\nColor: " + selectedDoctor.getColor() +
                                "\nXP: " + selectedDoctor.getXP() +
                                "\nEnergy: " + selectedDoctor.getEnergy() +
                                "\nStatus: Went to battle"
                );
            } else {
                statsText.setText("Please select a doctor first.");
            }
        });
    }
}