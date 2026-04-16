package com.example.spacecandygame;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class DragonHouseFragment extends Fragment {

    private CrewMember selectedDragon;

    public DragonHouseFragment() {

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
        TextView statsText = view.findViewById(R.id.soldierStatsText);
        LinearLayout dragonContainer = view.findViewById(R.id.soldierContainer);

        startButton.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
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
                                    "\nEnergy: " + crewMember.getEnergy()
                    );
                });

                dragonContainer.addView(dragonButton);
            }
        }

        trainButton.setOnClickListener(v -> {
            if (selectedDragon != null) {
                selectedDragon.addXP(3);
                statsText.setText(
                        "Name: " + selectedDragon.getName() +
                                "\nColor: " + selectedDragon.getColor() +
                                "\nXP: " + selectedDragon.getXP() +
                                "\nEnergy: " + selectedDragon.getEnergy() +
                                "\nStatus: Trained"
                );
            } else {
                statsText.setText("Please select a dragon first.");
            }
        });

        battleButton.setOnClickListener(v -> {
            if (selectedDragon != null) {
                selectedDragon.takeBattleDamage();
                statsText.setText(
                        "Name: " + selectedDragon.getName() +
                                "\nColor: " + selectedDragon.getColor() +
                                "\nXP: " + selectedDragon.getXP() +
                                "\nEnergy: " + selectedDragon.getEnergy() +
                                "\nStatus: Went to battle"
                );
            } else {
                statsText.setText("Please select a dragon first.");
            }
        });
    }
}