package com.example.spacecandygame;
import java.util.*;

//This file contains the GameTracker class to track total crew points for points gained in battle, number
//of missions completed, hold the crew list

//AI Usage Declaration: ChatGPT AI was used to assist in writing pseudocode for the structure of this file, and for troubleshooting errors
//within the code once written.ChatGPT AI helped to build the structure of the class so that it functioned properly.
//No code has been written by AI or copy-pasted from an AI source.

public class GameTracker {
    private List<CrewMember> crewList;
    private int crewPoints;
    private int totalMissions;

    //constructor
    public GameTracker(List<CrewMember> crewList) {
        crewList = new ArrayList<>();
        this.crewPoints = 0;
    }
    //Method to create dragon
    public void createDragon(String id, String name) {
        CrewMember dragon = new Dragon(id, name);
        crewList.add(dragon);
    }
    //Method to create engineer
    public void createEngineer(String id, String name) {
        CrewMember engineer = new Engineer(id, name);
        crewList.add(engineer);
    }
    //Method to create doctor
    public void createDoctor(String id, String name) {
        CrewMember doctor = new Doctor(id, name);
        crewList.add(doctor);
    }
    //Method to create scientist
    public void createScientist(String id, String name) {
        CrewMember scientist = new Scientist(id, name);
        crewList.add(scientist);
    }
    //Method to create soldier
    public void createSoldier(String id, String name) {
        CrewMember soldier = new Soldier(id, name);
        crewList.add(soldier);
    }
    //Method to get crew points
    public int getCrewPoints() {
        return crewPoints;
    }
    //Method to set crew points
    public void setCrewPoints(int points) {
        crewPoints += points;
    }
    //Method to track crew missions
    public int getTotalMissions() {
        int total = 0;
        for (CrewMember crewMember : crewList) {
            total += crewMember.getMissionsCompleted();
        }
        return total;
    }
}
