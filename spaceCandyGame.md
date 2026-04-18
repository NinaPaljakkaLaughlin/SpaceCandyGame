SPACE CANDY GAME DOCUMENTATION
******************************************************************************************************
Group Name: NOP

Group Members: Nina Paljakka-Laughlin, Özde Taştan, (Satarupa Barua)
******************************************************************************************************
- Project Description: CandyWorld Battle
  
  *
  
  CandyWorld Battle is a vibrant game where players explore a sugary world filled with:
  
  Strategic character gameplay for maximizing team points

  Training and battles with sweet enemies

  You and your colleagues are exploring a new planet: CandyWorld. You discovered that there are evil sour gummy worms roaming the     land, as well as sweet hard candies and gummy bears. Your team sets up a research facility to help clear the planet of the evil     sour gummy worms.

  You must create characters, train them to gain XP, and take them into battle to gain crew points and level up.

✨ Characters: Scientist, Engineer, Dragon, Doctor, Soldier ✨

✨ Game play: Train your scientist, engineer, and soldier characters to increase XP to 50 to be able to enter battle, and train   your dragon to increase XP to 60 to be able to enter battle. Once you can enter battle, take two crew members to battle the       candies and  gain crew points. The number of missions your crew completes will increase the difficulty of the training and      missions. Your energy is impacted by attacking the wrong candies in battle, so be careful! If you run out of energy in battle, you can train the doctor and heal other characters in the MedBay using the XP that the doctor gains in training. The doctor does not go to battle. The engineer knows how to plant special flowers on this planet, and gains XP each time she plants them. The scientist learned how to pick the flowers from the flower field and create a potion, which she must use to attack the enemies in training and battle. The dragon is able to train and battle both hard candies and sour gummy worms once she reaches 60 XP or greater. 

🏆 Track your team and individual crew member statistics from the statistics page found on the top right of the homes screen.

  *
  
******************************************************************************************************
- Class Diagram: (source)
******************************************************************************************************

- Divison of Work
  
  *
  
    Project Plan Documentation:
  
    - Implementation Plan: written by Nina
    - Features: written by Özde
    - User Interface Description: written by Satarupa
    - Plan UML: created by Özde, Nina, and Satarupa
 
  Backend Code

      written by Nina
    - CrewMember
    - CrewMemberAdapter
    - CrewStatsFragment
    - CrewType
    - Dragon
    - GameTracker
    - GamingLogic
    - Location
    - Mission
    - Scientist
    - Threat
    - Train
    - TrainingFragment
    - VillainType

      written by Özde
    - Doctor (Satarupa)
    - DoctorHouseFragment
    - DragonHouseFragment
    - Engineer
    - EngineerHouseFragment
    - FlowerField
    - MainActivity
    - MedbayFragment
    - RulesFragment
    - ScientistHouseFragment
    - Soldier
    - SoldierHouseFragment
    - SpecialStuff
      
      written in collaboration
    - HomeFragment
    - RecruitFragment
      
  Frontend Code
  
      written by Nina
    - fragment_crew_stats
    - fragment_training
 
      written by Özde
    - activity_main
    - fragment_doctor_house
    - fragment_dragon_house
    - fragment_engineer_house
    - fragment_medbay
    - fragment_rules
    - fragment_scientist_house
    - fragment_soldier_house
    - item_crew_member
      
      written in collaboration
    - fragment_home
    - fragment_recruit
 
  *
  
******************************************************************************************************
- Implemented Features
*

  Required Features

  - User can **create crew members**
  - Crew members **default location is the housing Quarters** when they are created
  - User can **move crew members to training or battle**
  - When the **crew members are trained they gain experience points** (XP)
  - **Experience points (XP) affect mission performance** (skill power increases as XP increases)
  - **Experience points start at 0**
  - **Energy starts at 12 (max energy)**
  - **Hashmap** is used to store crew member IDs and names
  - There are **buttons to navigate to different locations and create (recruit) new members**
  - When recruiting new crew members, there is **input for naming the crew member**
  - When recruiting new crew members, there is a **dropdown to select crew type and stats are shown         when crew types are clicked through**
  - When recruiting new crew members, there is a **create button and a cancel button**
  - When homes are clicked on, the **user can view the crew members of that crew type, and select the       crew members to view their stats**
  - From the homes screen, crew members can be selected and there are **buttons to go to training or        battle**
  - In training, there is a **start training button and an end-of-training summary** which displays XP      gained, XP lost, current skill power, and total training sessions for the crew member.
  - 
 
  Bonus Features

  - **Implemented a statistics page** for user to view crew member individual missions completed,           trainings completed, XP, and energy, as well as total crew missions completed, total crew points        gained in battle, number of crew members, and number of each type of crew member
  - **Recycler view used** to list crew member individual details in the statistics page
  - **Tracking for how crew members are performing** (number of missions, number of training sessions)
  - **Randomness** used for threat spawning
  - **Specializations** get bonuses on missions (engineer gets +2 skill power when planting flowers)
  - **Fragments** used in the application
  - **Implementation of your own feature** (engineer flower field and flower planting, scientist flower     picking and potion making)
  -  
  
*
******************************************************************************************************

