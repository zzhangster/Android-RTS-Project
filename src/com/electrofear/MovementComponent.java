package com.electrofear;



public class MovementComponent extends BaseObject {



    public MovementComponent() {

    }
    
    @Override
    public void reset() {
        
    }
    
    @Override
    public void update(float timeDelta, BaseObject parent) {
        GameObject object = (GameObject) parent;
        Vector2 currentPosition = object.getPosition();
        
        if (object.TOBEREMOVED){
        	object.setPosition(currentPosition.x, currentPosition.y + 15.0f * timeDelta);
        }
        
    }
	
}
