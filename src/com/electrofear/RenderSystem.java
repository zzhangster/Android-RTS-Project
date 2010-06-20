package com.electrofear;



public class RenderSystem {
    private ObjectManager mRenderQueues;
    
    public RenderSystem(){
        mRenderQueues = new ObjectManager();
    }
    
    public void setRenderQueue(GameRenderer renderer, float cameraX, float cameraY){
        renderer.setDrawQueue(mRenderQueues, cameraX, cameraY); 
    }
    
    /** 
     * Schedule an object for drawing... need to call BaseObject.RenderSystem.scheduleForDraw
     * Use setRenderQueue later to pass the objects to the rendering thread
     * may be slow.
     * @param classObject The class type to search for (e.g. BaseObject.class).
     * @return
     */
    public void scheduleForDraw(BaseObject object, float positionX, float positionY, int priority, boolean cameraRelative){
        //TODO change positionX and positionY to vector class
        mRenderQueues.add(object);
    }

    public void emptyQueues(GameRenderer mRenderer) {
        // TODO Auto-generated method stub
        
    }

    public void scheduleForDraw(DrawableObject mDrawable,
            Vector2 mPositionWorkspace, int mPriority, boolean mCameraRelative) {
        // TODO Auto-generated method stub
        
    }
}
