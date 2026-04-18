package com.example.spacecandygame;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class EngineerHouseFragment extends Fragment {

    private CrewMember selectedEngineer;

    public EngineerHouseFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_engineer_house, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button startButton = view.findViewById(R.id.goToStartButton);
        Button trainButton = view.findViewById(R.id.trainButton);
        Button battleButton = view.findViewById(R.id.battleButton);
        Button plantFlowerButton = view.findViewById(R.id.plantFlowerButton);
        Button sendToMedbayButton = view.findViewById(R.id.sendToMedbayButton);

        TextView statsText = view.findViewById(R.id.soldierStatsText);
        LinearLayout engineerContainer = view.findViewById(R.id.soldierContainer);

        startButton.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main, new HomeFragment())
                    .addToBackStack(null)
                    .commit();
        });

        GameTracker gameTracker = MainActivity.getGameTracker();

        for (CrewMember crewMember : gameTracker.getCrewList()) {
            if (crewMember.getCrewType() == CrewType.ENGINEER) {

                Button engineerButton = new Button(getContext());
                engineerButton.setText(crewMember.getName());

                engineerButton.setOnClickListener(v -> {
                    selectedEngineer = crewMember;
                    statsText.setText(
                            "Name: " + crewMember.getName() +
                                    "\nColor: " + crewMember.getColor() +
                                    "\nXP: " + crewMember.getXP() +
                                    "\nEnergy: " + crewMember.getEnergy() +
                                    "\nLocation: " + crewMember.getLocation()
                    );
                });

                engineerContainer.addView(engineerButton);
            }
        }

        // TRAIN
        trainButton.setOnClickListener(v -> {
            if (selectedEngineer != null) {
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main, TrainingFragment.newInstance(selectedEngineer.getId()))
                        .addToBackStack(null)
                        .commit();
            } else {
                statsText.setText("Please select an engineer first.");
            }
        });

        // BATTLE
        battleButton.setOnClickListener(v -> {
            if (selectedEngineer == null) {
                statsText.setText("Please select an engineer first.");
                return;
            }
            if (!selectedEngineer.canEnterBattle()) {
                statsText.setText(selectedEngineer.getName() + " needs 50 XP to enter battle!");
                return;
            }

            List<CrewMember> availableOthers = new ArrayList<>();
            for (CrewMember m : gameTracker.getCrewList()) {
                if (m != selectedEngineer && m.canEnterBattle() && m.getEnergy() > 0) {
                    availableOthers.add(m);
                }
            }

            if (availableOthers.isEmpty()) {
                statsText.setText("No other crew members are ready for battle.");
                return;
            }

            String[] otherNames = new String[availableOthers.size()];
            for (int i = 0; i < availableOthers.size(); i++) {
                otherNames[i] = availableOthers.get(i).getName() + " (" + availableOthers.get(i).getCrewType() + ")";
            }

            new AlertDialog.Builder(getContext())
                    .setTitle("Select Second Crew Member")
                    .setItems(otherNames, (dialog, which) -> {
                        CrewMember secondPlayer = availableOthers.get(which);
                        requireActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.main, MissionFragment.newInstance(selectedEngineer.getId(), secondPlayer.getId()))
                                .addToBackStack(null)
                                .commit();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });

        // PLANT FLOWER
        plantFlowerButton.setOnClickListener(v -> {
            if (selectedEngineer != null) {
                ((Engineer) selectedEngineer).plantFlower();
                statsText.setText(
                        "Name: " + selectedEngineer.getName() +
                                "\nXP: " + selectedEngineer.getXP() +
                                "\nStatus: Planted Flower"
                );
            } else {
                statsText.setText("Please select an engineer first.");
            }
        });

        // MEDBAY
        sendToMedbayButton.setOnClickListener(v -> {
            if (selectedEngineer != null) {
                selectedEngineer.setLocation(Location.MEDBAY);
                statsText.setText(
                        "Name: " + selectedEngineer.getName() +
                                "\nLocation: " + selectedEngineer.getLocation() +
                                "\nStatus: Sent to Medbay"
                );
            } else {
                statsText.setText("Please select an engineer first.");
            }
        });
    }
}