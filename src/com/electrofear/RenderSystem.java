package com.electrofear;



public class RenderSystem {
    private OrderedRenderObjectManager[] mRenderQueues;
    private int renderQueueIndex = 0;//current render queue
    private int TOTALQUEUE = 2;
    
    public RenderSystem(){
        mRenderQueues = new OrderedRenderObjectManager[TOTALQUEUE];
        for (int i = 0; i < TOTALQUEUE; i++){
            mRenderQueues[i] = new OrderedRenderObjectManager();
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
        //TO DO, schedule objects based on
        //Add objects to end, if priority is zero, else more
        mRenderQueues[renderQueueIndex].add(object);
    }    

    public void emptyQueues(GameRenderer mRenderer) {
        // TODO Auto-generated method stub
        for (int i = 0; i < TOTALQUEUE; i++){
            mRenderQueues[i] = null;
            mRenderQueues[i] = new OrderedRenderObjectManager();
        }
    }

    public void scheduleForDraw(DrawableObject mDrawable,
        Vector2 mPositionWorkspace, int mPriority, boolean mCameraRelative) {
        // TODO Auto-generated method stub
        
    }
}
