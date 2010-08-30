package com.electrofear.parser;

import java.util.ArrayList;

public class GlobalDataVehicle extends GlobalDataUnit {
    public String objectId;
    public String internalId;
    public String name;
    public String ownerName;
    public double speed;
    public double acceleration;
    public double rotateRate;
    public String tracked;
    public double unitHP;
    public double pointCost;
    public String graphicsId;
    
    public ArrayList<GlobalDataUnitSubTurretData> mSubTurretData; //not actual turret data
    
    public GlobalDataVehicle(){
        mSubTurretData = new ArrayList();
    }
    
    public void addTurret(GlobalDataUnitSubTurretData inputTurret){
        mSubTurretData.add(inputTurret);
    }
}
