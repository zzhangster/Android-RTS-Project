package com.electrofear;

import com.electrofear.GameObjectFactory.GameObjectType;
import com.electrofear.parser.GlobalDataParticle;

/*
 * Creates particle effects based on xml static data
 * 
 * 
 */

public class GameParticleEffectObjectFactory extends GameObject{
    public enum GameParticleObjectType {
        INVALID(-1),
        // Infantry Types
        FIRE_01 (10),
        FIRE_02 (11),
        SMOKE_01 (12),
        SMOKE_02 (13),
        SMOKE_03 (14),
        ALKAZIANINFANTRYHEAVY (26),
        ALKAZIANINFANTRYSUPPORT (27),
        NACINFANTRYLIGHT (28),
        NACINFANTRYHEAVY (29),
        NACINFANTRYSUPPORT (30);
        
        private final int mIndex;
        GameParticleObjectType(int index) {
            this.mIndex = index;
        }
    }    
    
    public GameObject spawn(GameParticleObjectType type, float x, float y, float z) {
        GameObject newObject = null;
        switch (type){
            case FIRE_01:
                newObject = spawnFire01(x,y,z);
                break;
            case SMOKE_01:
                newObject = spawnSmoke01(x,y,z);
                break;
            case SMOKE_02:
                newObject = spawnFire01(x,y,z);
                break;
            case SMOKE_03:
                newObject = spawnFire01(x,y,z);
                break;
            default:
                break;
        }
        return newObject;
    }
    
    /* Uses the FIRE_EMMITER_01 XML Data */
    private GameObject spawnFire01(float inputX, float inputY, float inputZ) {
        GlobalDataParticle objParticle = BaseObject.contextGlobalXMLData.getParticleById("FIRE_EMMITER_01");  

        //Define Emmiter Position
        //Right now create 6 Emmitor positions
        GameParticleSystemObject particleEffectObject = new GameParticleSystemObject(inputX, inputY, inputZ, objParticle);
        particleEffectObject.addParticleSpawnPosition(new Vector3(-5.0f, 5.0f, inputZ), 4f);
        //particleEffectObject.addParticleSpawnPosition(new Vector3(-5.0f, 0.0f, inputZ), 8f);
        //particleEffectObject.addParticleSpawnPosition(new Vector3(-5.0f, -5.0f, inputZ), 12f);
        
        //particleEffectObject.addParticleSpawnPosition(new Vector3( 5.0f, 5.0f, inputZ), 16f);
        //particleEffectObject.addParticleSpawnPosition(new Vector3( 5.0f, 0.0f, inputZ), 20f);
        //particleEffectObject.addParticleSpawnPosition(new Vector3( 5.0f, -5.0f, inputZ), 24f);
        
        
        
        return particleEffectObject;
    }
    
    private GameObject spawnSmoke01(float inputX, float inputY, float inputZ) {
        GlobalDataParticle objParticle = BaseObject.contextGlobalXMLData.getParticleById("SMOKE_EMMITER_01");  

        //Define Emmiter Position
        //Right now create 6 Emmitor positions
        GameParticleSystemObject particleEffectObject = new GameParticleSystemObject(inputX, inputY, inputZ, objParticle);
        particleEffectObject.addParticleSpawnPosition(new Vector3(-5.0f, 5.0f, inputZ), 4f);
        //particleEffectObject.addParticleSpawnPosition(new Vector3(-5.0f, 0.0f, inputZ), 8f);
        //particleEffectObject.addParticleSpawnPosition(new Vector3(-5.0f, -5.0f, inputZ), 12f);
        
        //particleEffectObject.addParticleSpawnPosition(new Vector3( 5.0f, 5.0f, inputZ), 16f);
        //particleEffectObject.addParticleSpawnPosition(new Vector3( 5.0f, 0.0f, inputZ), 20f);
        //particleEffectObject.addParticleSpawnPosition(new Vector3( 5.0f, -5.0f, inputZ), 24f);
        
        
        
        return particleEffectObject;
    }    
    
    private void createParticleSpread(GlobalDataParticle inputParticle) {
        
        if(inputParticle.particlesType == "Fire_01") {
            
            
            
            
            
        }
    }
    
}
