package com.electrofear;

import javax.microedition.khronos.opengles.GL10;


import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.view.KeyEvent;
import android.view.MotionEvent;


public class Game {
    private GameThread mGameThread;
    private Thread mGame;
    private ObjectManager mGameRoot;
    
    private GameRenderer mRenderer;
    private GLSurfaceView mSurfaceView;
    private ContextParameters mContextParameters;    
    
    private boolean mRunning;
    private boolean mBootstrapComplete;    
    private boolean mGLDataLoaded;
    
    //Controls
    private float firstTouchX, firstTouchY;

    
    public Game() {
        mRunning = false;
        mBootstrapComplete = false;
        mGLDataLoaded = false;
        mContextParameters = new ContextParameters();        
    }
    
    public void setSurfaceView(GLSurfaceView myGLSurfaceView) {
        // TODO Auto-generated method stub
        
    }
    public void bootstrap(Context context, int viewWidth, int viewHeight, int gameWidth, int gameHeight) {
        // TODO Auto-generated method stub
        mGameRoot = new ObjectManager();
        mRenderer = new GameRenderer(context, this, gameWidth, gameHeight);
        mGameThread = new GameThread(mRenderer);
        
        /*-- Set ContextParameters --*/
        BaseObject.contextParameters.viewWidth = viewWidth;
        BaseObject.contextParameters.viewHeight = viewHeight;
        BaseObject.contextParameters.gameWidth = gameWidth;
        BaseObject.contextParameters.gameHeight = gameHeight;
        BaseObject.contextParameters.viewScaleX = (float)viewWidth / gameWidth;
        BaseObject.contextParameters.viewScaleY = (float)viewHeight / gameHeight;
        BaseObject.contextParameters.context = context;
        BaseObject.contextParameters.gameRoot = mGameRoot;
        
        //Add GameRoot to gamethread so the gamethread can update the root during run function
        mGameThread.setGameRoot(mGameRoot);
        //start adding items to the 
        //start();
    }
    
    public void start() {
        if (!mRunning) {
            assert mGame == null;
            // Now's a good time to run the GC.
            Runtime r = Runtime.getRuntime();
            r.gc();
            mGame = new Thread(mGameThread);
            mGame.setName("Game");
            mGame.start();
            mRunning = true;
        } else {
            mGameThread.resumeGame();
        }
    } 
    
    //GOING TO ACTUAL LEVEL after surface change detected
    public void onSurfaceReady(Context mContext, GL10 gl) {
        goToLevel(1, mContext, gl);
    }
    
    protected synchronized void goToLevel(int level, Context mContext, GL10 gl) {
        //attach root here so that drawing object will be added to mGameRoot
        BaseObject.levelSystem.loadLevel(level);
        BaseObject.mapLibrary.loadAllTextures(mContext, gl);
        start();
    }
    
    public GameRenderer getRenderer() {
        // TODO Auto-generated method stub
        return mRenderer;
    }
    
    public void onSurfaceCreated() {
        
            
            //mSurfaceView.loadTextures(BaseObject.sSystemRegistry.longTermTextureLibrary);
            //mSurfaceView.loadTextures(BaseObject.sSystemRegistry.shortTermTextureLibrary);
            //mSurfaceView.loadBuffers(BaseObject.sSystemRegistry.bufferLibrary);
            mGLDataLoaded = true;
          
    }

    public boolean onTrackballEvent(MotionEvent event) {
    	//TRACK BALL??? REALLY?  WHO USES TRACKBALL THESE DAYS?   JESUS, BUY A NEW PHONE ALREADY!   KTHNXBAI!
        return true;
    }
    
    public boolean onOrientationEvent(float x, float y, float z) {
    	//LOL MAYBE NOT USED HERE!
        return true;
    }
    
    public boolean onTouchEvent(MotionEvent event) {
    	int action = event.getAction();
    	
    	float currentX,currentY,diffX,diffY;
    	
    	if (action == MotionEvent.ACTION_DOWN) {
    		firstTouchX = event.getRawX();
    		firstTouchY = event.getRawY();
    	} else if (action == MotionEvent.ACTION_MOVE) {
    		currentX = event.getRawX();
    		currentY = event.getRawY();
    		
    		diffX = currentX - firstTouchX;
    		diffY = currentY - firstTouchY;
    		//Find difference and update CameraPosition, NOTE NO DIFFX AT THIS TIME!
    		BaseObject.cameraSystem.updateDeltaChangeCoords(0, diffY, 0, 0, diffY, 0);
    		firstTouchX = currentX;
    		firstTouchY = currentY;
    		
    	} else if (action == MotionEvent.ACTION_UP) {
 		
    	}
    	
        return true;
    }
    
    public boolean onKeyDownEvent(int keyCode) {
    	// 
        return true;
    }
    
    public boolean onKeyUpEvent(int keyCode) {

        return true;
    }
    
    public void onPause() {
        // TODO Auto-generated method stub
        
    }
    public void onResume() {
        // TODO Auto-generated method stub
        
    }
}
