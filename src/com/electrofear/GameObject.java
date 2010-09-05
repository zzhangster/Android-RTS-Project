package com.electrofear;


public class GameObject extends ObjectManager {

    private Vector2 mPosition;
    private Vector2 mVelocity;
    private Vector2 mTargetVelocity;
    private Vector2 mAcceleration;
    private Vector2 mImpulse;
    private Vector2 facingDirection;
    private Vector2 mBackgroundCollisionNormal;

    private float mLastTouchedFloorTime;
    private float mLastTouchedCeilingTime;
    private float mLastTouchedLeftWallTime;
    private float mLastTouchedRightWallTime;
   
    public float width;
    public float height;
    
    //Properites
    public String faction;
    public String objectId;
    public float hitPoints;
    
    public boolean TOBEREMOVED;
    
    //Future Prediction
    //COLLISSION BOX IS ANGLE FROM facingDirection, applied to -width/2,-height/2 ; width/2,-height/2 ; -width/2,height/2 ; width/2,height/2
    //then translate by mPosition
    
    public GameObject(){
        super();
        mPosition = new Vector2();
    }
    
    public void setMovable(Boolean inputTOBEREMOVED){
    	TOBEREMOVED = inputTOBEREMOVED;
    }
    
    public void setFaction(String inputFaction){
    	this.faction = inputFaction;
    }
    
    public Vector2 getPosition() {
        // TODO Auto-generated method stub
        return mPosition;
    }

    public void setPosition(float x, float y){
        mPosition.set(x, y);
    }

	public void setObjectId(String inputObjId) {
		// TODO Auto-generated method stub
		objectId = inputObjId;
		
	}
}
