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
        Button sendToMedbayButton = view.findViewById(R.id.sendToMedbayButton);
        TextView statsText = view.findViewById(R.id.soldierStatsText);
        LinearLayout soldierContainer = view.findViewById(R.id.soldierContainer);

        startButton.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main, new HomeFragment())
                    .addToBackStack(null)
                    .commit();
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
                                    "\nEnergy: " + crewMember.getEnergy() +
                                    "\nLocation: " + crewMember.getLocation()

                    );
                });

                soldierContainer.addView(soldierButton);
            }
        }

        trainButton.setOnClickListener(v -> {
            if (selectedSoldier != null) {
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main, TrainingFragment.newInstance(selectedSoldier.getId()))
                        .addToBackStack(null)
                        .commit();
            } else {
                statsText.setText("Please select a soldier first.");
            }
        });

        battleButton.setOnClickListener(v -> {
            if (selectedSoldier != null) {
                if (selectedSoldier.canEnterBattle()) {
                    selectedSoldier.setLocation(Location.BATTLE);

                    requireActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main, new BattleFragment())
                            .addToBackStack(null)
                            .commit();
                } else {
                    statsText.setText("This soldier needs more XP before entering battle.");
                }
            } else {
                statsText.setText("Please select a soldier first.");
            }
        });

        sendToMedbayButton.setOnClickListener(v -> {
            if (selectedSoldier != null) {
                selectedSoldier.setLocation(Location.MEDBAY);
                statsText.setText(
                        "Name: " + selectedSoldier.getName() +
                                "\nColor: " + selectedSoldier.getColor() +
                                "\nXP: " + selectedSoldier.getXP() +
                                "\nEnergy: " + selectedSoldier.getEnergy() +
                                "\nLocation: " + selectedSoldier.getLocation() +
                                "\nStatus: Sent to Medbay"
                );
            } else {
                statsText.setText("Please select a soldier first.");
            }
        });
    }
}