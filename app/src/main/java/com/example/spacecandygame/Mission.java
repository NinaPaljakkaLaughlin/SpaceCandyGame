package com.example.spacecandygame;

//This file contains the Mission class to define mission attributes and methods

//AI Usage Declaration: ChatGPT AI was used to assist in writing pseudocode for the structure of this file, and for troubleshooting errors
//within the code once written.ChatGPT AI helped to build the structure of the class so that it functioned properly.
//added damage to the threat
public class Mission {
    private CrewMember member1;
    private CrewMember member2;

    public void startMission(CrewMember member1, CrewMember member2, Threat threat) {

        //if member cannot enter battle based on their total XP:
        if (!member1.canEnterBattle() || !member2.canEnterBattle()) {
            System.out.println("CrewMember does not have enough XP to enter battle");
            return;
        }

        //otherwise enter mission battle
        member1.setLocation(Location.BATTLE);
        member2.setLocation(Location.BATTLE);

        while (!threat.missionComplete() && (member1.getEnergy() > 0 || member2.getEnergy() > 0)) {

            //member 1 turn
            if (member1.getEnergy() > 0) {
                member1.crewMemberAction(randomTarget());
                threat.threatDamage();
            }

            //member 2 turn
            if (member2.getEnergy() > 0) {
                member2.crewMemberAction(randomTarget());
                threat.threatDamage();
            }
        }

        //send back to quarters after the mission
        member1.setLocation(Location.QUARTERS);
        member2.setLocation(Location.QUARTERS);
    }

    //Method to randomize the type of villain that comes on screen
    private VillainType randomTarget() {
        VillainType[] values = VillainType.values();
        int index = (int)(Math.random() * values.length);
        return values[index];
    }

    // Change to public and static so MissionFragment can use it
    public static int onMissionClick(CrewMember member, VillainType target) {
        if (!member.canEnterBattle()) return 0;

        int points = 0;
        if (target == VillainType.SOUR_GUMMY_WORM) {
            points = 2;
        } else {
            member.takeBattleDamage();
        }
        return points;
    }
}
