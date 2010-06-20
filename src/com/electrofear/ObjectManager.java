package com.electrofear;

import java.util.ArrayList;

public class ObjectManager extends BaseObject{

    //TODO: replace this with no allocation array later
    private ArrayList<BaseObject> mObjectList;
    private ArrayList<BaseObject> mObjects;
    private ArrayList<BaseObject> mPendingAdditions;
    private ArrayList<BaseObject> mPendingRemovals;    
    
    public ObjectManager(){
        mObjectList = new ArrayList();
        mObjects = new ArrayList();
        mPendingAdditions = new ArrayList();
        mPendingRemovals = new ArrayList();
    }
    
    public void update(float secondsDelta, BaseObject parent) {
        // TODO Auto-generated method stub
        
        for (int i = 0; i < mObjectList.size(); i++){
            mObjectList.get(i).update(secondsDelta, this);
            
        }
    }


    public void add(BaseObject object) {
        // TODO Auto-generated method stub
        mPendingAdditions.add(object);
        mObjectList.add(object);
    }
    
    

}
