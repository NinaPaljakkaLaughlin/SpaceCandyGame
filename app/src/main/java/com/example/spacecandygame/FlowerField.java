package com.example.spacecandygame;
//This file contains the FlowerField class to define attributes and methods for the flower field and using the flowers

//AI Usage Declaration: ChatGPT AI was used to assist in writing pseudocode for the structure of this file, and for troubleshooting errors
//within the code once written. No code has been written by AI or copy-pasted from an AI source.

public class FlowerField {

    //Method for the engineer to plant flowers
    public void plantFlowers(CrewMember member) {
        if (member instanceof Engineer) {
            ((Engineer) member).plantFlowers();
        }
    }
    //Method for the scientist to pick flowers
    public void pickFlowers(CrewMember member) {
        if (member instanceof Scientist) {
            ((Scientist) member).pickFlowers();
        }
    }

}

