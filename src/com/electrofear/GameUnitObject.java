package com.electrofear;

import java.util.ArrayList;

public class GameUnitObject extends GameObject {
    private float zAxis;
    private float height, width;
    private float angle;
    private float turnRate; //angle per second
    private float relativeX, relativeY; //relative to parent
    private float calculatedX, calculatedY;
    private boolean angleRelativeToParent;
    private DrawableObject mDrawable; //actual image or so
    private DrawableObject mDrawableShadow;
    private Texture mTexture;
    private GameUnitObject mParent = null;
    private ArrayList<GameUnitObject> mChildGameUnitObj;
    private String gameUnitObjectID;
    private String internalId, relativeId;
    
    public GameUnitObject(String objId,
    				      String inputInternalId,
    				      String inputRelativeId,
                          float width2, 
                          float height2, 
                          float relativeX, 
                          float relativeY,
                          float inputAngle,
                          float inputZAxis,
                          boolean angleRelativeToParent,
                          DrawableObject inputDrawable,
                          DrawableObject inputDrawableShadow){
        gameUnitObjectID = objId;
        internalId = inputInternalId;
        relativeId = inputRelativeId;
        mChildGameUnitObj = new ArrayList();
        mParent = null;
        height = height2;
        width = width2;
        this.relativeX = relativeX;
        this.relativeY = relativeY;
        this.calculatedX = 0;
        this.calculatedY = 0;
        angle = inputAngle;
        mDrawable = inputDrawable;
        zAxis = inputZAxis;
        this.angleRelativeToParent = angleRelativeToParent;
        
        //Shadow Drawables
        mDrawableShadow = inputDrawableShadow;
    }
    
    public void setAngle(float inputAngle){
    	this.angle = inputAngle;
    }

    public float getAngle(){
    	return this.angle;
    }
    
    //ADDS OJBECT BY INTERNAL ID, ALSO CHANGES THE Z-AXIS with Z-AXIS+1
    public void addGameUnitObjectByInternalId(GameUnitObject obj){
    	
    	if (this.internalId.equals(obj.relativeId)) {
    		obj.mParent = this;
    		//obj.zAxis = this.zAxis + 1.0f;
    		this.mChildGameUnitObj.add(obj);
    		return;
    	} else {
    		for (int i = 0; i < mChildGameUnitObj.size(); i++) {
    			mChildGameUnitObj.get(i).addGameUnitObjectByInternalId(obj);
    		}
    	}
    }    
    
    //ADDS OBJECT That DOES NOT NEED INTERNAL/EXTERNAL ID Relationship
    public void addGameUnit(GameUnitObject obj) {
    	obj.mParent = this;
    	//obj.zAxis = this.zAxis + 1.0f;
    	this.mChildGameUnitObj.add(obj);
    }
    
    public void setRelativeTranslateXY(float inputX, float inputY){
        relativeX = inputX;
        relativeY = inputY;
    }
    
    public void setCalculatedTranslateXY(float x, float y) {
        // TODO Auto-generated method stub
        calculatedX = x;
        calculatedY = y;
    }
    
    public void setHeightWidth(float width, float height){
        this.height = height;
        this.width = width;
    }
    
    @Override
	public Vector2 getPosition(){
    	return new Vector2(calculatedX, calculatedY);
    }
    
    public void calculate(){
        //get current relative X, Y and translate
        //get angle and rotate
        //translate by parent X,Y
        if (this.mParent != null){
            String testing = this.gameUnitObjectID;
            float calculatingX = this.relativeX;
            float calculatingY = this.relativeY;
            float currentAngle = this.angle;
         
            //do rotation calculation, if true base angle off parent, else use your own
            if (angleRelativeToParent){
                this.angle = this.mParent.angle;
            }
            double radians = Math.toRadians(this.mParent.angle % 360);
            //double unitLength = Math.sqrt(calculatingX * calculatingX + calculatingY * calculatingY);
            
            calculatingX = (float) (Math.cos(radians) * (relativeX) - Math.sin(radians) * (relativeY));
            calculatingY = (float) (Math.sin(radians) * (relativeX) + Math.cos(radians) * (relativeY));

            
            //calculatingY =  (float) (Math.sin(radians) * unitLength) * this.relativeY;
            //calculatingX =  (float) (Math.cos(radians) * unitLength) * this.relativeX;
           
            calculatingX += this.mParent.calculatedX;
            calculatingY += this.mParent.calculatedY;
            
            //NEW CENTER OF TURRET
            this.calculatedX = calculatingX;
            this.calculatedY = calculatingY;
            
            this.zAxis = mParent.zAxis + 1.0f;
        } else {
        	zAxis = 1.0f;
        }
            
        //DrawableObject and then update
        mDrawable.updateCoords(this.calculatedX, this.calculatedY, width, height, angle, zAxis);
        
        if (mDrawableShadow != null) {
        	this.calculateShadowPositions(this.calculatedX, this.calculatedY, width, height, angle, zAxis);
        }
        //DrawableObject 

        
        for (int i = 0; i < this.mChildGameUnitObj.size(); i++){
            this.mChildGameUnitObj.get(i).calculate();
        }        
    }
    
    private void calculateShadowPositions(float x, float y, float width, float height, float angle, float zAxis){
    	float shadowX,shadowY;
    	float unitLength;
    	
    	unitLength = (float) Math.sqrt( BaseObject.directionShadow.x * BaseObject.directionShadow.x + BaseObject.directionShadow.y * BaseObject.directionShadow.y);
    	
    	shadowX = x + (BaseObject.directionShadow.x / unitLength) * BaseObject.heightShadow;
    	shadowY = y + (BaseObject.directionShadow.y / unitLength) * BaseObject.heightShadow;
    	
    	mDrawableShadow.updateCoords(shadowX, shadowY, width, height, angle, zAxis - mDrawableShadow.getShadowDifference());
    }
    
    public void addGameUnitObject(GameUnitObject obj){
        obj.mParent = this;
        this.mChildGameUnitObj.add(obj);
    }
    
    @Override
	public void update(float timeDelta, BaseObject parent){
    	GameObject unitParent = (GameObject)parent;
    	Vector2 parentPosition = unitParent.getPosition();
    	this.setCalculatedTranslateXY(parentPosition.x, parentPosition.y);
    	
        //calculate everything
        this.calculate();
        this.scheduleDrawables();
    }
    
    //Schedulecurrentdrawable and then go down list and then create all child drawables
    public void scheduleDrawables(){
        if (mDrawable != null){
        	
            //DRAW SHADOW, draw shadow first before actual object!
            if (BaseObject.drawShadow) {
            	if (mDrawableShadow != null) {
            		BaseObject.renderSystem.scheduleForDraw(mDrawableShadow);
            	}
            }        	
        	
            BaseObject.renderSystem.scheduleForDraw(mDrawable);
            

            
        }
        for (int i = 0; i < this.mChildGameUnitObj.size(); i++){
            this.mChildGameUnitObj.get(i).scheduleDrawables();
        }        
    }



}
