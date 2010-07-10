package com.electrofear;

import android.os.SystemClock;

public class GameThread implements Runnable{
    private long mLastTime;

    private boolean mFinished;
    private ObjectManager mGameRoot;
    private GameRenderer mRenderer;
    
    @Override
    public void run() {
        // TODO Auto-generated method stub
        mFinished = false;
        while (!mFinished) {
    
            
            if (mGameRoot != null) {
                //Wait for Rendering Thread to Finish
                mRenderer.waitDrawingComplete();                
                final long time = SystemClock.uptimeMillis();
                final long timeDelta = time - mLastTime;
                long finalDelta = timeDelta;
                if (timeDelta > 12) {
                    float secondsDelta = (time - mLastTime) * 0.001f;
                    if (secondsDelta > 0.1f) {
                        secondsDelta = 0.1f;
                    }
                    //TODO This is the game root, will be used to update objects under it!
                    mGameRoot.update(secondsDelta, null);
                    
                    //TODO get camera positions and pass below
                    //This calls the setRenderQueue in render system which will eventually set the rendering thread to
                    //a queue of objects
                    BaseObject.renderSystem.swapQueue(mRenderer, 10f, 10f);
                }
                else {
                    
                }
            }

        }
        BaseObject.renderSystem.emptyQueues(mRenderer);
    }
    
    public GameThread(GameRenderer renderer) {
        mRenderer = renderer;
    }
    
    public void resumeGame() {
    }

    //Get the objectManager gameroot so that the thread can update
    //this gameroot
    public void setGameRoot(ObjectManager mGameRoot) {
        // TODO Auto-generated method stub
        this.mGameRoot = mGameRoot;
    }
}
