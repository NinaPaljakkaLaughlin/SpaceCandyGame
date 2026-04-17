package com.example.spacecandygame;

import java.util.*;
//This file contains the CrewMember parent class to define attributes and methods for all crew member types

//AI Usage Declaration: ChatGPT AI was used to assist in writing pseudocode for the structure of this file, and for troubleshooting errors
//within the code once written. No code has been written by AI or copy-pasted from an AI source.
//ChatGPT was used to solve merge conflicts
public abstract class CrewMember {
    //Attributes
    private String id; //get ID, ID set when crew member is made
    private String name; //get name, name set when crew member is made
    private String color; //get color, color set when crew member is made
    protected CrewType crewType; //defines the type of crew member, need getter and setter
    protected int XP; //get XP, damage in training method manages decreasing XP, add XP in training method manages increasing XP
    protected int energy; //get energy, damage in battle method manages decreasing energy, heal method manages increasing energy from doctor
    protected int maxEnergy; //no need for getter or setter, set in CrewMember constructor
    protected int resilience; //get resilience, values hardcoded in each crew members classes
    protected Location location; //get location, set location
    protected int missionsCompleted; //track number of missions each character has completed

    public abstract int onTrainClick(VillainType target);
    public abstract int onMissionClick(VillainType target);

    //Constructor
    public CrewMember(String id, String name) {
        this.id = id; //set member id
        this.name = name; //set member name
        this.maxEnergy = 12; //set max energy to 12
        this.energy = maxEnergy; //set energy to maxEnergy for start of game
        this.resilience = 0;
        this.location = Location.QUARTERS;
        this.XP = 0;
        this.missionsCompleted = 0;
        this.color = "";
    }

    //Method for getting crew member id
    public String getId() {
        return id;
    }

    //Method for getting crew member name
    public String getName() {
        return name;
    }

    //Method for getting color
    public String getColor() {
        return color;
    }

    //Method for getting crew type
    public CrewType getCrewType() {
        return crewType;
    }

    //Method for setting crew type
    public void setCrewType(CrewType type) {
        this.crewType = type;
    }

    //Method for setting color
    public void setColor(String color) {
        this.color = color;
    }

    //Method for getting XP
    public int getXP() {
        return XP;
    }

    //Method for getting resilience
    public int getResilience() {
        return resilience;
    }

    //Method for taking damage in training (training damage decreases XP)
    //Method for getting the amount of damage (note: XP can go negative)
    public int getDamageAmount() {
        int damage;
        if (resilience == 10) {
            damage = 2;
        } else if (resilience == 8) {
            damage = 3;
        } else {
            damage = 4;
        }
        return damage;
    }

    public void takeTrainingDamage() {
        XP -= getDamageAmount();
    }

    //Method for adding XP in training
    public void addXP(int amount) {
        XP += amount;
    }

    //Method for getting energy
    public int getEnergy() {
        return energy;
    }

    //Method for taking damage in battle (battle damage decreases energy, energy cannot go negative)
    public void takeBattleDamage() {
        energy -= getDamageAmount();
        if (energy < 0) {
            energy = 0;
        }
    }

    //Method for boolean on if energy is too low
    public boolean hasNoEnergy() {
        return energy <= 0;
    }

    //Method for healing energy by the doctor (energy can only be increased from going to the medbay for healing by the doctor)
    public void healed() {
        this.energy = this.maxEnergy;
    }

    //Method for character ability to enter battle
    public boolean canEnterBattle() {
        return XP >= 50;
    }

    //Method for getting character location
    public Location getLocation() {
        return location;
    }

    //Method found counting missions
    private void countMissions() {
        missionsCompleted++;
    }

    //Method for getting the number of completed missions
    public int getMissionsCompleted() {
        return missionsCompleted;
    }

    //Method for setting character location
    public void setLocation(Location newLocation) {
        if (newLocation == Location.BATTLE && this.location != Location.BATTLE) {
            countMissions();
        }
        if (newLocation == Location.QUARTERS) {
            energy = maxEnergy;
        }
        this.location = newLocation;
    }

    //Method for moving characters to different locations
    public void moveCrewMember(Location newLocation) {
        this.location = newLocation;
    }

    //Method for acting during gameplay, training arena and battle arena
    public abstract int crewMemberAction(VillainType target);
}


