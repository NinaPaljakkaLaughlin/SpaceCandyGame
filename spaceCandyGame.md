******************************************************************************************************

# 🍬 SPACE CANDY GAME DOCUMENTATION 🚀

---

## 👥 Group Information

**Group Name:** NOP

**Members:**

* Nina Paljakka-Laughlin
* Özde Taştan
* Satarupa Barua

---

## 🎮 Project Description — *CandyWorld Battle*


CandyWorld Battle is a vibrant game where players explore a sugary world filled with strategic character gameplay for maximizing team points, and training and battles with sweet enemies.

  You and your colleagues are exploring a new planet: CandyWorld. You discovered that there are evil sour gummy worms roaming the     land, as well as sweet hard candies and gummy bears. Your team sets up a research facility to help clear the planet of the evil     sour gummy worms.

  You must create characters, train them to gain XP, and take them into battle to gain crew points and level up.

✨ Game play: Train your scientist, engineer, and soldier characters to increase XP to 50 to be able to enter battle, and train   your dragon to increase XP to 60 to be able to enter battle. Once you can enter battle, take two crew members to battle the       candies and  gain crew points. The number of missions your crew completes will increase the difficulty of the training and      missions. Your energy is impacted by attacking the wrong candies in battle, so be careful! If you run out of energy in battle, you can train the doctor and heal other characters in the MedBay using the XP that the doctor gains in training. The doctor does not go to battle. The engineer knows how to plant special flowers on this planet, and gains XP each time she plants them. The scientist learned how to pick the flowers from the flower field and create a potion, which she must use to attack the enemies in training and battle. The dragon is able to train and battle both hard candies and sour gummy worms once she reaches 60 XP or greater. 

🏆 Track your team and individual crew member statistics from the statistics page found on the top right of the homes screen.

## 🧍 Characters

* 🔬 Scientist → Creates potions from flowers for attacks
* 🔧 Engineer → Plants special flowers for XP
* 🐉 Dragon → Battles all enemy types after 60 XP
* 🩺 Doctor → Heals teammates (no combat)
* 🪖 Soldier → Combat-focused unit

---

## 🧩 Class Diagram

*(Add source or image here)*

---

## 🛠️ Division of Work

### 📄 Documentation

* Implementation Plan → Nina
* Features → Özde
* UI Description → Satarupa
* UML Plan → Nina, Özde, Satarupa

---

### ⚙️ Backend Code

**Nina**

* CrewMember
* CrewMemberAdapter
* CrewStatsFragment
* CrewType
* Dragon
* GameTracker
* GamingLogic
* Location
* Mission
* MissionFragment
* Scientist
* Threat
* Train
* TrainingFragment
* VillainType

**Özde**

* Doctor *(with Satarupa)*
* DoctorHouseFragment
* DragonHouseFragment
* Engineer
* EngineerHouseFragment
* FlowerField
* FlowerFieldFragment
* MainActivity
* MedbayFragment
* RulesFragment
* ScientistHouseFragment
* Soldier
* SoldierHouseFragment
* SpecialStuff

**Collaboration**

* HomeFragment
* RecruitFragment

---

### 🎨 Frontend Code

**Nina**

* fragment_crew_stats
* fragment_mission
* fragment_training

**Özde**

* activity_main
* fragment_doctor_house
* fragment_dragon_house
* fragment_engineer_house
* fragment_flower_field
* fragment_medbay
* fragment_rules
* fragment_scientist_house
* fragment_soldier_house
* item_crew_member

**Collaboration**

* fragment_home
* fragment_recruit

---

## ✅ Implemented Features

### 🔹 Required Features

* Create crew members
* Default location: **Housing Quarters**
    * Doctor default: **MedBay**
* Move crew between training and battle
* Training increases **XP**
* XP affects **mission performance**
* XP starts at **0**
* Energy starts at **12 (max)**
* Uses **HashMap** for crew information storage - used in statistics view
* Navigation buttons for locations
* Recruit system includes:
  * Name input
  * Crew type dropdown
  * Stats preview
  * Create & cancel buttons
* Homes screen:
  * Crew housing organized by crew type
  * Crew members can be selected at home to go to training or battle
  * Crew members can be selected at home to view current stats
* Training includes:
  * Start button
  * If threat is defeated (gummy worms) crew members earn XP
    * When 50 XP is reached, the crew member can go to battle (for dragon type: 60 XP)
  * If the hard candies are attacked in training, the crew member loses XP
  * End summary (XP gained/lost, skill power, training sessions)
  * Back to Quarters button
* Battle includes:
  * Selection of two crew members for each battle
    * Missions are turn-based
    * Mission continues until threat is neutralized
    * If threat is defeated (gummy worms) crew members earn crew points
    * When crew member energy drains to 0 or below, their energy can be revived by the doctor
      * The doctor must have XP from training to heal crew members
      * If a crew member returns to quarters after battle with energy above 0, their energy is restored
  * Start Battle button
  * Number of missions for each crew member is tracked and scales the number of generated threats
  * End summary ()
---

### 🌟 Bonus Features

* 📊 **Statistics Page**

  * Individual stats (missions, training, XP, energy)
  * Team stats (total missions,crew points, crew count)

* 📋 **RecyclerView**

  * Displays crew member details in statistics page

* 📈 **Performance Tracking**

  * Missions completed
  * Training sessions

* 📷 **Unique Images for Crew Types**

  * When creating crew members, image of the crew member is populated and the color of the character can be chosen, the image color updates based on the user choice
    
* 🎬 **Visual Effects**
  * Training
    * Animations - gummy worms and hard candies move across the screen
    * Progress Bar - tracks live increase in crew member XP during training
  * Battle
    * Animations - gummy worms and hard candies move across the screen
    * Progress Bar - tracks live increase in crew points gained during battle
  *Statistics
    * Progress Bar - visual display of accumulated crew points gained during battles

* 🎭 **Crew Member Performance Tracking**
  * Number of training sessions
  * Number of battles

* 🩺 **MedBay for Energy Healing**
  * When a crew member's energy drops to or below 0 during battle they can be sent to the MedBay and healed to full energy by the doctor
      * The doctor must have XP from training to have healing abilities

* 🎲 **Randomization Implementation**
  * Math.random() used for:
      * randomizing the type of threats generated for battles (ratio)
      * randomizing the number of threats per battle (based on calculation with number of missions completed by the crew -> more missions = higher difficulty in battle)
      * randomizing the number of threats per training session
      * randomizing true/false boolean value to determine what threat type will be calculated next
      * randomizing the location on the training and battle views where the threats will enter the screen from
      * randomizing the speed at which the threats enter training and battle, and the amount of time between threats entering the screen
  
* ⚡ **Specializations for Battle Advantage**
  * Engineer gets +5 XP for planting flowers
  * Skill Power increases as crew member trains: 50 XP = +1 Skill Power
  * Crew Member Resilience impacts damage during training (-XP) and battle (-energy)
        
* 🧱 **Fragment-based architecture**
  * Fragments created per unique functionality
    * Applied to all unique locations (home, crew houses, medbay, training arena, battle arena, flower field)
    * Applied to all pages requiring unique functionality (crew statistics page, recruitment page)

* 💡 **Custom Features**
  * Scientist must use potion made from picking flowers to go to training
  * Flower planting can be done by the engineer 
  * Potion crafting can be done by the scientist after picking flowers that the engineer planted 

---

AI Usage Declaration: ChatGPT AI was used to format this page. 

