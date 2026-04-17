package com.example.spacecandygame;
import java.util.*;

//This file contains the GameTracker class to track total crew points for points gained in battle, number
//of missions completed, hold the crew list

//AI Usage Declaration: ChatGPT AI was used to assist in writing pseudocode for the structure of this file, and for troubleshooting errors
//within the code once written.ChatGPT AI helped to build the structure of the class so that it functioned properly.
//ChatGPT was used to solve merge conflicts
public class GameTracker {
    private List<CrewMember> crewList;
    private List<CrewMember> battleMembers = new ArrayList<>(); //two crew members go to battle at a time
    private HashMap<String, CrewMember> crewMap = new HashMap<>();
    private int crewPoints;
    private int totalMissions; //track total missions for display

    //constructor
    public GameTracker(List<CrewMember> crewList) {
        this.crewList = (crewList != null) ? crewList : new ArrayList<>();
        this.crewPoints = 0;
    }

    //Method to select which members will go to battle arena
    public void setBattleMembers(CrewMember crewMember) {
        if (battleMembers.size() < 2) {
            battleMembers.add(crewMember);
        }
    }

    //Method to create dragon - add to hashmap and crew list
    public void createDragon(String id, String name) {
        CrewMember dragon = new Dragon(id, name);
        crewList.add(dragon);
        crewMap.put(dragon.getId(), dragon);
    }

    //Method to create engineer - add to hashmap and crew list
    public void createEngineer(String id, String name) {
        CrewMember engineer = new Engineer(id, name);
        crewList.add(engineer);
        crewMap.put(engineer.getId(), engineer);
    }

    //Method to create doctor - add to hashmap and crew list
    public void createDoctor(String id, String name) {
        CrewMember doctor = new Doctor(id, name);
        crewList.add(doctor);
        crewMap.put(doctor.getId(), doctor);
    }

    //Method to create scientist - add to hashmap and crew list
    public void createScientist(String id, String name) {
        CrewMember scientist = new Scientist(id, name);
        crewList.add(scientist);
        crewMap.put(scientist.getId(), scientist);
    }

    //Method to create soldier - add to hashmap and crew list
    public void createSoldier(String id, String name) {
        CrewMember soldier = new Soldier(id, name);
        crewList.add(soldier);
        crewMap.put(soldier.getId(), soldier);
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

    //getter so house fragments can read the created crew
    public List<CrewMember> getCrewList() {
        return crewList;
    }

    //Method to get a crew member by id
    public CrewMember getCrewMemberById(String id) {
        return crewMap.get(id);
    }

    //Method to control mission turns - user should select players from a list,
    // then there should be one round with one crew member and the next round with the other
    public void missionTurn(Threat threat) {
        //choose 2 crew members
        if (battleMembers.size() < 2) return;

        CrewMember crew1 = battleMembers.get(0);
        CrewMember crew2 = battleMembers.get(1);
        Mission mission = new Mission();
        mission.startMission(crew1, crew2, threat);

        totalMissions++;
    }

    //Method for handling random enemy amounts for training and battle
    public List<VillainType> generateVillains(int count) {
        List<VillainType> list = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            double rand = Math.random();

            if (rand < 0.6) list.add(VillainType.SOUR_GUMMY_WORM);
            else if (rand < 0.85) list.add(VillainType.HARD_CANDY);
            else list.add(VillainType.GUMMY_BEAR);
        }

        return list;
    }
}
