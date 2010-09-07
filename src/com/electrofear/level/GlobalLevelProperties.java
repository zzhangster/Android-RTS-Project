package com.electrofear.level;

import java.util.ArrayList;

import com.electrofear.parser.GlobalTerrainObj;

//HOLDS LEVEL PROPERTIES and list of items to be displayed on the level!

public class GlobalLevelProperties {
	public float mapWidth,mapHeight;
	public String mapBackGroundImage;
	public String mapName;
	public ArrayList<GlobalTerrainObj> terrainObjects;
	public float cameraPositionX, cameraPositionY, cameraPositionZ;
	public float lightingAngle, lightingIntesity, lightingShadowLength;
	public float windAngle, windIntesity;
	public String weatherType;
	public float weatherIntensity;
	
	public GlobalLevelProperties() {
		terrainObjects = new ArrayList();
	}
	
	public void addTerrainObject(GlobalTerrainObj inputObj) {
		terrainObjects.add(inputObj);
	}
}
