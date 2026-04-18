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

******************************************************************************************************

