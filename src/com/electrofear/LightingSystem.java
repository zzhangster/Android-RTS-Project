package com.electrofear;

import java.util.ArrayList;

import android.util.Log;

/* LightingSystem
 * ===========================
 * Controls the RBG base color
 * to mimick night/day effect
 * takes input from levelbuilder
 * as to what time it is
 * ===========================
 */

public class LightingSystem extends BaseObject{
	float red;
	float blue;
	float green;
	float alpha;
	VectorColor4 vectorColor = new VectorColor4();
	ArrayList<LightingNode> lightingNodes = new ArrayList();
	
	float totalTimeDuration, currentTime;
	
	public LightingSystem(){
		red = 255;
		blue = 255;
		green = 255;
		totalTimeDuration = 50f;
		currentTime = 0f;
		alpha = 255;
		
		//PRESET VALUES FOR NIGHT DAY CYCLE
		//STRUCTURE AS 
		//PUT DATA IN XML AND PARSE FOR FUTURE REFERENCE
		LightingNode NightFirst = new LightingNode(0f,
													0.15f,
													new VectorColor4(75f,75f,105f, alpha),
													new VectorColor4(75f,75f,105f, alpha));	
		lightingNodes.add(NightFirst);
		
		//////////
		LightingNode NightToDawn = new LightingNode(0.15f,
													0.25f,
													new VectorColor4(75f,75f,105f, alpha),
													new VectorColor4(175f,135f,135, alpha));
		lightingNodes.add(NightToDawn);
		
		//////////
		LightingNode DawnToDay = new LightingNode(0.25f,
												  0.35f,
												  new VectorColor4(175f,135f,135f, alpha),
												  new VectorColor4(255f,255f,255f, alpha));
		
		lightingNodes.add(DawnToDay);
		//////////
		LightingNode FullDay = new LightingNode(0.35f,
												0.65f,
												new VectorColor4(255f,255f,255f, alpha),
												new VectorColor4(255f,255f,255f, alpha));
		
		lightingNodes.add(FullDay);
		
		/////////////
		LightingNode DayToDusk = new LightingNode(0.65f,
													0.7f,
													new VectorColor4(255f,255f,255f, alpha),
													new VectorColor4(255f,215f,215f, alpha));
		lightingNodes.add(DayToDusk);
		
		//////////////
		LightingNode DuskToNightFall = new LightingNode(0.7f,
													0.75f,
													new VectorColor4(255f,215f,215f, alpha),
													new VectorColor4(175f,135f,135f, alpha));	
		lightingNodes.add(DuskToNightFall);	
		
		/////////////
		LightingNode NightFallToNight = new LightingNode(0.75f,
														0.85f,
														new VectorColor4(175f,135f,135f, alpha),
														new VectorColor4(75f,75f,105f, alpha));
		lightingNodes.add(NightFallToNight);
		
		////////////
		LightingNode NightLast = new LightingNode(0.85f,
													1.0f,
													new VectorColor4(75f,75f,105f, alpha),
													new VectorColor4(75f,75f,105f, alpha));	
		lightingNodes.add(NightLast);
	}
	
	public void setCurrentTime(float inputTime){
		currentTime = inputTime;
	}
	
	//Update equation
	public VectorColor4 getCurrentLightingColor() {
		vectorColor.set(red, green, blue, alpha);
		return vectorColor;
	}
	
	
	//Manual Adjustment Of Colors
	public void changeRed(float inputRed){
		if (inputRed + red > 255) {
			red = 255;
		} else if (inputRed + red < 0) {
			red = 0;
		} else {
			red += inputRed;
		}
		Log.v("LightingSystem: ", "Color: " + red + ", " + green + ", " + blue);
	}
	
	public void changeBlue(float inputBlue){
		if (inputBlue + blue > 255) {
			blue = 255;
		} else if (inputBlue + blue < 0) {
			blue = 0;
		} else {
			blue += inputBlue;
		}
		Log.v("LightingSystem: ", "Color: " + red + ", " + green + ", " + blue);
	}
	
	public void changeGreen(float inputGreen){
		if (inputGreen + green > 255) {
			green = 255;
		} else if (inputGreen + green < 0) {
			green = 0;
		} else {
			green += inputGreen;
		}
		Log.v("LightingSystem: ", "Color: " + red + ", " + green + ", " + blue);
	}
	
    public void update(float timeDelta, BaseObject parent) {
    	currentTime = ( currentTime + timeDelta ) % totalTimeDuration;
    	//Add Here to Do Once per frame instead of once per object
    	calculateColorByTime();
    	Log.v("LightingSystem: ", "Color: " + red + ", " + green + ", " + blue);
    }
    
    private void calculateColorByTime(){
        //Duration of Events
    	LightingNode currentNode;
    	float nodeTime, nodeCurrentTimePosition, redDiff, blueDiff, greenDiff, alphaDiff;
    	for (int i = 0; i < lightingNodes.size(); i ++) {
    		currentNode = lightingNodes.get(i);
    		if (currentNode.durationRatioEnd * totalTimeDuration > currentTime) {
    			
    			//Time falls within the duration of this lighting Node
    			nodeTime = currentNode.durationRatioEnd * totalTimeDuration - currentNode.durationRatioStart * totalTimeDuration;
    			nodeCurrentTimePosition = (currentTime - currentNode.durationRatioStart * totalTimeDuration) / nodeTime;
    			
    			redDiff = currentNode.endingColorVector.red - currentNode.startingColorVector.red;
    			greenDiff = currentNode.endingColorVector.green - currentNode.startingColorVector.green;
    			blueDiff = currentNode.endingColorVector.blue - currentNode.startingColorVector.blue;
    			alphaDiff = currentNode.endingColorVector.alpha - currentNode.startingColorVector.alpha;
    			
    			red = currentNode.startingColorVector.red + redDiff * nodeCurrentTimePosition;
    			green = currentNode.startingColorVector.green + greenDiff * nodeCurrentTimePosition;
    			blue = currentNode.startingColorVector.blue + blueDiff * nodeCurrentTimePosition;
    			alpha = currentNode.startingColorVector.alpha + alphaDiff * nodeCurrentTimePosition;
    			
    			break;		
    		}
    	}
    }
}
