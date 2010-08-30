package com.electrofear;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class OrderedRenderObjectManager extends ObjectManager{

    //TODO: replace this with no allocation array later
    private ArrayList<BaseObject> mObjectList;
    private ArrayList<BaseObject> mObjects;
    private ArrayList<BaseObject> mPendingAdditions;
    private ArrayList<BaseObject> mPendingRemovals;    
    private final static PhasedObjectComparator sPhasedObjectComparator = new PhasedObjectComparator();
    
    public OrderedRenderObjectManager(){
        mObjectList = new ArrayList();
        mObjects = new ArrayList();
        mPendingAdditions = new ArrayList();
        mPendingRemovals = new ArrayList();

    }
    
    @Override
	public ArrayList<BaseObject> getMObjectList(){
        return mObjectList;
    }
    
    @Override
	public void update(float secondsDelta, BaseObject parent) {
        // TODO Auto-generated method stub
        
        for (int i = 0; i < mObjectList.size(); i++){
            mObjectList.get(i).update(secondsDelta, this);
            
        }
    }

    @Override
	public void clear(){
        BaseObject tempObject;
        for (int i = 0; i < mObjectList.size(); i++){
            tempObject = mObjectList.get(i);
            tempObject = null;
        }
        mObjectList.clear();
    }

    @Override
	public void add(BaseObject object) {
        // TODO Auto-generated method stub
            mObjectList.add(object);
            sortArray();
    }
    
    public void sortArray(){
        Collections.sort(mObjectList, sPhasedObjectComparator);
    }
    
    private static class PhasedObjectComparator implements Comparator<BaseObject>  {
        public int compare(BaseObject object1, BaseObject object2) {
            int result = 0;
            if (object1 != null && object2 != null) {
                float temp = (((DrawableBitmap) object1).zAxis - ((DrawableBitmap) object2).zAxis);
                if(temp > 0) { 
                	return 1;
                } else {
                	return -1;
                }
            } else if (object1 == null && object2 != null) {
                result = 1;
            } else if (object2 == null && object1 != null) {
                result = -1;
            } 
            return result;
        }
    }    
}
