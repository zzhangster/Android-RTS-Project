package com.electrofear;

//USED TO KEEP TRACK DAY/NIGHT CYCLE
//EACH NODE Represent a time duration with a starting color and ending color

public class LightingNode {
	public float durationRatioStart,durationRatioEnd;
	public VectorColor4 startingColorVector,endingColorVector;
	
	public LightingNode(float inputRatioStart, 
						float inputRatioEnd,
						VectorColor4 inputColorStartVector,
						VectorColor4 inputColorEndVector) {
		durationRatioStart = inputRatioStart;
		durationRatioEnd = inputRatioEnd;
		startingColorVector = inputColorStartVector;
		endingColorVector = inputColorEndVector;
	}
	
}
