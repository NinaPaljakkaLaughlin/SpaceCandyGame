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

public class ScientistHouseFragment extends Fragment {

    private CrewMember selectedScientist;

    public ScientistHouseFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_scientist_house, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button startButton = view.findViewById(R.id.goToStartButton);
        Button trainButton = view.findViewById(R.id.trainButton);
        Button battleButton = view.findViewById(R.id.battleButton);
        Button collectFlowerButton = view.findViewById(R.id.collectFlowerButton);
        Button makePotionButton = view.findViewById(R.id.makePotionButton);
        Button sendToMedbayButton = view.findViewById(R.id.sendToMedbayButton);

        TextView statsText = view.findViewById(R.id.soldierStatsText);
        LinearLayout scientistContainer = view.findViewById(R.id.soldierContainer);

        startButton.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main, new HomeFragment())
                    .addToBackStack(null)
                    .commit();
        });

        GameTracker gameTracker = MainActivity.getGameTracker();

        for (CrewMember crewMember : gameTracker.getCrewList()) {
            if (crewMember.getCrewType() == CrewType.SCIENTIST) {

                Button scientistButton = new Button(getContext());
                scientistButton.setText(crewMember.getName());

                scientistButton.setOnClickListener(v -> {
                    selectedScientist = crewMember;
                    statsText.setText(
                            "Name: " + crewMember.getName() +
                                    "\nColor: " + crewMember.getColor() +
                                    "\nXP: " + crewMember.getXP() +
                                    "\nEnergy: " + crewMember.getEnergy() +
                                    "\nLocation: " + crewMember.getLocation()

                    );
                });

                scientistContainer.addView(scientistButton);
            }
        }

        // TRAIN
        trainButton.setOnClickListener(v -> {
            if (selectedScientist != null) {
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main, TrainingFragment.newInstance(selectedScientist.getId()))
                        .addToBackStack(null)
                        .commit();
            } else {
                statsText.setText("Please select a scientist first.");
            }
        });

        // BATTLE
        battleButton.setOnClickListener(v -> {
            if (selectedScientist == null) {
                statsText.setText("Please select a scientist first.");
                return;
            }
            if (!selectedScientist.canEnterBattle()) {
                statsText.setText(selectedScientist.getName() + " needs 50 XP to enter battle!");
                return;
            }

            // Pop up to select second player
            List<CrewMember> availableOthers = new ArrayList<>();
            for (CrewMember m : gameTracker.getCrewList()) {
                if (m != selectedScientist && m.canEnterBattle() && m.getEnergy() > 0) {
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
                                .replace(R.id.main, MissionFragment.newInstance(selectedScientist.getId(), secondPlayer.getId()))
                                .addToBackStack(null)
                                .commit();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });

        // COLLECT FLOWERS
        collectFlowerButton.setOnClickListener(v -> {
            if (selectedScientist != null) {
                ((Scientist) selectedScientist).pickFlowers();
                statsText.setText("Collected flowers!");
            } else {
                statsText.setText("Please select a scientist first.");
            }
        });

        // MAKE POTION
        makePotionButton.setOnClickListener(v -> {
            if (selectedScientist != null) {
                ((Scientist) selectedScientist).makeChemical();
                statsText.setText("Potion created!");
            } else {
                statsText.setText("Please select a scientist first.");
            }
        });

        // MEDBAY
        sendToMedbayButton.setOnClickListener(v -> {
            if (selectedScientist != null) {
                selectedScientist.setLocation(Location.MEDBAY);
                statsText.setText("Sent to Medbay");
            } else {
                statsText.setText("Please select a scientist first.");
            }
        });
    }
}