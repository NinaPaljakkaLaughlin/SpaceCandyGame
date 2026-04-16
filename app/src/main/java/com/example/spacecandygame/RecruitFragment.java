package com.example.spacecandygame;
//AI use declaration: AI was used to generate the background image
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.List;

public class RecruitFragment extends Fragment {

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

        EditText nameInput = view.findViewById(R.id.nameInput);
        EditText idInput = view.findViewById(R.id.idInput);
        Spinner typeSpinner = view.findViewById(R.id.typeSpinner);
        Spinner colorSpinner = view.findViewById(R.id.colorSpinner);
        TextView resultText = view.findViewById(R.id.resultText);

        String[] crewTypes = {"SOLDIER", "ENGINEER", "SCIENTIST", "DRAGON"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                crewTypes
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

        startButton.setOnClickListener(v -> {
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
}