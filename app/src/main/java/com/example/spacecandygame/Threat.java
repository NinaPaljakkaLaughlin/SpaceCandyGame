package com.example.spacecandygame;

/*
This file contains the Threat class to define attributes and methods for threat generation

AI Usage Declaration: ChatGPT AI was used to assist in writing pseudocode for the structure of this file, and for troubleshooting errors
within the code once written.

ChatGPT was used to solve merge conflicts
*/

public class Threat {
    private int threatPower; //total number of gummys that will attack in battle
    private int threatCounter; //a counter to track when all gummys are defeated during battle

    //Constructor
    public Threat(int missionsCompleted) {
        this.threatPower = missionsCompleted * 5;
        this.threatCounter = threatPower;
    }

    //Method for tracking when gummys are hit by crew members
    public void threatDamage() {
        if (threatCounter > 0) {
            threatCounter --;
        }
    }

    //Method for tracking when a round is done
    public boolean missionComplete() {
        return threatCounter <= 0;
    }

}
