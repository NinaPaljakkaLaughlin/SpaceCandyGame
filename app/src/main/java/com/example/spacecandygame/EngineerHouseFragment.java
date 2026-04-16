package com.example.spacecandygame;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class EngineerHouseFragment extends Fragment {

    private CrewMember selectedEngineer;

    public EngineerHouseFragment() {
        // Required empty public constructor
    }

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
        TextView statsText = view.findViewById(R.id.soldierStatsText);
        LinearLayout engineerContainer = view.findViewById(R.id.soldierContainer);

        startButton.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
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
                                    "\nEnergy: " + crewMember.getEnergy()
                    );
                });

                engineerContainer.addView(engineerButton);
            }
        }

        trainButton.setOnClickListener(v -> {
            if (selectedEngineer != null) {
                selectedEngineer.addXP(3);
                statsText.setText(
                        "Name: " + selectedEngineer.getName() +
                                "\nColor: " + selectedEngineer.getColor() +
                                "\nXP: " + selectedEngineer.getXP() +
                                "\nEnergy: " + selectedEngineer.getEnergy() +
                                "\nStatus: Trained"
                );
            } else {
                statsText.setText("Please select an engineer first.");
            }
        });

        battleButton.setOnClickListener(v -> {
            if (selectedEngineer != null) {
                selectedEngineer.takeBattleDamage();
                statsText.setText(
                        "Name: " + selectedEngineer.getName() +
                                "\nColor: " + selectedEngineer.getColor() +
                                "\nXP: " + selectedEngineer.getXP() +
                                "\nEnergy: " + selectedEngineer.getEnergy() +
                                "\nStatus: Went to battle"
                );
            } else {
                statsText.setText("Please select an engineer first.");
            }
        });
    }
}