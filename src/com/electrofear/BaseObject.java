package com.electrofear;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;


/* All objects must inherit from this object - this will have a static object registery */
public class BaseObject {
    public static MasterObjectRegistry objRegistry; // DO WE REALLY NEED THIS ONE?
    
    //Progress Week 2, OBJ Manager and OBJ Factory
    //Factory Creates any spawnable objects, while manager manages them
    public static ObjectManager objManager;
    public static GameObjectFactory objFactory;
    
    //public static CameraMainSystem cameraSystem; // CameraSystem
    public static CollisionMainSystem collisionSystem; // Collision System
    public static ContextParameters contextParameters; //GLOBAL VALUES FOR SYSTEM
    
    
    public static Map globalUnitObjData = new TreeMap();

    public static Object sSystemRegistry;

    //List of Systems
    public static RenderSystem renderSystem = new RenderSystem();
    public static LevelSystem levelSystem = new LevelSystem();
    public static CameraSystem cameraSystem = new CameraSystem();
    public static LevelBuilder levelBuilder = new LevelBuilder();

    public static DrawableFactory drawableFactory = new DrawableFactory();

    public void update(float timeDelta, BaseObject parent) {
        // TODO Auto-generated method stub
        
    }

    public void reset() {
        // TODO Auto-generated method stub
        
    }
}
