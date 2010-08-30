package com.electrofear.misc;

import java.util.HashMap;


public class World {
	private WorldLogic myWorldLogic;
	private WorldRender myWorldRender;
	
	private HashMap myObjectList = new HashMap();
	
	public void addObject(int inputHeight, Object InputObject){
		myObjectList.put(inputHeight, InputObject);
	}
	
	
	
}
