package com.electrofear;

import javax.microedition.khronos.opengles.GL10;

public class DrawableObject extends BaseObject{

    
    public float shadowDifference;
    public float zAxis;

	public boolean visibleAtPosition(Vector2 mScreenLocation) {
        // TODO Auto-generated method stub
        return false;
    }

    public Object getParentPool() {
        // TODO Auto-generated method stub
        return null;
    }

    public void updateCoords(float calculatedX, float calculatedY,
            float width, float height, float angle, float zAxis) {
        // TODO Auto-generated method stub

    }

	public float getShadowDifference() {
		// TODO Auto-generated method stub
		return 0;
	}

    public void startDrawing(GL10 gl) {
        // TODO Auto-generated method stub
        int testing = 0;
        testing++;
        
    }

}
