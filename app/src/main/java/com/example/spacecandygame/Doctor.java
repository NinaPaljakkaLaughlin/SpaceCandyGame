package com.example.spacecandygame;


//AI Usage Declaration: ChatGPT AI was used to assist in writing pseudocode for the structure of this file, and for troubleshooting errors
//within the code once written. No code has been written by AI or copy-pasted from an AI source.

public class Doctor extends CrewMember {

    private int healsUsed; //tracks number of heals used

    public Doctor(String id, String name) {
        super(id, name);
        setCrewType(CrewType.DOCTOR);
        this.resilience = 5;
        this.XP = 0;
        this.healsUsed = 0;
    }

    //Doctor cannot enter battle arena
    @Override
    public boolean canEnterBattle() {
        return false;
    }

    //Doctor trains by attacking sour gummy worms with a scalpel
    public int attack(VillainType target) {
        if (getLocation() == Location.TRAINING) {
            if (target == VillainType.SOUR_GUMMY_WORM) {
                addXP(3);
            } else {
                takeTrainingDamage();
            }
        }
        return 0;
    }

    //Doctor gets one heal for every 10 XP earned
    public boolean canHeal() {
        return (getXP() / 10) > healsUsed;
    }

    //Heal another crew member back to full energy
    public boolean healCrewMember(CrewMember target) {
        if (target != null && canHeal()) {
            target.healed();
            healsUsed++;
            return true;
        }
        return false;
    }

    //Getter for number of heals used
    public int getHealsUsed() {
        return healsUsed;
    }

    //Getter for remaining heals available
    public int getRemainingHeals() {
        return (getXP() / 10) - healsUsed;
    }
}