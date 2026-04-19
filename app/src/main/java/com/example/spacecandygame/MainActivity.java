package com.example.spacecandygame;

/*
This file contains the MainActivity class to define the main page of the game which includes the
buttons to recruit new crew members, go to the homes, and view the rules.

AI Usage Declaration: ChatGPT AI was used for troubleshooting errors within the code once written.
AI was used to generate the background image.

ChatGPT was used to solve some merge conflicts
*/

import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private static GameTracker gameTracker = new GameTracker(null);

    public static GameTracker getGameTracker() {
        return gameTracker;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button homeButton = findViewById(R.id.goToHomeButton);

        Button recruitButton = findViewById(R.id.recruitButton);
        //Button to Open home screen
        homeButton.setOnClickListener(v -> {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main, new HomeFragment())
                    .addToBackStack(null)
                    .commit();
        });
       // removed the button to go to medbay
        //Button to go to recruit
        recruitButton.setOnClickListener(v -> {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main, new RecruitFragment())
                    .addToBackStack(null)
                    .commit();
        });
        Button rulesButton = findViewById(R.id.rulesButton);
        rulesButton.setOnClickListener(v -> {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main, new RulesFragment())
                    .addToBackStack(null)
                    .commit();
        });


    }
}