package com.example.spacecandygame;
//This file contains the GameTracker class to track total crew points for points gained in battle, number
//of missions completed, hold the crew list

//AI Usage Declaration: ChatGPT AI was used to assist in writing pseudocode for the structure of this file, and for troubleshooting errors
//within the code once written.ChatGPT AI helped to build the structure of the class so that it functioned properly.

public class Threat {
    private int threatPower; //the number of gummys that will attack in battle
    private int threatCounter; //a counter to track when all gummys are defeated during battle
    //Constructor
    public Threat(int missionsCompleted) {
        this.threatPower = missionsCompleted * 5;
        this.threatCounter = threatPower;
    }
    //Method for gummy attack and power (number of gummys that attack during the mission)
    public int threatAttack() {
        return threatPower;
    }
    //Method for tracking when gummys are hit by crew members
        //UI should run power number of bad gummys on the screen until threat counter hits 0 (all gummys are killed)
        //number of good gummys should be randomly generated between 1-100
        //number of bad gummys should increase as number of missions completed increases
    public void threatDamage() {
        if (threatCounter > 0) {
            threatCounter --;
        }
    }
    //Method for accessing the threat counter
    public int getThreatCounter() {
        return threatCounter;
    }
    //Method for tracking when a round is done
    public boolean missionComplete() {
        return threatCounter <= 0;
    }

}
