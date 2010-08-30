package com.electrofear;


public class UnitRenderComponent extends BaseObject {
    private DrawableObject mDrawable;
    
    private GameObject gameObjectManager; //used here to keep track of complex objects just as tanks and others with turrest and cannons
                                          //Ideally this has one child (chasis) and that has another child(turrets) and that has it's own
                                          //(cannons)
    private int mPriority = 0;
    private boolean mCameraRelative;
    private Vector2 mPositionWorkspace;
    private Vector2 mScreenLocation;
    private Vector2 mDrawOffset;
    
    public UnitRenderComponent() {
        //super();
        //setPhase(ComponentPhases.DRAW.ordinal());
        
        //mPositionWorkspace = new Vector2();
        //mScreenLocation = new Vector2();
        //mDrawOffset = new Vector2();
        //reset();
    }
    
    @Override
    public void reset() {
        mPriority = 0;
        mCameraRelative = true;
        mDrawable = null;
        mDrawOffset = null;
    }
    


    @Override
	public void update(float timeDelta, BaseObject parent) {
        if (mDrawable != null) {

            
            RenderSystem system = BaseObject.renderSystem;
            if (system != null) {
                //Do logic to render chasis, turret and cannon in correct position then call scheduleToDraw function in render system
            }
        }
    }

    public DrawableObject getDrawable() {
        return mDrawable;
    }
    
    public void setDrawable(DrawableObject drawable) {
        mDrawable = drawable;
    }

    public void setPriority(int priority) {
        mPriority = priority;
    }
    
    public int getPriority() {
        return mPriority;
    }

    public void setCameraRelative(boolean relative) {
        mCameraRelative = relative;
    }
    
    public void setDrawOffset(float x, float y) {
        mDrawOffset.set(x, y);
    }
}
