package com.example.spacecandygame;
//This file contains the Dragon child class to define attributes and methods for crew member type dragon, it inherits the crew member class

//AI Usage Declaration: ChatGPT AI was used to assist in writing pseudocode for the structure of this file, and for troubleshooting errors
//within the code once written. No code has been written by AI or copy-pasted from an AI source.

public class Dragon extends CrewMember {
    //constructor
    public Dragon(String id, String name) {
            super(id, name);
            setCrewType(CrewType.DRAGON);
            this.resilience = 10;
            this.XP = -10;
        }

    @Override
    public boolean canEnterBattle() {
        return getXP() >= 60;
    }
}

