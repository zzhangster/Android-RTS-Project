package com.electrofear;

import com.electrofear.parser.GlobalDataVehicle;



public class MovementComponent extends BaseObject {
    private float currentSpeed, acceleration;
    private float maxSpeed;

    public MovementComponent(double mSpeed, double mAcceleration) {
       maxSpeed = (float) mSpeed;
       acceleration = (float) mAcceleration;
       currentSpeed = 0f;
    }
    
    @Override
    public void reset() {
        
    }
    
    @Override
    public void update(float timeDelta, BaseObject parent) {
        GameObject object = (GameObject) parent;
        Vector2 currentPosition = object.getPosition();
        float facingAngle = object.getAngle();
        

        
        if ( !((GameObject) parent).findTargetingState() ){
            //IF TARGET NOT FOUND, STOP
            accelerateSpeed(timeDelta);
        } else {
            decelerateSpeed(timeDelta);
        }
        
        
        float deltaX = (float) Math.cos(Math.toRadians(facingAngle)) * currentSpeed;
        float deltaY = (float) Math.sin(Math.toRadians(facingAngle)) * currentSpeed;
        object.setPosition(currentPosition.x + deltaX * timeDelta, currentPosition.y + deltaY * timeDelta);        
        
    }
    
    private void accelerateSpeed(float inputTimeDelta) {
        if (currentSpeed < maxSpeed) {
            currentSpeed = currentSpeed + acceleration * inputTimeDelta;
        } else {
            currentSpeed = maxSpeed;
        }        
    }
    
    private void decelerateSpeed(float inputTimeDelta) {
        if (currentSpeed > 0f) {
            currentSpeed = currentSpeed - acceleration * inputTimeDelta;
        } else {
            currentSpeed = 0;
        }        
    }    
	
}
