package com.example.spacecandygame;

public class SpecialStuff {
    private static int flowers = 0;
    public static void addFlower() {
        flowers++;
    }
    public static int getFlowers() {
        return flowers;
    }
    public static void useFlowers(int amount) {
        flowers -= amount;
    }
}
