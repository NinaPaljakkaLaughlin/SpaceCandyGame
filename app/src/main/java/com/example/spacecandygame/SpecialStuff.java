package com.example.spacecandygame;

/*
This file contains the Special Stuff class to define attributes and methods for the flower field, such
as adding flowers to the field, counting the planted flowers, and picking the flowers from the field.

AI Usage Declaration: ChatGPT AI was used to assist in writing pseudocode for the structure of this file, and for troubleshooting errors
within the code once written.
AI was used to generate the background image.

ChatGPT was used to solve merge conflicts
*/

public class SpecialStuff {

    //default flowers in field 0
    private static int flowersInField = 0;

    //Method for adding flowers to the field
    public static void addFlowers(int amount) {
        flowersInField += amount;
    }

    //Method for getting number of flowers in the field
    public static int getFlowersInField() {
        return flowersInField;
    }

    //Method for getting number of flowers in the field for UI display
    public static int getFlowers() {
        return flowersInField;
    }

    //Method for picking flowers from the field
    public static boolean pickOneFlower() {
        if (flowersInField > 0) {
            flowersInField--;
            return true;
        }
        return false;
    }
}
