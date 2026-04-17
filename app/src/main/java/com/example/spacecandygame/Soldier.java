package com.example.spacecandygame;

public class Soldier extends CrewMember {

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

    //Required abstract methods (UI not using yet)
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

    public int attack(VillainType target) {

        //only sour gummy worm gives +xp
        //Soldier before enough XP
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
