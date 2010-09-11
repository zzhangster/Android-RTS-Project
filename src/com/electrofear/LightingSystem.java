package com.electrofear;

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
	
	public LightingSystem(){
		red = 255;
		blue = 255;
		green = 255;
	}
	
	
	//Update equation
	
	
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
    	
        
    }	
}
