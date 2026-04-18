package com.example.spacecandygame;
//This file contains the Mission Fragment to define user interaction with the fragment for the battle arena

//AI Usage Declaration: Gemini AI was used to assist in writing this file, and for troubleshooting errors

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
    private int villainsProcessed = 0;
    
    private boolean isWarrior1Turn = true;
    private Threat currentThreat;

    public MissionFragment() {
        // Required empty public constructor
    }

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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        battleArena = view.findViewById(R.id.battleArena);
        startButton = view.findViewById(R.id.startButton);
        Button backButton = view.findViewById(R.id.backButton);
        summaryLayout = view.findViewById(R.id.summaryLayout);
        TextView nameText1 = view.findViewById(R.id.crewMemberName1);
        TextView nameText2 = view.findViewById(R.id.crewMemberName2);

        GameTracker tracker = MainActivity.getGameTracker();
        
        if (getArguments() != null) {
            String w1Id = getArguments().getString(ARG_WARRIOR1_ID);
            String w2Id = getArguments().getString(ARG_WARRIOR2_ID);
            
            for (CrewMember cm : tracker.getCrewList()) {
                if (cm.getId().equals(w1Id)) warrior1 = cm;
                if (cm.getId().equals(w2Id)) warrior2 = cm;
            }
        }

        if (warrior1 != null && warrior2 != null) {
            updateTurnDisplay(nameText1, nameText2);
            warrior1.setLocation(Location.BATTLE);
            warrior2.setLocation(Location.BATTLE);
        } else {
            nameText1.setText("Warriors not properly selected.");
            nameText2.setText("");
            startButton.setEnabled(false);
        }

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

    private void startBattle() {
        if (warrior1 == null || warrior2 == null) return;

        startButton.setEnabled(false);
        crewPointsGainedSession = 0;
        villainsProcessed = 0;
        
        GameTracker tracker = MainActivity.getGameTracker();
        currentThreat = new Threat(tracker.getTotalMissions() + 1);

        spawnNextVillain();
    }

    private void spawnNextVillain() {
        if (currentThreat.missionComplete() || (warrior1.getEnergy() <= 0 && warrior2.getEnergy() <= 0)) {
            showSummary();
            return;
        }

        VillainType type = random.nextBoolean() ? VillainType.SOUR_GUMMY_WORM : VillainType.HARD_CANDY;
        
        TextView villain = new TextView(getContext());
        villain.setText(type == VillainType.SOUR_GUMMY_WORM ? "🐛" : "🍬");
        villain.setTextSize(60); 

        battleArena.post(() -> {
            int arenaWidth = battleArena.getWidth();
            int arenaHeight = battleArena.getHeight();
            if (arenaHeight <= 0) arenaHeight = 800;

            int startY = random.nextInt(Math.max(1, arenaHeight - 150));
            battleArena.addView(villain);

            villain.setX(-300);
            villain.setY(startY);

            ObjectAnimator animator = ObjectAnimator.ofFloat(villain, "translationX", -300f, (float) arenaWidth + 300f);
            animator.setDuration(7000);

            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    if (villain.getParent() != null) {
                        battleArena.removeView(villain);
                        CrewMember activeWarrior = isWarrior1Turn ? warrior1 : warrior2;
                        activeWarrior.takeBattleDamage();
                        
                        switchTurn();
                        spawnNextVillain();
                    }
                }
            });

            villain.setOnClickListener(v -> {
                GameTracker tracker = MainActivity.getGameTracker();
                CrewMember activeWarrior = isWarrior1Turn ? warrior1 : warrior2;
                int gained = Mission.onMissionClick(activeWarrior, type);
                
                tracker.setCrewPoints(gained);
                crewPointsGainedSession += gained;
                currentThreat.threatDamage();

                battleArena.removeView(villain);
                animator.cancel();
                
                switchTurn();
                spawnNextVillain();
            });

            animator.start();
        });
    }

    private void switchTurn() {
        isWarrior1Turn = !isWarrior1Turn;
        if (isWarrior1Turn && warrior1.getEnergy() <= 0) isWarrior1Turn = false;
        if (!isWarrior1Turn && warrior2.getEnergy() <= 0) isWarrior1Turn = true;
        
        View view = getView();
        if (view != null) {
            updateTurnDisplay(view.findViewById(R.id.crewMemberName1), view.findViewById(R.id.crewMemberName2));
        }
    }

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