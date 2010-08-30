package com.electrofear.parser;

import java.util.ArrayList;

public class GlobalDataTurret {
    public String turretId;
    public String graphicId;
    public Double rotateRate;
    public ArrayList<String> weaponDataString;
    
    public GlobalDataTurret() {
    	weaponDataString = new ArrayList();
    }
}
