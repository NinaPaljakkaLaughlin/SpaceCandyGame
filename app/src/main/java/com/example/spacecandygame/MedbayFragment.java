package com.example.spacecandygame;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class MedbayFragment extends Fragment {

    private CrewMember selectedCrewMember;
    private Doctor doctor;

    public MedbayFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_medbay, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button startButton = view.findViewById(R.id.goToStartButton);
        Button healButton = view.findViewById(R.id.healButton);
        Button trainDoctorButton = view.findViewById(R.id.trainDoctorButton);
        TextView statsText = view.findViewById(R.id.medbayStatsText);
        LinearLayout medbayContainer = view.findViewById(R.id.medbayContainer);

        // Go back to Home screen
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
                doctor = (Doctor) crewMember;
                doctor.setLocation(Location.MEDBAY);
            }

            if (crewMember.getLocation() == Location.MEDBAY) {
                Button crewButton = new Button(getContext());
                crewButton.setText(crewMember.getName());

                crewButton.setOnClickListener(v -> {
                    selectedCrewMember = crewMember;
                    statsText.setText(
                            "Name: " + crewMember.getName() +
                                    "\nType: " + crewMember.getCrewType() +
                                    "\nColor: " + crewMember.getColor() +
                                    "\nXP: " + crewMember.getXP() +
                                    "\nEnergy: " + crewMember.getEnergy() +
                                    "\nLocation: " + crewMember.getLocation()
                    );
                });

                medbayContainer.addView(crewButton);
            }
        }

        trainDoctorButton.setOnClickListener(v -> {
            if (doctor != null) {
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main, TrainingFragment.newInstance(doctor.getId()))
                        .addToBackStack(null)
                        .commit();
            } else {
                statsText.setText("Doctor not found.");
            }
        });

        healButton.setOnClickListener(v -> {
            if (doctor == null) {
                statsText.setText("Doctor not found.");
                return;
            }

            if (selectedCrewMember == null) {
                statsText.setText("Please select a crew member first.");
                return;
            }

            if (selectedCrewMember.getCrewType() == CrewType.DOCTOR) {
                statsText.setText("Doctor cannot heal themselves.");
                return;
            }

            if (!selectedCrewMember.hasNoEnergy()) {
                statsText.setText("This crew member does not need healing yet.");
                return;
            }

            if (!doctor.canHeal()) {
                statsText.setText("Doctor does not have enough XP heals available.");
                return;
            }

            boolean healed = doctor.healCrewMember(selectedCrewMember);

            if (healed) {
                selectedCrewMember.setLocation(Location.QUARTERS);
                doctor.setLocation(Location.MEDBAY);

                statsText.setText(
                        "Name: " + selectedCrewMember.getName() +
                                "\nType: " + selectedCrewMember.getCrewType() +
                                "\nColor: " + selectedCrewMember.getColor() +
                                "\nXP: " + selectedCrewMember.getXP() +
                                "\nEnergy: " + selectedCrewMember.getEnergy() +
                                "\nLocation: " + selectedCrewMember.getLocation() +
                                "\nStatus: Healed by doctor" +
                                "\nDoctor heals left: " + doctor.getRemainingHeals()
                );
            } else {
                statsText.setText("Healing failed.");
            }
        });
    }
}