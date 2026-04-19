package com.example.spacecandygame;
/*
This file contains the CrewMember parent class to define attributes and methods for all crew member types

AI Usage Declaration: ChatGPT AI was used to assist in writing pseudocode for the structure of this file, and for troubleshooting errors
within the code once written. No code has been copy-pasted directly from an AI source.

ChatGPT was used to solve some merge conflicts
 */

import java.util.*;

public abstract class CrewMember {
    //Attributes
    private String id; //get ID, ID set when crew member is made
    private String name; //get name, name set when crew member is made
    private String color; //get color, color set when crew member is made
    protected CrewType crewType; //defines the type of crew member
    protected int XP; //XP = experience points, gained in training and required to battle (or heal for doctor)
    protected int energy; //damage in battle decreases energy
    protected int maxEnergy; //maxEnergy for default and healing by doctor
    protected int resilience; //resilience, hardcoded in each crew members classes and impacts damage level
    protected Location location; //location of the crew member in the game
    protected int missionsCompleted; //track number of missions each character has completed
    protected int trainingSessions; //track how many training sessions each character has done
    protected int skillPower; //skill power of the crew member, increases with XP, starts at 1

    public abstract int onTrainClick(VillainType target);
    public abstract int onMissionClick(VillainType target);

    //Constructor
    public CrewMember(String id, String name) {
        this.id = id; //set member id
        this.name = name; //set member name
        this.maxEnergy = 12; //set max energy to 12
        this.energy = maxEnergy; //set energy to maxEnergy for start of game
        this.resilience = 0; //redefined per crew member type
        this.location = Location.QUARTERS; //default location quarters (exception- doctor)
        this.XP = 0; //XP starts a 0
        this.missionsCompleted = 0; //missions completed starts at 0
        this.trainingSessions = 0; //training sessions completed starts at 0
        this.skillPower = 1; //skill power starts at 1
        this.color = ""; //color default is empty
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

        // Doctor belongs in Medbay, other crew members start in Quarters
        if (type == CrewType.DOCTOR) {
            this.location = Location.MEDBAY;
        } else {
            this.location = Location.QUARTERS;
        }
    }

    //Method for setting color
    public void setColor(String color) {
        this.color = color;
    }

    //Method for getting XP
    public int getXP() {
        return XP;
    }

    //Method for getting the amount of damage- impacted by resilience (note: XP can go negative)
    public int getDamageAmount() {
        int damage;
        if (resilience == 10) {
            damage = 1;
        } else if (resilience == 8) {
            damage = 2;
        } else {
            damage = 3;
        }
        return damage;
    }

    //Method for taking damage in training (training damage decreases XP)
    public void takeTrainingDamage() {
        XP -= getDamageAmount();
        updateSkillPower();
    }

    //Method for adding XP in training - adds skill power
    public void addXP(int amount) {
        XP += amount;
        updateSkillPower();
    }

    //Method for updating skill power
    private void updateSkillPower() {
        // Skill power increases by 1 for every 50 XP gained.
        if (XP < 0) {
            skillPower = 1;
        } else {
            skillPower = 1 + (XP / 50);
        }
    }

    public int getSkillPower() {
        return skillPower;
    }

    //Method for getting energy
    public int getEnergy() {
        return energy;
    }

    //Method for taking damage in battle (battle damage decreases energy, energy cannot go negative)
    public void takeBattleDamage() {
        energy -= getDamageAmount();
        if (energy <= 0) {
            energy = 0;
            setLocation(Location.MEDBAY);
        }
    }

    //Method for boolean on if energy is too low
    public boolean hasNoEnergy() {
        return energy <= 0;
    }

    //Method for healing energy by the doctor (energy can only be increased from going to the medbay for healing by the doctor)
    public void healed() {
        this.energy = this.maxEnergy;
        setLocation(Location.QUARTERS);
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

    //Method for incrementing training sessions
    public void incrementTrainingSessions() {
        trainingSessions++;
    }

    //Method for getting the number of training sessions
    public int getTrainingSessions() {
        return trainingSessions;
    }

    //Method for setting character location
    public void setLocation(Location newLocation) {
        if (newLocation == Location.BATTLE && this.location != Location.BATTLE) {
            countMissions();
        }

        // Only restore energy in quarters if the crew member still has energy
        if (newLocation == Location.QUARTERS && energy > 0) {
            energy = maxEnergy;
        }

        this.location = newLocation;
    }

    //Method for acting during gameplay, training arena and battle arena
    public abstract int crewMemberAction(VillainType target);
}