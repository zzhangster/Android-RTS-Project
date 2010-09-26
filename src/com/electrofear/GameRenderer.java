package com.electrofear;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;
import javax.microedition.khronos.opengles.GL11Ext;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.opengl.GLUtils;
import android.os.SystemClock;
import android.util.Log;

public class GameRenderer implements GLSurfaceView.Renderer{
    private long mLastTime;

    private int mWidth;
    private int mHeight;
    private float cameraX;
    private float cameraY;
    private Context mContext;
    private Game mGame;
    private OrderedRenderObjectManager mDrawQueue;
    private float mScaleX;
    private float mScaleY;
    private boolean lostSurfaceView = false; //Hackish attempt to mimic onSurfaceLost
    
    public GameRenderer(Context context, Game game, int gameWidth,
            int gameHeight) {
        // TODO Auto-generated constructor stub
        mContext = context;
        mGame = game;
        mWidth = gameWidth;
        mHeight = gameHeight;
    }

    public void onDrawFrame(GL10 gl) {
    	Vector3 cameraPosition,cameraLookAt;
    	
    	
        long time = SystemClock.uptimeMillis();
        long time_delta = (time - mLastTime);
        
        synchronized(this){
            gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);  
            gl.glShadeModel(GL10.GL_SMOOTH);
            gl.glEnable(GL10.GL_BLEND);
            //gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);  
            gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA); 
            //Check if the blend flag has been set to enable/disable blending
            gl.glDisable(GL10.GL_DEPTH_TEST);   //Turn Depth Testing Off
            
            
            
            //gl.glClearColor(1.0f, 0.0f, 0.0f, 0.5f); 
            
            //SETUP Camera Coordinates
            cameraPosition = BaseObject.cameraSystem.getCameraPosition();
            cameraLookAt = BaseObject.cameraSystem.getCameraLookAt();
            
            gl.glLoadIdentity();                    //Reset The Current Modelview Matrix
            GLU.gluLookAt(gl, 
                          cameraPosition.x, cameraPosition.y, cameraPosition.z, 
                          cameraLookAt.x, cameraLookAt.y, cameraLookAt.z,
                          0, 1, 0);
            //END OF SETUP CAMERA
          
            
            
            if (mDrawQueue != null) {
                
                ArrayList<BaseObject> objectDrawList = mDrawQueue.getMObjectList();
                for (int i = 0; i < objectDrawList.size(); i++){
                    DrawableBitmap temp = (DrawableBitmap) (objectDrawList.get(i));
                    
                    gl.glPushMatrix();
                    temp.startDrawing(gl);
                    gl.glPopMatrix();
                } 
            }
        }
        
    }
    
    public synchronized void checkRenderingIsFinshed(){
        
    }

    public synchronized void setDrawQueue(OrderedRenderObjectManager queue, float cameraX, float cameraY) {
        mDrawQueue = queue;
        this.cameraX = cameraX;
        this.cameraY = cameraY;
        //TODO: synchronize the queue or use a draw lock!
    }
    
    //ANOTHER RENDERER FUNCTION, ON SURFACECHANGED MEANS
    public void onSurfaceChanged(GL10 gl, int w, int h) {
        //mWidth = w;0
        //mHeight = h;
        // ensure the same aspect ratio as the game
        if(h == 0) {                       //Prevent A Divide By Zero By
            h = 1;                         //Making Height Equal One
        }

        gl.glViewport(0, 0, w, h);     //Reset The Current Viewport
        gl.glMatrixMode(GL10.GL_PROJECTION);    //Select The Projection Matrix
        gl.glLoadIdentity();                    //Reset The Projection Matrix

        //Calculate The Aspect Ratio Of The Window
        GLU.gluPerspective(gl, 45.0f, (float)w / (float)h, 0.1f, 2000.0f);
        float ratio = (float) mWidth / mHeight;
        gl.glMatrixMode(GL10.GL_MODELVIEW);     //Select The Modelview Matrix
        gl.glLoadIdentity(); 
        //gl.glFrustumf(-ratio, ratio, -200, 200, 3, 1000);
        
        
        mGame.onSurfaceReady(mContext, gl);
        
        //Bad because we still have texture in memory if we decide to quit
        
    }

    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        // Initialize GL
        gl.glDisable(GL10.GL_DITHER);                       //Disable dithering
        gl.glEnable(GL10.GL_TEXTURE_2D);                    //Enable Texture Mapping
        gl.glShadeModel(GL10.GL_FLAT);                    //Enable Smooth Shading
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);            //Black Background
        gl.glClearDepthf(1.0f);                             //Depth Buffer Setup
        gl.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE_MINUS_SRC_ALPHA);     //Set The Blending Function For Translucency
        gl.glDepthFunc(GL10.GL_LEQUAL);                     //The Type Of Depth Testing To Do
        gl.glEnable(GL10.GL_COLOR_MATERIAL);
        //Really Nice Perspective Calculations
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST); 
        // TODO call the mGame.onSurfaceCreated and then load textures
        // IMPORTANT BECAUSE textures can be deleted! NOTICE that if you leave app and come back
        // this function is called
        
        //textureTest(gl);
        if (lostSurfaceView) {
	        BaseObject.mapLibrary.resetTextures();
	        BaseObject.mapLibrary.loadAllTextures(this.mContext, gl);
        } else {
        	lostSurfaceView = true;
        }

    }
    
    public void onSurfaceLost() {
    	Log.v("Game Renderer: ", "Screen Lost");
    	BaseObject.mapLibrary.resetTextures();
    }
      
    
    /*public void textureTest(GL10 gl){
        //Texture Test
        int[] textures = new int[1];
        int[] mCropWorkspace = new int[4];
        gl.glGenTextures(1, textures, 0);

        int mTextureID = textures[0];
        gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextureID);

        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);

        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_CLAMP_TO_EDGE);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_CLAMP_TO_EDGE);

        gl.glTexEnvf(GL10.GL_TEXTURE_ENV, GL10.GL_TEXTURE_ENV_MODE, GL10.GL_REPLACE);

        InputStream is = mContext.getResources().openRawResource(R.drawable.back_ground_test);
        Bitmap bitmap;
        try {
            bitmap = BitmapFactory.decodeStream(is);
            //try this  bitmap = BitmapFactory.decodeStream(is, null, sBitmapOptions);
        } finally {
            try {
                is.close();
            } catch(IOException e) {
                // Ignore.
            }
        }
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
        mCropWorkspace[0] = 0;
        mCropWorkspace[1] = bitmap.getHeight();
        mCropWorkspace[2] = bitmap.getWidth();
        mCropWorkspace[3] = -bitmap.getHeight();
        bitmap.recycle();
        ((GL11) gl).glTexParameteriv(GL10.GL_TEXTURE_2D, 
                GL11Ext.GL_TEXTURE_CROP_RECT_OES, mCropWorkspace, 0);

    }*/
    
    public synchronized void waitDrawingComplete() {
    }

}
