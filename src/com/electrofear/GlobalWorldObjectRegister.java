package com.electrofear;

import java.util.ArrayList;


/* Global Object to keep track of all objects in position
 * ======================================================
 * Accepts game objects and environmental objects and others
 * Used in Collision, Explosion Collision, Targeting components
 * since this keeps track of all objects, current divided into 
 * GameObjects (Vehicles and Infantry) and environmental objects
 * (brushes etc)
 * 
 */
public class GlobalWorldObjectRegister {
	private ArrayList<GameObject> worldGameObjectTracker;
	
	
	public GlobalWorldObjectRegister(){
		worldGameObjectTracker = new ArrayList();
	}
	
	public ArrayList<GameObject> getCollisionObjects(GameObject current) {
		ArrayList<GameObject> returnList = new ArrayList();
		GameObject temp;
		for (int i = 0; i < worldGameObjectTracker.size(); i++) {


				
			
		}
		
		return returnList;		
	}
	
	public void registerToGlobalRegistry(GameObject inputObj) {
		worldGameObjectTracker.add(inputObj);
	}
	
	public ArrayList<GameObject> getObjectsInRange(String criteria, GameObject current, float range){
		
		//critiera HAS => ALL => get all objects in range
		//                ALLIED => get only allied units
		//                HOSTILE => get only hostile units in range
		//                
		float currentX,currentY;
		float targetX, targetY;
		
		ArrayList<GameObject> returnList = new ArrayList();
		GameObject temp;
		for (int i = 0; i < worldGameObjectTracker.size(); i++) {
			temp = worldGameObjectTracker.get(i);
			currentX = current.getPosition().x;
			currentY = current.getPosition().y;
			targetX = temp.getPosition().x;
			targetY = temp.getPosition().y;
			if (current != temp){
				
				if (criteria.equalsIgnoreCase("ALL")) {
					if (calculateDistance(currentX, currentY, targetX, targetY) <= range){
						returnList.add(temp);
					}
				} else if (criteria.equalsIgnoreCase("ALLIED") && current.faction.equalsIgnoreCase(temp.faction)) {
					if (calculateDistance(currentX, currentY, targetX, targetY) <= range){
						returnList.add(temp);
					}
				} else if (criteria.equalsIgnoreCase("HOSTILE") && !current.faction.equalsIgnoreCase(temp.faction)) {
					if (calculateDistance(currentX, currentY, targetX, targetY) <= range){
						returnList.add(temp);
					}					
				}
				
			}
		}
		
		return returnList;
	}
	
	private double calculateDistance(float currentX, float currentY, float targetX, float targetY){
		double diffX = (targetX - currentX);
		double diffY = (targetY - currentY);
		
		return Math.sqrt(diffY*diffY + diffX*diffX);
	
	}
}
