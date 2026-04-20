package com.example.spacecandygame;

/*
This file contains the Rules fragment subclass to define the fragment in which UI displays
the rules for the game when the Rules button is selected from the main page

AI Usage Declaration: Gemini AI was used for troubleshooting errors within the code once written.
AI was used to generate the background image.

ChatGPT was used to solve some merge conflicts
*/

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class RulesFragment extends Fragment {

    //Constructor
    public RulesFragment() {
        // Required empty constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rules, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button backButton = view.findViewById(R.id.backButton);

        backButton.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });
    }
}