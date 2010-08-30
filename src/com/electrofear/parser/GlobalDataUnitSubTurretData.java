package com.electrofear.parser;

import java.util.ArrayList;

public class GlobalDataUnitSubTurretData {
    
    public String internalId;
    public String turretId;
    public String relativeToId;
    public double turretOffSetX;
    public double turretOffSetY;
    public ArrayList<String> weapon;
    
    
    public GlobalDataUnitSubTurretData(){
    	weapon = new ArrayList();
    }

}
