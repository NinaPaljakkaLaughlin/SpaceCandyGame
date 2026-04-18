package com.example.spacecandygame;

public class Engineer extends CrewMember{

    public Engineer(String id, String name) {
        super(id, name);
        setCrewType(CrewType.ENGINEER);
        this.resilience = 7;
        this.XP = 0;
    }

    //Planting flowers increase engineer xp by 5
    public void plantFlower() {
        SpecialStuff.addFlowers(1);
        addXP(5);
    }

    //Engineer throws a gear at the sour gummy worms.
    //Engineer cannot enter battle before gaining 50 xp
    @Override
    public boolean canEnterBattle() {
        return getXP() >= 50;
    }

    //Required abstract methods
    @Override
    public int onTrainClick(VillainType target) {
        return attack(target);
    }

    @Override
    public int onMissionClick(VillainType target) {
        return attack(target);
    }

    @Override
    public int crewMemberAction(VillainType target) {
        return attack(target);
    }

    //training + battle logic
    public int attack(VillainType target){
        if (getLocation() == Location.TRAINING && getXP() < 50) {
            if (target == VillainType.SOUR_GUMMY_WORM) {
                addXP(3);
            }else {
                takeTrainingDamage();
            }
            return 0;
        }

        if (getLocation() == Location.BATTLE && getXP() >= 50) {
            if (target == VillainType.HARD_CANDY || target == VillainType.SOUR_GUMMY_WORM) {
                return 3;
            } else if (target == VillainType.GUMMY_BEAR) {
                takeBattleDamage();
            }
        }

        return 0;
    }
}









