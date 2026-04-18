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

public class DragonHouseFragment extends Fragment {

    private CrewMember selectedDragon;

    public DragonHouseFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dragon_house, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button startButton = view.findViewById(R.id.goToStartButton);
        Button trainButton = view.findViewById(R.id.trainButton);
        Button battleButton = view.findViewById(R.id.battleButton);
        Button sendToMedbayButton = view.findViewById(R.id.sendToMedbayButton);
        TextView statsText = view.findViewById(R.id.soldierStatsText);
        LinearLayout dragonContainer = view.findViewById(R.id.soldierContainer);

        startButton.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main, new HomeFragment())
                    .addToBackStack(null)
                    .commit();
        });

        GameTracker gameTracker = MainActivity.getGameTracker();

        for (CrewMember crewMember : gameTracker.getCrewList()) {
            if (crewMember.getCrewType() == CrewType.DRAGON) {
                Button dragonButton = new Button(getContext());
                dragonButton.setText(crewMember.getName());

                dragonButton.setOnClickListener(v -> {
                    selectedDragon = crewMember;
                    statsText.setText(
                            "Name: " + crewMember.getName() +
                                    "\nColor: " + crewMember.getColor() +
                                    "\nXP: " + crewMember.getXP() +
                                    "\nEnergy: " + crewMember.getEnergy() +
                                    "\nLocation: " + crewMember.getLocation()

                    );
                });

                dragonContainer.addView(dragonButton);
            }
        }

        trainButton.setOnClickListener(v -> {
            if (selectedDragon != null) {
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main, TrainingFragment.newInstance(selectedDragon.getId()))
                        .addToBackStack(null)
                        .commit();
            } else {
                statsText.setText("Please select a dragon first.");
            }
        });

        battleButton.setOnClickListener(v -> {
            if (selectedDragon == null) {
                statsText.setText("Please select a dragon first.");
                return;
            }
            if (!selectedDragon.canEnterBattle()) {
                statsText.setText(selectedDragon.getName() + " needs 50 XP to enter battle!");
                return;
            }

            // Pop up to select second player
            List<CrewMember> availableOthers = new ArrayList<>();
            for (CrewMember m : gameTracker.getCrewList()) {
                if (m != selectedDragon && m.canEnterBattle() && m.getEnergy() > 0) {
                    availableOthers.add(m);
                }
            }

            if (availableOthers.isEmpty()) {
                statsText.setText("No other crew members are ready for battle (need 50 XP and energy).");
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
                                .replace(R.id.main, MissionFragment.newInstance(selectedDragon.getId(), secondPlayer.getId()))
                                .addToBackStack(null)
                                .commit();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });

        sendToMedbayButton.setOnClickListener(v -> {
            if (selectedDragon != null) {
                selectedDragon.setLocation(Location.MEDBAY);
                statsText.setText(
                        "Name: " + selectedDragon.getName() +
                                "\nColor: " + selectedDragon.getColor() +
                                "\nXP: " + selectedDragon.getXP() +
                                "\nEnergy: " + selectedDragon.getEnergy() +
                                "\nLocation: " + selectedDragon.getLocation() +
                                "\nStatus: Sent to Medbay"
                );
            } else {
                statsText.setText("Please select a dragon first.");
            }
        });
    }
}