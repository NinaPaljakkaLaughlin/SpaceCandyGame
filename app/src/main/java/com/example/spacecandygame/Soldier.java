package com.example.spacecandygame;

/*
This file contains the Soldier sub-class of the CrewMember parent class to define attributes and
methods for crew member type soldier

AI Usage Declaration: ChatGPT AI was used to assist in writing pseudocode for the structure of this file, and for troubleshooting errors
within the code once written.

ChatGPT was used to solve merge conflicts
*/

public class Soldier extends CrewMember {

    //Constructor
    public Soldier(String id, String name) {
        super(id, name);
        setCrewType(CrewType.SOLDIER);
        this.resilience = 8;
        this.XP = 0;
    }

    //soldier’s weapon is a sword that slices the sour gummy worms.
    //Soldier cannot enter battle before gaining 50 xp
    @Override
    public boolean canEnterBattle() {
        return getXP() >= 50;
    }

    //Required abstract methods
    @Override
    public int onTrainClick(VillainType target) {
        addXP(3);
        return 0;
    }

    @Override
    public int onMissionClick(VillainType target) {
        takeBattleDamage();
        return 0;
    }

    @Override
    public int crewMemberAction(VillainType target) {
        return 0;
    }

    //training + battle logic
    public int attack(VillainType target) {
        //training
        //only sour gummy worm gives +xp
        if (getLocation() == Location.TRAINING && getXP() < 50) {
            if (target == VillainType.SOUR_GUMMY_WORM) {
                addXP(3);
            } else {
                takeTrainingDamage();
            }
            return 0;
        }

        //battle
        if (getLocation() == Location.BATTLE && getXP() >= 50) {
            if (target == VillainType.SOUR_GUMMY_WORM || target == VillainType.HARD_CANDY) {
                return 2;
            } else if (target == VillainType.GUMMY_BEAR) {
                takeBattleDamage();
            }
        }

        return 0;
    }
}
