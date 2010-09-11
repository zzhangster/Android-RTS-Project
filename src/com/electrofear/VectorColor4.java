package com.electrofear;

public class VectorColor4 {
    public float red;
    public float green;
    public float blue;
    public float alpha;


	public final void set(float inputRed, float inputGreen, float inputBlue, float inputAlpha) {
        red = inputRed;
        green = inputGreen;
        blue = inputBlue;
        alpha = inputAlpha;
    }
    
	public VectorColor4(float inputRed, float inputGreen, float inputBlue, float inputAlpha) {
        red = inputRed;
        green = inputGreen;
        blue = inputBlue;
        alpha = inputAlpha;
    }
	
	public VectorColor4(){
		red = 0f;
		green = 0f;
		blue = 0f;
		alpha = 0f;
	}
    
}
