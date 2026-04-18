package com.example.spacecandygame;
//AI Declaration: Gemini AI was used for generating training UI and implementing
//the training arena buttons and the visualization for the training xml

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

import androidx.fragment.app.Fragment;

import java.util.Random;

public class TrainingFragment extends Fragment {

    private String crewMemberId;
    private CrewMember trainee;
    private int xpGainedSession = 0;
    private int xpLostSession = 0;
    private FrameLayout trainingArena;
    private Button startButton;
    private LinearLayout summaryLayout;
    private Random random = new Random();
    private int totalCandyInSession = 0;
    private int wormsProcessed = 0;

    public TrainingFragment() {
        // Required empty public constructor
    }

    public static TrainingFragment newInstance(String crewMemberId) {
        TrainingFragment fragment = new TrainingFragment();
        Bundle args = new Bundle();
        args.putString("crewMemberId", crewMemberId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            crewMemberId = getArguments().getString("crewMemberId");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_training, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        trainingArena = view.findViewById(R.id.trainingArena);
        startButton = view.findViewById(R.id.startButton);
        Button backButton = view.findViewById(R.id.backButton);
        summaryLayout = view.findViewById(R.id.summaryLayout);
        TextView nameText = view.findViewById(R.id.crewMemberName);

        // Find trainee from GameTracker
        GameTracker tracker = MainActivity.getGameTracker();
        if (crewMemberId != null) {
            for (CrewMember cm : tracker.getCrewList()) {
                if (cm.getId().equals(crewMemberId)) {
                    trainee = cm;
                    break;
                }
            }
        }

        if (trainee != null) {
            nameText.setText("Trainee: " + trainee.getName() + " (" + trainee.getCrewType() + ")");
            trainee.setLocation(Location.TRAINING);
        } else {
            nameText.setText("No trainee selected.");
            startButton.setEnabled(false);
        }

        startButton.setOnClickListener(v -> startTraining());

        backButton.setOnClickListener(v -> {
            if (trainee != null) {
                trainee.setLocation(Location.QUARTERS);
            }
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        view.findViewById(R.id.closeSummaryButton).setOnClickListener(v -> {
            summaryLayout.setVisibility(View.GONE);
            startButton.setEnabled(true);
            trainingArena.removeAllViews();
        });
    }

    private void startTraining() {
        if (trainee == null) return;

        startButton.setEnabled(false);
        xpGainedSession = 0;
        xpLostSession = 0;
        wormsProcessed = 0;
        trainee.incrementTrainingSessions();


        int sourWormsCount = random.nextInt(10) + 12;
        int hardCandyCount = random.nextInt(8) + 8;
        totalCandyInSession = sourWormsCount + hardCandyCount;

        for (int i = 0; i < sourWormsCount; i++) {
            spawnWorm(VillainType.SOUR_GUMMY_WORM);
        }
        for (int i = 0; i < hardCandyCount; i++) {
            spawnWorm(VillainType.HARD_CANDY);
        }
    }

    private void spawnWorm(VillainType type) {
        TextView worm = new TextView(getContext());
        if (type == VillainType.SOUR_GUMMY_WORM) {
            worm.setText("🐛"); // Sour worm
            worm.setTextColor(0xFF00FF00);
        } else {
            worm.setText("🍬"); // hard candy
            worm.setTextColor(0xFFFF00FF);
        }
        worm.setTextSize(40);

        trainingArena.post(() -> {
            int arenaWidth = trainingArena.getWidth();
            int arenaHeight = trainingArena.getHeight();
            
            if (arenaHeight <= 0) arenaHeight = 800; // Fallback

            // SPREAD OUT: Use full height of the arena, and wider start/end range
            int startY = random.nextInt(Math.max(1, arenaHeight - 120));
            trainingArena.addView(worm);
            
            worm.setX(-300); // Start further off-screen
            worm.setY(startY);

            // SLOWER: Increased duration for slower movement
            ObjectAnimator animator = ObjectAnimator.ofFloat(worm, "translationX", -300f, (float) arenaWidth + 300f);
            animator.setDuration(12000 + random.nextInt(8000)); // 12-20 seconds per item
            
            // SPREAD OUT IN TIME: More variation in start delays
            animator.setStartDelay(random.nextInt(18000));
            
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    if (worm.getParent() != null) {
                        trainingArena.removeView(worm);
                        wormsProcessed++;
                        checkTrainingEnd();
                    }
                }
            });

            worm.setOnClickListener(v -> {
                if (type == VillainType.SOUR_GUMMY_WORM) {
                    int oldXP = trainee.getXP();
                    trainee.addXP(2); // Gaining XP
                    xpGainedSession += (trainee.getXP() - oldXP);
                } else {
                    int oldXP = trainee.getXP();
                    trainee.takeTrainingDamage(); // Losing XP
                    xpLostSession += trainee.getXP() - oldXP;
                }
                trainingArena.removeView(worm);
                animator.cancel();
                wormsProcessed++;
                checkTrainingEnd();
            });

            animator.start();
        });
    }

    private void checkTrainingEnd() {
        if (wormsProcessed >= totalCandyInSession) {
            showSummary();
        }
    }

    private void showSummary() {
        if (summaryLayout != null) {
            summaryLayout.setVisibility(View.VISIBLE);
            TextView gainedTxt = summaryLayout.findViewById(R.id.summaryXpGained);
            TextView lostTxt = summaryLayout.findViewById(R.id.summaryXpLost);
            TextView skillTxt = summaryLayout.findViewById(R.id.summarySkillPower);
            TextView sessionsTxt = summaryLayout.findViewById(R.id.summaryTotalSessions);

            if (gainedTxt != null) gainedTxt.setText("XP Gained: " + xpGainedSession);
            if (lostTxt != null) lostTxt.setText("XP Lost: " + xpLostSession);
            if (skillTxt != null) skillTxt.setText("Current Skill Power: " + trainee.getSkillPower());
            if (sessionsTxt != null) sessionsTxt.setText("Total Sessions: " + trainee.getTrainingSessions());
        }
    }
}