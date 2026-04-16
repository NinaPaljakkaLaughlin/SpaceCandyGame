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

    public ScientistHouseFragment() {

    }

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
        TextView statsText = view.findViewById(R.id.soldierStatsText);
        LinearLayout scientistContainer = view.findViewById(R.id.soldierContainer);

        startButton.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
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
                                    "\nEnergy: " + crewMember.getEnergy()
                    );
                });

                scientistContainer.addView(scientistButton);
            }
        }

        trainButton.setOnClickListener(v -> {
            if (selectedScientist != null) {
                selectedScientist.addXP(3);
                statsText.setText(
                        "Name: " + selectedScientist.getName() +
                                "\nColor: " + selectedScientist.getColor() +
                                "\nXP: " + selectedScientist.getXP() +
                                "\nEnergy: " + selectedScientist.getEnergy() +
                                "\nStatus: Trained"
                );
            } else {
                statsText.setText("Please select a scientist first.");
            }
        });

        battleButton.setOnClickListener(v -> {
            if (selectedScientist != null) {
                selectedScientist.takeBattleDamage();
                statsText.setText(
                        "Name: " + selectedScientist.getName() +
                                "\nColor: " + selectedScientist.getColor() +
                                "\nXP: " + selectedScientist.getXP() +
                                "\nEnergy: " + selectedScientist.getEnergy() +
                                "\nStatus: Went to battle"
                );
            } else {
                statsText.setText("Please select a scientist first.");
            }
        });
    }
}