package com.example.spacecandygame;

/*
This file contains flower field fragment subclass to define the fragment in which UI displays
the flower field which is accessible by the engineer, who plants flowers, and the scientist, who
picks flowers from the field and can make potions from the picked flowers to go into training arena

AI Usage Declaration: ChatGPT AI was used for troubleshooting errors within the code once written.
AI was used to generate the background image.

ChatGPT was used to solve some merge conflicts
*/

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.Random;

public class FlowerFieldFragment extends Fragment {

    private static final String ARG_CREW_ID = "crew_id";
    private static final String ARG_ROLE = "role";
    private CrewMember selectedMember;

    //Constructor
    public FlowerFieldFragment() {
        // Required empty public constructor
    }

    public static FlowerFieldFragment newInstance(String crewId, String role) {
        FlowerFieldFragment fragment = new FlowerFieldFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CREW_ID, crewId);
        args.putString(ARG_ROLE, role);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_flower_field, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button backButton = view.findViewById(R.id.backButton);
        Button plantButton = view.findViewById(R.id.plantButton);
        TextView infoText = view.findViewById(R.id.infoText);
        LinearLayout flowerContainer = view.findViewById(R.id.flowerContainer);

        String crewId = getArguments() != null ? getArguments().getString(ARG_CREW_ID) : null;
        String role = getArguments() != null ? getArguments().getString(ARG_ROLE) : "";

        selectedMember = MainActivity.getGameTracker().getCrewMemberById(crewId);

        backButton.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        //engineer can plant flowers but nobody else can
        if ("ENGINEER".equals(role)) {
            plantButton.setVisibility(View.VISIBLE);
            plantButton.setOnClickListener(v -> {
                int amount = new Random().nextInt(10) + 1; // 1 to 10
                SpecialStuff.addFlowers(amount);

                if (selectedMember instanceof Engineer) {
                    ((Engineer) selectedMember).plantFlower();
                }

                infoText.setText("Planted flowers! Flowers in field: " + SpecialStuff.getFlowersInField());
                renderFlowers(flowerContainer, infoText, role);
            });
        } else {
            plantButton.setVisibility(View.GONE);
        }

        renderFlowers(flowerContainer, infoText, role);
    }

    //method to add flowers to the field as buttons (for scientist to click and collect)
    private void renderFlowers(LinearLayout flowerContainer, TextView infoText, String role) {
        flowerContainer.removeAllViews();

        int totalFlowers = SpecialStuff.getFlowersInField();

        if (totalFlowers == 0) {
            infoText.setText("No flowers in the field yet.");
            return;
        }

        for (int i = 0; i < totalFlowers; i++) {
            Button flowerButton = new Button(getContext());
            flowerButton.setText("🌸");
            flowerButton.setAllCaps(false);
            flowerButton.setGravity(Gravity.CENTER);

            flowerButton.setOnClickListener(v -> {
                if ("SCIENTIST".equals(role) && selectedMember instanceof Scientist) {
                    boolean picked = SpecialStuff.pickOneFlower();
                    if (picked) {
                        ((Scientist) selectedMember).pickFlowers();
                        infoText.setText(
                                "Flowers in field: " + SpecialStuff.getFlowersInField() +
                                        "\nScientist flowers: " + ((Scientist) selectedMember).getFlowers() +
                                        "\nChemicals: " + ((Scientist) selectedMember).getChemicals()
                        );
                        renderFlowers(flowerContainer, infoText, role);
                    }
                }
            });

            flowerContainer.addView(flowerButton);
        }

        //scientist can collect flowers from the field
        if ("SCIENTIST".equals(role) && selectedMember instanceof Scientist) {
            Scientist scientist = (Scientist) selectedMember;
            infoText.setText(
                    "Flowers in field: " + SpecialStuff.getFlowersInField() +
                            "\nScientist flowers: " + scientist.getFlowers() +
                            "\nChemicals: " + scientist.getChemicals()
            );
        } else {
            infoText.setText("Flowers in field: " + SpecialStuff.getFlowersInField());
        }
    }
}