package com.electrofear;

import com.electrofear.GameObjectFactory.GameObjectType;





public class LevelSystem {

    public GameObject mBackgroundObject;
    public GameObject mGameObject; //holds actual items
    public void loadLevel(int level) {
        // TODO Auto-generated method stub
        ObjectManager root = BaseObject.contextParameters.gameRoot;
        LevelBuilder builder = BaseObject.levelBuilder; // for experimenting
        mBackgroundObject = builder.buildBackground(0,330,972); // Replace with real values
        root.add(mBackgroundObject);
        
        
        //LOAD ALL MAP FAUNAS ETC HERE!
        
        
        
        //Create Objects, temporary solution here! HERE how's to create objects, put this somewhere else later!
        //mGameObject = BaseObject.objFactory.spawn(GameObjectType.NACSCOUT, 50, 80); //Generate NACSCOUT at position 30, 30
        //root.add(mGameObject);
        //
        //mGameObject = BaseObject.objFactory.spawn(GameObjectType.NACSCOUT, 50, 200); //Generate NACSCOUT at position 30, 30
        //root.add(mGameObject);
        
        mGameObject = BaseObject.objFactory.spawn(GameObjectType.NACHEAVYTANK, 90, 50, true); //Generate NACSCOUT at position 30, 30
        root.add(mGameObject);
        
        mGameObject = BaseObject.objFactory.spawn(GameObjectType.NACHEAVYTANK, -60, -110, false); //Generate NACSCOUT at position 30, 30
        root.add(mGameObject);        
       
        //mGameObject = BaseObject.objFactory.spawn(GameObjectType.NACWALKER, 190, 230); //Generate NACSCOUT at position 30, 30
        //root.add(mGameObject);
    }

}
