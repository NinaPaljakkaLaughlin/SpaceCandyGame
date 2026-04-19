package com.example.spacecandygame;

/*
This file contains the Doctor sub-class of the CrewMember parent class to define attributes and
methods for crew member type doctor

AI Usage Declaration: ChatGPT AI was used to assist in writing pseudocode for the structure of this file, and for troubleshooting errors
within the code once written.

ChatGPT was used to solve some merge conflicts
*/

public class Doctor extends CrewMember {
    //variable to count heals used
    private int healsUsed;

    //Constructor
    public Doctor(String id, String name) {
        super(id, name);
        setCrewType(CrewType.DOCTOR);
        this.resilience = 5;
        this.XP = 0;
        this.healsUsed = 0;
        this.location = Location.MEDBAY;
    }

    //Doctor cannot enter battle arena
    @Override
    public boolean canEnterBattle() {
        return false;
    }

    //Doctor trains by attacking sour gummy worms
    public int attack(VillainType target) {
        if (getLocation() == Location.MEDBAY) {
            if (target == VillainType.SOUR_GUMMY_WORM) {
                addXP(10);
            } else {
                takeTrainingDamage();
            }
        }
        return 0;
    }

    //Required abstract methods
    @Override
    public int onTrainClick(VillainType target) {
        return attack(target);
    }

    @Override
    public int onMissionClick(VillainType target) {
        return 0;
    }

    @Override
    public int crewMemberAction(VillainType target) {
        return attack(target);
    }

    //Method to check if doctor can heal
    public boolean canHeal() {
        return (getXP() / 10) > healsUsed;
    }

    //Method to heal crew member
    public boolean healCrewMember(CrewMember target) {
        if (target != null && canHeal()) {
            target.healed();
            healsUsed++;
            return true;
        }
        return false;
    }

    //Method to calculate heals remaining after healing a crew member
    public int getRemainingHeals() {
        return (getXP() / 10) - healsUsed;
    }
}