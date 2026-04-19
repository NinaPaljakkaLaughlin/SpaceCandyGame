package com.example.spacecandygame;

/*
This file contains the Recruit fragment subclass to define the fragment in which UI displays
the recruitment page to allow the user to create new crew members: entering the name and id of the crew member
as well as selecting the type of crew member (with stats viewable) and the color of the character.

AI Usage Declaration: Gemini AI was used to assist in writing this file, and for troubleshooting errors, and for troubleshooting errors
within the code once written.
AI was used to generate the background image and the crew member images.

ChatGPT was used to solve some merge conflicts
*/

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.List;

public class RecruitFragment extends Fragment {

    private ImageView characterPreview;
    private Spinner typeSpinner;
    private Spinner colorSpinner;
    private TextView descriptionText;
    private View colorOverlay;

    public RecruitFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recruit, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button startButton = view.findViewById(R.id.goToStartButton);
        Button createButton = view.findViewById(R.id.createCharacterButton);
        Button cancelButton = view.findViewById(R.id.cancelButton);

        EditText nameInput = view.findViewById(R.id.nameInput);
        EditText idInput = view.findViewById(R.id.idInput);
        typeSpinner = view.findViewById(R.id.typeSpinner);
        colorSpinner = view.findViewById(R.id.colorSpinner);
        TextView resultText = view.findViewById(R.id.resultText);
        descriptionText = view.findViewById(R.id.descriptionText);
        characterPreview = view.findViewById(R.id.characterPreview);
        colorOverlay = view.findViewById(R.id.colorOverlay);

        String[] crewTypeNames = {"SOLDIER", "ENGINEER", "SCIENTIST", "DRAGON", "DOCTOR"};

        String[] crewTypeDescriptions = {
                "SOLDIER- starting XP: 0, starting energy: 12, starting skill power: 1",
                "ENGINEER- starting XP: 0, starting energy: 12, starting skill power: 1, special ability: can plant flowers to gain extra XP",
                "SCIENTIST- starting XP: 0, starting energy: 12, starting skill power: 1, special ability: can pick flowers and create chemicals to use in training and battle arenas",
                "DRAGON- starting XP: -10, starting energy: 12, starting skill power: 1, special abilities: can gain crew points in battle by attacking hard candies and gummy worms after reaching 60 XP",
                "DOCTOR: starting XP: 0, starting energy: 12, starting skill power: 1, special abilities: gains XP from training arena and for every 10 XP can heal other crew members to full energy after battle drops their energy to 0"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                crewTypeNames
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter);

        String[] colors = {"Pink", "Purple", "Yellow", "Green", "Blue", "Red", "Black"};
        ArrayAdapter<String> colorAdapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                colors
        );
        colorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        colorSpinner.setAdapter(colorAdapter);

        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View spinnerView, int position, long id) {
                descriptionText.setText(crewTypeDescriptions[position]);
                updateCharacterPreview();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                descriptionText.setText("Select a type to see abilities");
            }
        });

        colorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View spinnerView, int position, long id) {
                updateCharacterPreview();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        updateCharacterPreview();

        startButton.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        cancelButton.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        createButton.setOnClickListener(v -> {
            String name = nameInput.getText().toString().trim();
            String id = idInput.getText().toString().trim();
            String selectedType = typeSpinner.getSelectedItem().toString();
            String selectedColor = colorSpinner.getSelectedItem().toString();

            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(id)) {
                resultText.setText("Please enter both name and ID.");
                return;
            }

            GameTracker gameTracker = MainActivity.getGameTracker();

            if (selectedType.equals("SOLDIER")) {
                gameTracker.createSoldier(id, name);
            } else if (selectedType.equals("ENGINEER")) {
                gameTracker.createEngineer(id, name);
            } else if (selectedType.equals("SCIENTIST")) {
                gameTracker.createScientist(id, name);
            } else if (selectedType.equals("DRAGON")) {
                gameTracker.createDragon(id, name);
            } else if (selectedType.equals("DOCTOR")) {
                gameTracker.createDoctor(id, name);
            }

            List<CrewMember> list = gameTracker.getCrewList();
            if (!list.isEmpty()) {
                CrewMember newMember = list.get(list.size() - 1);
                newMember.setColor(selectedColor);
            }

            resultText.setText(selectedType + " created: " + name + " (" + selectedColor + ")");

            nameInput.setText("");
            idInput.setText("");
        });
    }

    private void updateCharacterPreview() {
        if (typeSpinner == null || colorSpinner == null || characterPreview == null || colorOverlay == null) {
            return;
        }

        String selectedType = typeSpinner.getSelectedItem().toString();
        String selectedColor = colorSpinner.getSelectedItem().toString();

        int imageResId = getCharacterImage(selectedType);
        characterPreview.setImageResource(imageResId);
        characterPreview.clearColorFilter();
        characterPreview.setImageAlpha(255);

        int overlayColor = getSelectedColorValue(selectedColor);
        colorOverlay.setBackgroundColor(overlayColor);
    }

    private int getCharacterImage(String characterType) {
        switch (characterType) {
            case "SOLDIER":
                return R.drawable.soldier;
            case "ENGINEER":
                return R.drawable.engineer;
            case "SCIENTIST":
                return R.drawable.scientist;
            case "DRAGON":
                return R.drawable.dragon;
            case "DOCTOR":
                return R.drawable.doctor;
            default:
                return R.drawable.soldier;
        }
    }

    private int getSelectedColorValue(String colorName) {
        switch (colorName) {
            case "Pink":
                return Color.parseColor("#44FFC0CB");
            case "Purple":
                return Color.parseColor("#448A2BE2");
            case "Yellow":
                return Color.parseColor("#44FFFF00");
            case "Green":
                return Color.parseColor("#4400FF00");
            case "Blue":
                return Color.parseColor("#440000FF");
            case "Red":
                return Color.parseColor("#44FF0000");
            case "Black":
                return Color.parseColor("#44000000");
            default:
                return Color.parseColor("#00FFFFFF");
        }
    }
}