package com.example.spacecandygame;

public class Doctor extends CrewMember {
    public Doctor(String id, String name) {
        super(id, name);
        setCrewType(CrewType.DOCTOR);
        this.resilience = 10;
        this.XP = 0;
    }
    //the doctor can't enter battle
    @Override
    public boolean canEnterBattle() {
        return false;
    }
    // training
    public void train(GummyType gummy) {
        if (gummy == GummyType.SOUR_WORM) {
            addXP(5);
        } else {
            addXP(-3);
        }
    }
        //doctor can heal crewmember
        //doctor must have enough XP to heal
        //the target must be alive to be healed
        public void heal (CrewMember target){
            if (target != null && target.isAlive() && getXP() >= 50) {
                target.healToFull();
            }

        }
}
// the doctor doesn't take any damages
// the doctor looses life points