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
    //Special ability: can breathe fire to train and battle

    //while training to get 60 XP, can attack sour gummy worms to gain XP
    //while > 60XP, can attack sour and sweet gummy worms in training(to gain XP) and can battle(to complete missions and gain crew points)
    @Override
    public boolean canEnterBattle() {
        return getXP() >= 60;
    }

    public int attack(VillainType target) { //should return crew points
        //dragon can only train
        if (getLocation() == Location.TRAINING && getXP() < 60) {
            if (target == VillainType.SOUR_GUMMY_WORM) {
                addXP(2);
            } else {
                takeTrainingDamage();
            }
            return 0; //returns 0 crew points when training
        }

        //dragon can train and enter battle
        //training after XP >= 60
        if (getLocation() == Location.TRAINING && getXP() >= 60) {
            if (target == VillainType.SOUR_GUMMY_WORM || target == VillainType.SWEET_GUMMY_WORM) {
                addXP(5);
            }
            else {
                takeTrainingDamage();
            }
            return 0; //returns 0 crew points when training
        }
        //battle
        if (getLocation() == Location.BATTLE && getXP() >= 60) {
            if (target == VillainType.SOUR_GUMMY_WORM || target == VillainType.SWEET_GUMMY_WORM) {
                return 2; //crew points gained
            } else if (target == VillainType.GUMMY_BEAR) {
                takeBattleDamage(); //lose energy when you hit gummy bears by accident
            }
        }
        return 0;
    }
}

