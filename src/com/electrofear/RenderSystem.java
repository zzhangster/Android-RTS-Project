package com.electrofear;



public class RenderSystem {
    private ObjectManager[] mRenderQueues;
    private int renderQueueIndex = 0;//current render queue
    private int TOTALQUEUE = 2;
    
    public RenderSystem(){
        mRenderQueues = new ObjectManager[TOTALQUEUE];
        for (int i = 0; i < TOTALQUEUE; i++){
            mRenderQueues[i] = new ObjectManager();
        }
    }
    
    public void setRenderQueue(GameRenderer renderer, float cameraX, float cameraY){
        renderer.setDrawQueue(mRenderQueues[renderQueueIndex], cameraX, cameraY); 
    }
    
    public void swapQueue(GameRenderer renderer, float cameraX, float cameraY){
        renderer.setDrawQueue(mRenderQueues[renderQueueIndex], cameraX, cameraY);
        
        //Clear Current view for placement
        renderQueueIndex = (renderQueueIndex + 1) % TOTALQUEUE;
        mRenderQueues[renderQueueIndex].clear();
    }
    public void scheduleForDraw(BaseObject object){
        //TODO change positionX and positionY to vector class
        mRenderQueues[renderQueueIndex].add(object);
    }    

    public void emptyQueues(GameRenderer mRenderer) {
        // TODO Auto-generated method stub
        
    }

    public void scheduleForDraw(DrawableObject mDrawable,
        Vector2 mPositionWorkspace, int mPriority, boolean mCameraRelative) {
        // TODO Auto-generated method stub
        
    }
}
