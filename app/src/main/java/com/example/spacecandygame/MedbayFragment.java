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

        healButton.setOnClickListener(v -> {
            if (selectedCrewMember != null) {
                selectedCrewMember.healed();
                selectedCrewMember.setLocation(Location.QUARTERS);

                statsText.setText(
                        "Name: " + selectedCrewMember.getName() +
                                "\nType: " + selectedCrewMember.getCrewType() +
                                "\nColor: " + selectedCrewMember.getColor() +
                                "\nXP: " + selectedCrewMember.getXP() +
                                "\nEnergy: " + selectedCrewMember.getEnergy() +
                                "\nLocation: " + selectedCrewMember.getLocation() +
                                "\nStatus: Healed and returned to Quarters"
                );
            } else {
                statsText.setText("Please select a crew member first.");
            }
        });
    }
}