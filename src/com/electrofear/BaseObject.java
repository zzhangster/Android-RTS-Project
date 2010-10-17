package com.electrofear;

import java.util.Map;
import java.util.TreeMap;

import com.electrofear.level.GlobalLevelProperties;
import com.electrofear.parser.GlobalParsedXMLDataRepository;



/* All objects must inherit from this object - this will have a static object registery */
public class BaseObject {
    //public static MasterObjectRegistry objRegistry; // DO WE REALLY NEED THIS ONE?
    
    //Progress Week 2, OBJ Manager and OBJ Factory
    //Factory Creates any spawnable objects, while manager manages them
    public static ObjectManager objManager;
    public static GameObjectFactory objFactory = new GameObjectFactory();
    public static GameParticleEffectObjectFactory objParticleFactory = new GameParticleEffectObjectFactory();
    
    //public static CameraMainSystem cameraSystem; // CameraSystem
    public static CollisionMainSystem collisionSystem; // Collision System
    public static ContextParameters contextParameters = new ContextParameters(); //GLOBAL VALUES FOR SYSTEM
    public static GlobalParsedXMLDataRepository contextGlobalXMLData = new GlobalParsedXMLDataRepository();
    
    public static Map globalUnitObjData = new TreeMap();

    public static Object sSystemRegistry;
    
    //TEXTURE LIBRARY, holds TEXTURE OBJECTS which contains the drawable id and openGL id
    public static TextureLibrary mapLibrary = new TextureLibrary();
    public static TextureLibrary objectLibrary = new TextureLibrary();
    public static TextureLibrary effectsLibrary = new TextureLibrary();
    public static TextureLibrary uiLibrary = new TextureLibrary();

    //List of Systems
    public static RenderSystem renderSystem = new RenderSystem();
    public static LevelSystem levelSystem = new LevelSystem();
    public static CameraMainSystem cameraSystem = new CameraMainSystem();
    public static LevelBuilder levelBuilder = new LevelBuilder();
    
    //Create New System in Game class BootStrap function and not here
    public static LightingSystem lightSystem;

    public static DrawableFactory drawableFactory = new DrawableFactory();
    public static GlobalWorldObjectRegister globalWorldRegister = new GlobalWorldObjectRegister();
    
    
    
    //ALL GLOBAL SETTINGS FOR GAME - SHADOW SETTINGS
    public static GlobalLevelProperties globalLevelProperties = new GlobalLevelProperties();
    public static boolean drawShadow = true;
    public static Vector2 directionShadow = new Vector2(-10f, -20f);
    public static float heightShadow = 15f;
    public static Vector2 directionWind = new Vector2(0f, 10f);
    public static float magnitudeWind = 3f;
    public static boolean flushTextures = false;
    

    
    public void update(float timeDelta, BaseObject parent) {
        // TODO Auto-generated method stub
        
    }

    public void reset() {
    	
    }
    
    public static void resetAllElements() {
        // TODO Auto-generated method stub
        //Progress Week 2, OBJ Manager and OBJ Factory
        //Factory Creates any spawnable objects, while manager manages them
        objManager = new ObjectManager();
        objFactory = new GameObjectFactory();
        objParticleFactory = new GameParticleEffectObjectFactory();
        
        //public static CameraMainSystem cameraSystem; // CameraSystem
        collisionSystem = new CollisionMainSystem(); // Collision System
        contextParameters = new ContextParameters(); //GLOBAL VALUES FOR SYSTEM
        contextGlobalXMLData = new GlobalParsedXMLDataRepository();
        
        globalUnitObjData = new TreeMap();

        //TEXTURE LIBRARY, holds TEXTURE OBJECTS which contains the drawable id and openGL id
        mapLibrary = new TextureLibrary();
        objectLibrary = new TextureLibrary();
        effectsLibrary = new TextureLibrary();
        uiLibrary = new TextureLibrary();            
        //sSystemRegistry;

        //List of Systems
        renderSystem = new RenderSystem();
        levelSystem = new LevelSystem();
        cameraSystem = new CameraMainSystem();
        levelBuilder = new LevelBuilder();
        
        //Create New System in Game class BootStrap function and not here
        lightSystem = new LightingSystem();

        drawableFactory = new DrawableFactory();
        globalWorldRegister = new GlobalWorldObjectRegister();
        
        
        
        //ALL GLOBAL SETTINGS FOR GAME - SHADOW SETTINGS
        globalLevelProperties = new GlobalLevelProperties();
        drawShadow = true;
        directionShadow = new Vector2(-10f, -20f);
        heightShadow = 15f;
        flushTextures = false;
    }
    
    public static void resetAllTextures() {
	
    }
}
