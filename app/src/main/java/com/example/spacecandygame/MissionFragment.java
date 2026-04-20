package com.example.spacecandygame;

/*
This file contains the Mission fragment subclass to define the fragment in which UI displays
the battle arena where two crew members can battle threats in turns to gain crew points.

AI Usage Declaration: Gemini AI was used to assist in writing this file, and for troubleshooting errors, and for troubleshooting errors
within the code once written.
AI was used to generate the background image.

ChatGPT was used to solve some merge conflicts
*/

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;
import java.util.Random;

public class MissionFragment extends Fragment {

    private static final String ARG_WARRIOR1_ID = "warrior1_id";
    private static final String ARG_WARRIOR2_ID = "warrior2_id";

    private CrewMember warrior1;
    private CrewMember warrior2;
    private int crewPointsGainedSession = 0;
    private FrameLayout battleArena;
    private Button startButton;
    private LinearLayout summaryLayout;
    private Random random = new Random();
    private ProgressBar progressBarCrewPoints;
    
    private boolean isWarrior1Turn = true;
    private Threat currentThreat;

    // Track progress of candies defeated in each turn
    private int villainsInTurnProcessed = 0;
    private int villainsInTurnTotal = 0;

    //Constructor
    public MissionFragment() {
        // Required empty public constructor
    }

    //Create instance of mission
    public static MissionFragment newInstance(String warrior1Id, String warrior2Id) {
        MissionFragment fragment = new MissionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_WARRIOR1_ID, warrior1Id);
        args.putString(ARG_WARRIOR2_ID, warrior2Id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mission, container, false);
    }

    //create battle arena page with buttons
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        battleArena = view.findViewById(R.id.battleArena);
        startButton = view.findViewById(R.id.startButton);
        Button backButton = view.findViewById(R.id.backButton);
        summaryLayout = view.findViewById(R.id.summaryLayout);
        TextView nameText1 = view.findViewById(R.id.crewMemberName1);
        TextView nameText2 = view.findViewById(R.id.crewMemberName2);
        progressBarCrewPoints = view.findViewById(R.id.progressBarCrewPoints);

        GameTracker tracker = MainActivity.getGameTracker();
        
        if (getArguments() != null) {
            String w1Id = getArguments().getString(ARG_WARRIOR1_ID);
            String w2Id = getArguments().getString(ARG_WARRIOR2_ID);
            
            for (CrewMember cm : tracker.getCrewList()) {
                if (cm.getId().equals(w1Id)) warrior1 = cm;
                if (cm.getId().equals(w2Id)) warrior2 = cm;
            }
        }

        //once both players are selected, go to battle arena
        if (warrior1 != null && warrior2 != null) {
            updateTurnDisplay(nameText1, nameText2);
            warrior1.setLocation(Location.BATTLE);
            warrior2.setLocation(Location.BATTLE);
        } else {
            nameText1.setText("Warriors not properly selected.");
            nameText2.setText("");
            startButton.setEnabled(false);
        }

        //button to start battle
        startButton.setOnClickListener(v -> startBattle());

        backButton.setOnClickListener(v -> {
            if (warrior1 != null) warrior1.setLocation(Location.QUARTERS);
            if (warrior2 != null) warrior2.setLocation(Location.QUARTERS);
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        view.findViewById(R.id.closeSummaryButton).setOnClickListener(v -> {
            summaryLayout.setVisibility(View.GONE);
            startButton.setEnabled(true);
            battleArena.removeAllViews();
            isWarrior1Turn = true; 
            updateTurnDisplay(nameText1, nameText2);
        });
    }

    //display which crew member is active for the battle turn
    private void updateTurnDisplay(TextView nameText1, TextView nameText2) {
        if (warrior1 == null || warrior2 == null) return;
        
        if (isWarrior1Turn) {
            nameText1.setText("-> Warrior 1: " + warrior1.getName() + " (ACTIVE)");
            nameText2.setText("   Warrior 2: " + warrior2.getName() + " (Waiting)");
        } else {
            nameText1.setText("   Warrior 1: " + warrior1.getName() + " (Waiting)");
            nameText2.setText("-> Warrior 2: " + warrior2.getName() + " (ACTIVE)");
        }
    }

    //start battle, count the battle towards total crew battles/missions
    private void startBattle() {
        if (warrior1 == null || warrior2 == null) return;

        startButton.setEnabled(false);
        crewPointsGainedSession = 0;
        
        GameTracker tracker = MainActivity.getGameTracker();
        currentThreat = new Threat(tracker.getTotalMissions() + 1);

        startTurn();
    }

    private void startTurn() {
        // Check if mission is complete or everyone is out of energy before starting a new wave
        if (currentThreat.missionComplete() || (warrior1.getEnergy() <= 0 && warrior2.getEnergy() <= 0)) {
            showSummary();
            return;
        }

        GameTracker tracker = MainActivity.getGameTracker();
        villainsInTurnProcessed = 0; //track the number of candies already defeated in this turn
        //get a size for the number of candies for the turn based on the total number of missions
        villainsInTurnTotal = random.nextInt(4 * tracker.getTotalMissions()) + 5;

        for (int i = 0; i < villainsInTurnTotal; i++) {
            spawnVillainInWave();
        }
    }

    //Method to get candy threats into battle arena
    private void spawnVillainInWave() {
        // Randomly select between sour gummy worm, hard candies, and gummy bears
        int rand = random.nextInt(3);
        VillainType type;
        if (rand == 0) type = VillainType.SOUR_GUMMY_WORM;
        else if (rand == 1) type = VillainType.HARD_CANDY;
        else type = VillainType.GUMMY_BEAR;

        TextView threatView = new TextView(getContext());
        if (type == VillainType.SOUR_GUMMY_WORM) threatView.setText("🐛");
        else if (type == VillainType.HARD_CANDY) threatView.setText("🍬");
        else threatView.setText("🧸"); // Gummy Bear

        threatView.setTextSize(60);

        battleArena.post(() -> {
            int arenaWidth = battleArena.getWidth();
            int arenaHeight = battleArena.getHeight();
            if (arenaHeight <= 0) arenaHeight = 800;

            int startY = random.nextInt(Math.max(1, arenaHeight - 120));
            battleArena.addView(threatView);

            threatView.setX(-300);
            threatView.setY(startY);

            //animate the threats on the screen
            ObjectAnimator animator = ObjectAnimator.ofFloat(threatView, "translationX", -300f, (float) arenaWidth + 300f);
            //randomly setting speed of the threats
            animator.setDuration(8000 + random.nextInt(4000));
            //randomly setting time between different threats entering the screen
            animator.setStartDelay(random.nextInt(5000));

            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    if (threatView.getParent() != null) {
                        battleArena.removeView(threatView);
                        // If it was a real threat (sour gummy worm), active warrior takes damage if they miss it
                        if (type != VillainType.GUMMY_BEAR || type != VillainType.HARD_CANDY) {
                            CrewMember activeWarrior = isWarrior1Turn ? warrior1 : warrior2;
                            activeWarrior.takeBattleDamage(); //get damage for hitting a gummy bear or hard candy
                        }
                        
                        onVillainProcessed();
                    }
                }
            });

            threatView.setOnClickListener(v -> {
                GameTracker tracker = MainActivity.getGameTracker();
                CrewMember activeWarrior = isWarrior1Turn ? warrior1 : warrior2;

                int gained = Mission.onMissionClick(activeWarrior, type);
                tracker.setCrewPoints(gained);
                crewPointsGainedSession += gained; //tracking crew points gained in battle session
                updateProgressBar(crewPointsGainedSession); //update the progress bar on the screen

                // Only worms count towards defeating the threat
                if (type != VillainType.SOUR_GUMMY_WORM) {
                    currentThreat.threatDamage();
                }

                battleArena.removeView(threatView);
                animator.cancel();
                
                onVillainProcessed();
            });

            animator.start();
        });
    }

    //Method for switching turns once the number of threats calculated have animated
    private void onVillainProcessed() {
        villainsInTurnProcessed++;
        // If all villains in the current turn have gone across the screen, switch turns
        if (villainsInTurnProcessed >= villainsInTurnTotal) {
            switchTurn();
            startTurn();
        }
    }

    //Method for updating progress bar on mission screen for crew points gained
    public void updateProgressBar(int crewPointsGainedSession) {
        if (progressBarCrewPoints != null) {
            progressBarCrewPoints.setMax(1000);
            progressBarCrewPoints.setProgress(crewPointsGainedSession);
        }
    }

    //Method to define switching the turn display
    private void switchTurn() {
        isWarrior1Turn = !isWarrior1Turn;
        if (isWarrior1Turn && warrior1.getEnergy() <= 0) isWarrior1Turn = false;
        if (!isWarrior1Turn && warrior2.getEnergy() <= 0) isWarrior1Turn = true;
        
        View view = getView();
        if (view != null) {
            updateTurnDisplay(view.findViewById(R.id.crewMemberName1), view.findViewById(R.id.crewMemberName2));
        }
    }

    //Method for the summary to show after battle has ended
    private void showSummary() {
        summaryLayout.setVisibility(View.VISIBLE);
        TextView gainedTxt = summaryLayout.findViewById(R.id.summaryCrewPointsGained);
        TextView lostTxt1 = summaryLayout.findViewById(R.id.summaryWarrior1EnergyLost);
        TextView lostTxt2 = summaryLayout.findViewById(R.id.summaryWarrior2EnergyLost);
        TextView skillTxt1 = summaryLayout.findViewById(R.id.summarySkillPower1);
        TextView skillTxt2 = summaryLayout.findViewById(R.id.summarySkillPower2);
        TextView missionsTxt = summaryLayout.findViewById(R.id.summaryTotalMissions);

        gainedTxt.setText("Crew Points Gained: " + crewPointsGainedSession);
        lostTxt1.setText(warrior1.getName() + " Energy: " + warrior1.getEnergy());
        lostTxt2.setText(warrior2.getName() + " Energy: " + warrior2.getEnergy());
        skillTxt1.setText(warrior1.getName() + " Skill Power: " + warrior1.getSkillPower());
        skillTxt2.setText(warrior2.getName() + " Skill Power: " + warrior2.getSkillPower());
        
        GameTracker tracker = MainActivity.getGameTracker();
        tracker.missionTurn(currentThreat);
        missionsTxt.setText("Total Missions: " + tracker.getTotalMissions());
    }
}