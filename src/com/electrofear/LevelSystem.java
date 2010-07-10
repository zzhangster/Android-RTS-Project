package com.electrofear;

import com.electrofear.GameObjectFactory.GameObjectType;





public class LevelSystem {

    public GameObject mBackgroundObject;
    public GameObject mGameObject; //holds actual items
    public void loadLevel(int level, ObjectManager root) {
        // TODO Auto-generated method stub
        LevelBuilder builder = BaseObject.levelBuilder; // for experimenting
        mBackgroundObject = builder.buildBackground(0,800,800); // Replace with real values
        root.add(mBackgroundObject);
        
        
        //Create Objects, temporary solution here!
        mGameObject = BaseObject.objFactory.spawn(GameObjectType.NACSCOUT, 50, 80); //Generate NACSCOUT at position 30, 30
        root.add(mGameObject);
        
        mGameObject = BaseObject.objFactory.spawn(GameObjectType.NACSCOUT, 50, 240); //Generate NACSCOUT at position 30, 30
        root.add(mGameObject);
        
        mGameObject = BaseObject.objFactory.spawn(GameObjectType.NACAFV, 40, 150); //Generate NACSCOUT at position 30, 30
        root.add(mGameObject);
        
        mGameObject = BaseObject.objFactory.spawn(GameObjectType.NACHEAVYTANK, 190, 120); //Generate NACSCOUT at position 30, 30
        root.add(mGameObject);
       
        mGameObject = BaseObject.objFactory.spawn(GameObjectType.NACAFV, 200, 230); //Generate NACSCOUT at position 30, 30
        root.add(mGameObject);
    }

}
