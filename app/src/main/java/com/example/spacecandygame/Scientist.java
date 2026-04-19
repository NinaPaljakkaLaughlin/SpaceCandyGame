package com.example.spacecandygame;

/*
This file contains the Scientist sub-class of the CrewMember parent class to define attributes and
methods for crew member type scientist

AI Usage Declaration: ChatGPT AI was used to assist in writing pseudocode for the structure of this file, and for troubleshooting errors
within the code once written.

ChatGPT was used to solve merge conflicts
*/

public class Scientist extends CrewMember{
    //Attributes
    private int flowers; //need getter for UI
    private int chemicals; //need getter for UI

    //Constructor
    public Scientist(String id, String name) {
        super(id, name);
        setCrewType(CrewType.SCIENTIST);
        this.resilience = 8;
        this.XP = 0;
    }
    //Special ability: can attack sour gummy worms with chemical to train and battle
    //must pick flowers in the flower field and make chemical weapon for training and battle

    //while training to get 50 XP, can attack sour gummy worms to gain XP
    //while > 50XP, can attack sour gummy worms in training(to gain XP) and can battle(to complete missions and gain crew points)
    @Override
    public boolean canEnterBattle() {
        return getXP() >= 50;
    }
    //Method to handle click in training and battle for points, XP, and damage
    @Override
    public int onTrainClick(VillainType target) {
        if (!hasChemicals()) return 0;
        if (target == VillainType.SOUR_GUMMY_WORM) {
            chemicals--;
            addXP(2);
        } else {
            takeTrainingDamage();
        }
        return 0;
    }
    @Override
    public int onMissionClick(VillainType target) {
        if (!hasChemicals()) return 0;
        if (target == VillainType.SOUR_GUMMY_WORM) {
            chemicals--;
            addXP(5);
        } else {
            takeTrainingDamage();
        }
        return 0;
    }
    //Method to get flowers number for UI display
    public int getFlowers() {
        return flowers;
    }
    //Method to get chemicals number for UI display
    public int getChemicals() {
        return chemicals;
    }
    //Method for picking flowers
    public void pickFlowers() {
        flowers += 1; //total flower count tracked here when flowers are clicked on UI
    }
    //Method for using flowers to make chemical
    public void makeChemical() {
        if (flowers >= 5) {
            chemicals += 1; //total chemical count goes up, costs 5 flowers
            flowers -= 5; //total flower count subtracts when a chemical is formulated
        }
    }
    //Method for checking if scientist can start training
    public boolean canStartTraining() {
        return chemicals >= 10;
    }
    //Method for tracking existence of chemicals
    public boolean hasChemicals() {
        return chemicals > 0;
    }
    //Method to track ability to attack (must have chemical stock)
    public boolean canAttack() {
        if (!hasChemicals())
            return false;
        else
            return true;
    }
    @Override
    public int crewMemberAction(VillainType target) {
        return attack(target);
    }
    public int attack(VillainType target) { //should return crew points
        //must have chemicals
        if (!hasChemicals()) {
            return 0;
        }
        else
            chemicals--;
        //scientist can only train
        if (getLocation() == Location.TRAINING && getXP() < 50) {
            if (target == VillainType.SOUR_GUMMY_WORM) {
                addXP(2);
            } else {
                takeTrainingDamage();
            }
            return 0; //returns 0 crew points when training
        }

        //scientist can train and enter battle
        //training after XP >= 50
        if (getLocation() == Location.TRAINING && getXP() >= 50) {
            if (target == VillainType.SOUR_GUMMY_WORM) {
                addXP(5);
            }
            else {
                takeTrainingDamage();
            }
            return 0; //returns 0 crew points when training
        }
        //battle
        if (getLocation() == Location.BATTLE && getXP() >= 50) {
            if (target == VillainType.SOUR_GUMMY_WORM) {
                return 2; //crew points gained
            } else if (target == VillainType.GUMMY_BEAR || target == VillainType.HARD_CANDY) {
                takeBattleDamage(); //lose energy when you hit gummy bears by accident
            }
        }
        return 0;
    }

}


