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
    private static int flowersInField = 0;

    public static void addFlowers(int amount) {
        flowersInField += amount;
    }

    public static int getFlowersInField() {
        return flowersInField;
    }

    public static int getFlowers() {
        return flowersInField;
    }

    public static boolean pickOneFlower() {
        if (flowersInField > 0) {
            flowersInField--;
            return true;
        }
        return false;
    }
}
