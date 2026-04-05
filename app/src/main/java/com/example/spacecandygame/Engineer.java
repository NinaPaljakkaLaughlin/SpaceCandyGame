package com.example.spacecandygame;

public class Engineer extends CrewMember{
    public  int Sour = 1;
    public  int Normal = 2;

    public Engineer(String name, String color) {
        //name, specialization,skill,resiliance, experience,max energy
        super(name, "Engineer", 8, 10, 12, color);
    }

    public void train(int gummyType) {
        if (gummyType == Sour) {
            gainExperience(2);
        }else if(gummyType ==Normal) {
            gainExperience(1);
        }else {gainExperience(-3);
        }
    }
    public void plantFlowers() {
        gainExperience(5);
    }

}
