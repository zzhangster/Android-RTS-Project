package com.electrofear.parser;

import com.electrofear.Vector3;

public class GlobalTerrainObj {
	public float positionX,positionY,positionZ,angle,shadowMultipler;
	public String objType;
	
	public float getShadowMultipler(){
		return shadowMultipler;
	}
	
	public Vector3 getPositions(){
		return new Vector3(positionX, positionY, positionZ);
	}
	
	public float getAngle(){
		return angle;
	}
	
	public String getObjType(){
		return objType;
	}
}
