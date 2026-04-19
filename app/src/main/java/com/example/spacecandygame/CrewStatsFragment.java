package com.example.spacecandygame;

//This file contains the Crew Stats Fragment class to define the fragment which in UI displays
//stats for the entire crew and for each individual member of the crew using recycler view

//AI Usage Declaration: Gemini AI was used to assist in writing UI and structuring the code for the recycler view,
//and for troubleshooting errors

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CrewStatsFragment extends Fragment {

    private RecyclerView recyclerView;
    private TextView overallStatsText;
    private TextView textCrewPoints;
    private Button teamStatsButton;
    private Button memberStatsButton;
    private ProgressBar progressBarTotalCrewPoints;

    public CrewStatsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_crew_stats, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView);
        overallStatsText = view.findViewById(R.id.overallStatsText);
        textCrewPoints = view.findViewById(R.id.textCrewPoints);
        teamStatsButton = view.findViewById(R.id.crewStatsButton);
        memberStatsButton = view.findViewById(R.id.memberStatsButton);
        Button backButton = view.findViewById(R.id.backButton);
        progressBarTotalCrewPoints = view.findViewById(R.id.progressBarTotalCrewPoints);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        
        GameTracker gameTracker = MainActivity.getGameTracker();
        List<CrewMember> crewList = gameTracker.getCrewList();

        // Initially show overall stats
        showOverallStats(gameTracker);

        teamStatsButton.setOnClickListener(v -> showOverallStats(gameTracker));

        memberStatsButton.setOnClickListener(v -> {
            overallStatsText.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            if (progressBarTotalCrewPoints != null) {
                progressBarTotalCrewPoints.setVisibility(View.GONE);
            }
            if (textCrewPoints != null) {
                textCrewPoints.setVisibility(View.GONE);
            }
            recyclerView.setAdapter(new CrewMemberAdapter(crewList));
        });

        backButton.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });
    }

    public void updateProgressBar(int crewPointsTotal) {
        if (progressBarTotalCrewPoints != null) {
            progressBarTotalCrewPoints.setVisibility(View.VISIBLE);
            progressBarTotalCrewPoints.setMax(1000); // Set a reasonable goal or scale
            progressBarTotalCrewPoints.setProgress(crewPointsTotal);
        }
        if (textCrewPoints != null) {
            textCrewPoints.setVisibility(View.VISIBLE);
        }
    }

    private void showOverallStats(GameTracker tracker) {
        overallStatsText.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);


        List<CrewMember> list = tracker.getCrewList();
        int soldiers = 0, engineers = 0, scientists = 0, dragons = 0, doctors = 0;

        for (CrewMember member : list) {
            if (member instanceof Soldier) soldiers++;
            else if (member instanceof Engineer) engineers++;
            else if (member instanceof Scientist) scientists++;
            else if (member instanceof Dragon) dragons++;
            else if (member instanceof Doctor) doctors++;
        }

        updateProgressBar(tracker.getCrewPoints());

        String crewStats = "Total Missions: " + tracker.getTotalMissions() + "\n" +
                "Total Crew Points: " + tracker.getCrewPoints() + "\n" +
                "Number of Crew Members: " + list.size() + "\n\n" +
                "Breakdown by Type:\n" +
                "- Soldiers: " + soldiers + "\n" +
                "- Engineers: " + engineers + "\n" +
                "- Scientists: " + scientists + "\n" +
                "- Dragons: " + dragons + "\n" +
                "- Doctors: " + doctors;

        overallStatsText.setText(crewStats);
    }
}