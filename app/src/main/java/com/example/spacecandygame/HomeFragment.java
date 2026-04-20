package com.example.spacecandygame;

/*
This file contains home fragment subclass to define the fragment in which UI displays
the home (quarters) which is where all crew member types homes can be clicked to access the created
crew members and access their activities and stats.

AI Usage Declaration: ChatGPT AI was used for troubleshooting errors within the code once written.
AI was used to generate the background image.

ChatGPT was used to solve some merge conflicts
*/

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    //Constructor
    public HomeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    //creating the homes (quarters) screen
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button startButton = view.findViewById(R.id.goToStartButton);
        Button soldierHouseButton = view.findViewById(R.id.soldierHouseButton);
        Button scientistHouseButton = view.findViewById(R.id.scientistHouseButton);
        Button engineerHouseButton = view.findViewById(R.id.engineerHouseButton);
        Button dragonHouseButton = view.findViewById(R.id.dragonHouseButton);
        Button medbayButton = view.findViewById(R.id.medbayButton);
        Button viewStatsButton = view.findViewById(R.id.viewStatsButton);

        //Go back to start
        startButton.setOnClickListener(v -> {
            requireActivity().finish();
            requireActivity().startActivity(new Intent(requireActivity(), MainActivity.class));
        });

        //Go to stats screen
        viewStatsButton.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main, new CrewStatsFragment())
                    .addToBackStack(null)
                    .commit();
        });

        //Go to soldier house
        soldierHouseButton.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main, new SoldierHouseFragment())
                    .addToBackStack(null)
                    .commit();
        });

        //Go to scientist house
        scientistHouseButton.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main, new ScientistHouseFragment())
                    .addToBackStack(null)
                    .commit();
        });

        //Go to engineer house
        engineerHouseButton.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main, new EngineerHouseFragment())
                    .addToBackStack(null)
                    .commit();
        });

        //Go to dragon house
        dragonHouseButton.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main, new DragonHouseFragment())
                    .addToBackStack(null)
                    .commit();
        });

        //Go to medbay (doctor house)
        medbayButton.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main, new MedbayFragment())
                    .addToBackStack(null)
                    .commit();
        });
    }
}
