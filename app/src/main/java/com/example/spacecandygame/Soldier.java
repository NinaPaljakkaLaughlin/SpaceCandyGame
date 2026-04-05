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

    public int attack(VillainType target) {

        //only sour gummy worm gives +xp
        //Soldier before enough XP
        if (getLocation() == Location.TRAINING && getXP() < 50) {
            if (target == VillainType.SOUR_GUMMY_WORM) {
                addXP(3);
            } else {
                getDamageAmount();
                takeTrainingDamage();
            }
            return 0;
        }
        //soldier can go into battle after 50 xp
        //in the battle sour and sweet gummy worms give + xp
        //gummy bear gives - xp


        //battle
        if (getLocation() == Location.BATTLE && getXP() >= 50) {
            if (target == VillainType.SOUR_GUMMY_WORM || target == VillainType.SWEET_GUMMY_WORM ) {
                return 2; //crew points gained
            } else if (target == VillainType.GUMMY_BEAR) {
                getDamageAmount();
                takeBattleDamage();
            }
        }

        return 0;
    }
}
