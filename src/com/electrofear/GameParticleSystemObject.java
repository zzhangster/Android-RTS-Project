package com.electrofear;

import java.util.ArrayList;

import com.electrofear.parser.GlobalDataGraphicAnimation;
import com.electrofear.parser.GlobalDataParticle;

//Contains a series of particles of one type

public class GameParticleSystemObject extends GameObject{
    private float zAxis;
    private float height, width;
    private float angle, defaultAngle;
    private float turnRate; //angle per second
    private float relativeX, relativeY; //relative to parent
    private float calculatedX, calculatedY;
    private GameUnitObject mParent = null;
    private ArrayList<Vector3> relativeParticlePosition;
    private ArrayList<GameParticleUnitObject> mParticleList;
    private ArrayList durationSpawnTime;
    private String gameUnitObjectID;
    private String internalId, relativeId;
    private String textureId;
    private GlobalDataGraphicAnimation globalParticleGraphicAnimationProperties;
    private int maxParticles = 50;
    private String particleAlphaSystem, particleSizeSystem;
    private float positionX,positionY,positionZ;
    private float totalDuration, inBetweenDuration;
    private float timeTracker = 0;
    private int particlesAdded = 0;
    
    /* GameParticleUnitObject
     * ======================
     * Takes, position x,y,z
     * and totalDuration
     * inBetweenDuration is
     * time between each 
     * series of particle
     * creation
     * ======================
     */
    public GameParticleSystemObject(float inputPositionX,
                                  float inputPositionY,
                                  float inputPositionZ,
                                  float inputTotalDuration,
                                  float inputInBetweenDuration){
        
        positionX = inputPositionX;
        positionY = inputPositionY;
        positionZ = inputPositionZ;
        totalDuration = inputTotalDuration;
        inBetweenDuration = inputInBetweenDuration;
        mParticleList = new ArrayList();
        relativeParticlePosition = new ArrayList();
        durationSpawnTime = new ArrayList();
        timeTracker = 0;
    }
    
    public GameParticleSystemObject( float inputPositionX,
                                   float inputPositionY,
                                   float inputPositionZ,
                                   GlobalDataParticle objParticle) {
        positionX = inputPositionX;
        positionY = inputPositionY;
        positionZ = inputPositionZ;
        
        totalDuration = objParticle.particleDuration;
        inBetweenDuration = objParticle.particleDeviation;
        mParticleList = new ArrayList();
        relativeParticlePosition = new ArrayList();
        durationSpawnTime = new ArrayList();
        textureId = objParticle.particleGraphic;
        timeTracker = 0;
        globalParticleGraphicAnimationProperties = BaseObject.contextGlobalXMLData.getGraphicAnimationById(textureId);
        particleAlphaSystem = objParticle.particleAlpha;
        particleSizeSystem = objParticle.particleSize;
        
    }
    
    public void addParticleSpawnPosition( Vector3 inputPosition, float timeDelay) {
        relativeParticlePosition.add(inputPosition);
        durationSpawnTime.add(timeDelay);
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
    
    public void update(float timeDelta, BaseObject parent){
        timeTracker += timeDelta;

        for (int i = 0; i < durationSpawnTime.size(); i ++) {
            if (timeTracker > (Float) durationSpawnTime.get(i) && particlesAdded < maxParticles) {
                durationSpawnTime.set(i, (Float) durationSpawnTime.get(i) + 0.2f);
                createParticleAtPosition(i);
                particlesAdded++;
            }
        }
        
        //Calculates
        this.updateParticles(timeDelta);
    }
    
    /*
     * Helper Function to Create Particles
     * Spawn particles when called
     * 
     */
    public void createParticleAtPosition(int inputPosition){
        GameParticleUnitObject tempParticleObj = new GameParticleUnitObject(particleAlphaSystem,
                                                                            particleSizeSystem,
                                                                            (float) globalParticleGraphicAnimationProperties.width,
                                                                            (float) globalParticleGraphicAnimationProperties.height);
        DrawableBitmap tempParticle = new DrawableBitmap(  relativeParticlePosition.get(inputPosition).x + positionX,
                                                           relativeParticlePosition.get(inputPosition).y + positionY,
                                                           relativeParticlePosition.get(inputPosition).z + positionZ,
                                                           (float) globalParticleGraphicAnimationProperties.width,
                                                           (float) globalParticleGraphicAnimationProperties.height,
                                                           0.0f,
                                                           globalParticleGraphicAnimationProperties.imageBase,
                                                           (int) globalParticleGraphicAnimationProperties.imageCount);
        tempParticleObj.setAnimationDrawable(tempParticle);
        
        
        //if (this.particleSizeSystem == "FADE_UNFADE")
        
        mParticleList.add(tempParticleObj);
        
    }
    
    
    public void updateParticles(float timeDelta){
        for (int i = 0; i < this.mParticleList.size(); i++){
            this.mParticleList.get(i).updateParticle(timeDelta);
        }        
    }

}
