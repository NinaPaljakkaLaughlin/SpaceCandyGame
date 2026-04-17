package com.example.spacecandygame;

public class Doctor extends CrewMember {

    private int healsUsed;

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

    //Doctor trains by attacking sour gummy worms
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

    //Required abstract methods
    @Override
    public int onTrainClick(VillainType target) {
        addXP(2);
        return 0;
    }

    @Override
    public int onMissionClick(VillainType target) {
        return 0;
    }

    @Override
    public int crewMemberAction(VillainType target) {
        return 0;
    }

    public boolean canHeal() {
        return (getXP() / 10) > healsUsed;
    }

    public boolean healCrewMember(CrewMember target) {
        if (target != null && canHeal()) {
            target.healed();
            healsUsed++;
            return true;
        }
        return false;
    }

    public int getHealsUsed() {
        return healsUsed;
    }

    public int getRemainingHeals() {
        return (getXP() / 10) - healsUsed;
    }
}