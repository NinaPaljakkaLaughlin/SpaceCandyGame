package com.example.spacecandygame;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

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
                                    "\nSkillPower: " + crewMember.getSkillPower() +
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
                Scientist scientist = (Scientist) selectedScientist;
                if (scientist.canStartTraining()) {
                    requireActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main, TrainingFragment.newInstance(selectedScientist.getId()))
                            .addToBackStack(null)
                            .commit();
                } else {
                    statsText.setText("Scientist needs 10 chemicals before training.");
                }
            } else {
                statsText.setText("Please select a scientist first.");
            }
        });

        // BATTLE
        battleButton.setOnClickListener(v -> {
            if (selectedScientist != null) {
                if (selectedScientist.canEnterBattle()) {
                    selectedScientist.setLocation(Location.BATTLE);

                    requireActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main, new BattleFragment())
                            .addToBackStack(null)
                            .commit();
                } else {
                    statsText.setText("This scientist needs more XP before entering battle.");
                }
            } else {
                statsText.setText("Please select a scientist first.");
            }
        });

        // COLLECT FLOWERS
        collectFlowerButton.setOnClickListener(v -> {
            if (selectedScientist != null) {
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main, FlowerFieldFragment.newInstance(selectedScientist.getId(), "SCIENTIST"))
                        .addToBackStack(null)
                        .commit();
            } else {
                statsText.setText("Please select a scientist first.");
            }
        });

        // MAKE POTION
        makePotionButton.setOnClickListener(v -> {
            if (selectedScientist != null) {
                Scientist scientist = (Scientist) selectedScientist;
                scientist.makeChemical();

                statsText.setText(
                        "Name: " + scientist.getName() +
                                "\nFlowers: " + scientist.getFlowers() +
                                "\nChemicals: " + scientist.getChemicals()
                );
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