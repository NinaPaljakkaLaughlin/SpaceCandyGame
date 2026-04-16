package com.example.spacecandygame;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class SoldierHouseFragment extends Fragment {

    private CrewMember selectedSoldier;

    public SoldierHouseFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_soldier_house, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button startButton = view.findViewById(R.id.goToStartButton);
        Button trainButton = view.findViewById(R.id.trainButton);
        Button battleButton = view.findViewById(R.id.battleButton);
        TextView statsText = view.findViewById(R.id.soldierStatsText);
        LinearLayout soldierContainer = view.findViewById(R.id.soldierContainer);

        startButton.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        GameTracker gameTracker = MainActivity.getGameTracker();

        for (CrewMember crewMember : gameTracker.getCrewList()) {
            if (crewMember.getCrewType() == CrewType.SOLDIER) {
                Button soldierButton = new Button(getContext());
                soldierButton.setText(crewMember.getName());

                soldierButton.setOnClickListener(v -> {
                    selectedSoldier = crewMember;
                    statsText.setText(
                            "Name: " + crewMember.getName() +
                                    "\nColor: " + crewMember.getColor() +
                                    "\nXP: " + crewMember.getXP() +
                                    "\nEnergy: " + crewMember.getEnergy()
                    );
                });

                soldierContainer.addView(soldierButton);
            }
        }

        trainButton.setOnClickListener(v -> {
            if (selectedSoldier != null) {
                selectedSoldier.addXP(3);
                statsText.setText(
                        "Name: " + selectedSoldier.getName() +
                                "\nColor: " + selectedSoldier.getColor() +
                                "\nXP: " + selectedSoldier.getXP() +
                                "\nEnergy: " + selectedSoldier.getEnergy() +
                                "\nStatus: Trained"
                );
            } else {
                statsText.setText("Please select a soldier first.");
            }
        });

        battleButton.setOnClickListener(v -> {
            if (selectedSoldier != null) {
                selectedSoldier.takeBattleDamage();
                statsText.setText(
                        "Name: " + selectedSoldier.getName() +
                                "\nColor: " + selectedSoldier.getColor() +
                                "\nXP: " + selectedSoldier.getXP() +
                                "\nEnergy: " + selectedSoldier.getEnergy() +
                                "\nStatus: Went to battle"
                );
            } else {
                statsText.setText("Please select a soldier first.");
            }
        });
    }
}