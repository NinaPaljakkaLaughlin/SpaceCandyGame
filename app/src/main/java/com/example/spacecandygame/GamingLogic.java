package com.example.spacecandygame;
//This file contains the GamingLogic class to define what happens during game play for the train and battle
//classes (clicking on the threats causes a call to the training and battle classes to handle point management
//according to what was clicked)

//AI Usage Declaration: ChatGPT AI was used to assist in writing pseudocode for the structure of this file, and for troubleshooting errors
//within the code once written.ChatGPT AI helped to build the structure of the class so that it functioned properly.

public class GamingLogic {
    public int onClick(CrewMember member, VillainType target) {
        if (member.getLocation() == Location.TRAINING) {
            return member.onTrainClick(target);
        }
        if (member.getLocation() == Location.BATTLE) {
            return member.onMissionClick(target);
        }
        return 0;
    }
}
