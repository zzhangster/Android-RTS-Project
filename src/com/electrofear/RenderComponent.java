package com.electrofear;


public class RenderComponent extends BaseObject {
    private DrawableObject mDrawable;
    private int mPriority = 0;
    private boolean mCameraRelative;
    private Vector2 mPositionWorkspace;
    private Vector2 mScreenLocation;
    private Vector2 mDrawOffset;
    
    public RenderComponent() {
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
                //mPositionWorkspace.set(((GameObject)parent).getPosition());
                //mPositionWorkspace.add(mDrawOffset);

                /*if (mDrawable.visibleAtPosition(mScreenLocation)) {
                    system.scheduleForDraw(mDrawable, mPositionWorkspace, mPriority, mCameraRelative);
                }*/
                system.scheduleForDraw(mDrawable);
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
