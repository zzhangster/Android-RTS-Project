package com.electrofear;

import java.util.ArrayList;

//Object That stores all unit data for parsed XML objects
//Note, some units may have everything filled, some may not

public class BaseUnitParserObject {
    public String objectID;
    public String name;
    public float height;
    public float width;
    public String image;
    
    //Turrent Information, each values based on index!
    public ArrayList turret_id;
    public ArrayList turret_x;
    public ArrayList turret_y;
    public ArrayList turret_image;
    public ArrayList turret_weapon_id; // can have multiple values per index
    public ArrayList turrent_turn_rate;
    
    //Base Weapon
    public ArrayList base_weapon_id; //can have multiple values per index as base weapon
    
    //Logic Parameters
    public String objectType;
    public int speed;
    public int turnRate;
    
    public BaseUnitParserObject(){
        turret_id = new ArrayList();
        turret_x = new ArrayList();
        turret_y = new ArrayList();
        turret_image = new ArrayList();
        turret_weapon_id = new ArrayList();
        turrent_turn_rate = new ArrayList();
    }
}

