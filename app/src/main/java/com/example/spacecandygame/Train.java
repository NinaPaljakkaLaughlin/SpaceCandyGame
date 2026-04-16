package com.example.spacecandygame;
//This file contains the Train class to define training methods

//AI Usage Declaration: ChatGPT AI was used to assist in writing pseudocode for the structure of this file, and for troubleshooting errors
//within the code once written. ChatGPT AI helped to build the structure of the class so that it functioned properly.

public class Train {
    //Constructor
    public void trainCrewMember(CrewMember member, VillainType target) {
        member.setLocation(Location.TRAINING);
        member.crewMemberAction(target);
    }

    private int onTrainClick(CrewMember member, VillainType target) {
        if (target == VillainType.SOUR_GUMMY_WORM) {
            member.addXP(2);
        } else {
            member.takeTrainingDamage();
        }
        return 0; //return crew points- no crew points gained in training arena
    }
}
