package com.electrofear;

import java.util.Vector;

public class GameObject extends ObjectManager {

    private Vector2 mPosition;
    public GameObject(){
        super();
        mPosition = new Vector2();
    }
    
    public Vector2 getPosition() {
        // TODO Auto-generated method stub
        return mPosition;
    }

    public void setPosition(float x, float y){
        mPosition.set(x, y);
    }
}
