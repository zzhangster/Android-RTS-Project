package com.electrofear;

public class Vector3 {
    public float x;
    public float y;
    public float z;



	public final void set(float xValue, float yValue, float zValue) {
        x = xValue;
        y = yValue;
        z = zValue;
    }
    
	public Vector3(float xValue, float yValue, float zValue) {
        x = xValue;
        y = yValue;
        z = zValue;
    }
	
	public Vector3(){
		x = 0f;
		y = 0f;
		z = 0f;
	}
    
}
