package com.example.spacecandygame;

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

    public static void clearFlowers() {
        flowersInField = 0;
    }
}
