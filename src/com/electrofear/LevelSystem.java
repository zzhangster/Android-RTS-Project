package com.electrofear;





public class LevelSystem {

    public GameObject mBackgroundObject;
    public void loadLevel(int level, ObjectManager root) {
        // TODO Auto-generated method stub
        LevelBuilder builder = BaseObject.levelBuilder; // for experimenting
        mBackgroundObject = builder.buildBackground(0,800,800); // Replace with real values
        root.add(mBackgroundObject);
    }

}
