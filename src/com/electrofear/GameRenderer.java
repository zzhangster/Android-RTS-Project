package com.electrofear;

import java.io.IOException;
import java.io.InputStream;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;
import javax.microedition.khronos.opengles.GL11Ext;

import com.electrofear.*;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
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
    private ObjectManager mDrawQueue;
    private float mScaleX;
    private float mScaleY;
    
    public GameRenderer(Context context, Game game, int gameWidth,
            int gameHeight) {
        // TODO Auto-generated constructor stub
        mContext = context;
        mGame = game;
        mWidth = gameWidth;
        mHeight = gameHeight;
    }

    @Override
    /*
     * On Draw Frame - Draw Bitmaps here
     */
    public void onDrawFrame(GL10 gl) {
        long time = SystemClock.uptimeMillis();
        long time_delta = (time - mLastTime);
        
        if (mDrawQueue != null) {
            gl.glShadeModel(GL10.GL_FLAT);
            gl.glEnable(GL10.GL_BLEND);
            gl.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE_MINUS_SRC_ALPHA);
            gl.glColor4x(0x10000, 0x10000, 0x10000, 0x10000);
    
            gl.glMatrixMode(GL10.GL_PROJECTION);
            gl.glPushMatrix();
            gl.glLoadIdentity();
            gl.glOrthof(0.0f, mWidth, 0.0f, mHeight, 0.0f, 1.0f);
            gl.glMatrixMode(GL10.GL_MODELVIEW);
            gl.glPushMatrix();
            gl.glLoadIdentity();
            gl.glEnable(GL10.GL_TEXTURE_2D);
    
            //DRAW STUFF HERE!
            //Get objects from DRAWING QUEUE
            //also call draw function!
            // TODO DO ABOVE TWO
            
            //END OF DRAWING STUFF HERE
            gl.glBindTexture(GL10.GL_TEXTURE_2D, 1);
            ((GL11Ext) gl).glDrawTexfOES(0, 0, 0, 330, 486);
            
            
            gl.glDisable(GL10.GL_BLEND);
            gl.glMatrixMode(GL10.GL_PROJECTION);
            gl.glPopMatrix();
            gl.glMatrixMode(GL10.GL_MODELVIEW);
            gl.glPopMatrix();        
        }
        else {
            gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        }
        
    }
    
    public synchronized void setDrawQueue(ObjectManager queue, float cameraX, float cameraY) {
        mDrawQueue = queue;
        this.cameraX = cameraX;
        this.cameraY = cameraY;
        //TODO: synchronize the queue or use a draw lock!
    }
    
    //ANOTHER RENDERER FUNCTION, ON SURFACECHANGED MEANS
    @Override
    public void onSurfaceChanged(GL10 gl, int w, int h) {
        //mWidth = w;0
        //mHeight = h;
        // ensure the same aspect ratio as the game
        float scaleX = (float)w / mWidth;
        float scaleY =  (float)h / mHeight;
        final int viewportWidth = (int)(mWidth * scaleX);
        final int viewportHeight = (int)(mHeight * scaleY);
        gl.glViewport(0, 0, viewportWidth, viewportHeight);
        mScaleX = scaleX;
        mScaleY = scaleY;

        
        float ratio = (float) mWidth / mHeight;
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glFrustumf(-ratio, ratio, -1, 1, 1, 10);
        
        
        mGame.onSurfaceReady();
        
    }

    @Override
    //INITIALIZE OPENGL HERE!
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        // Initialize GL
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);

        gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
        gl.glShadeModel(GL10.GL_FLAT);
        gl.glDisable(GL10.GL_DEPTH_TEST);
        gl.glEnable(GL10.GL_TEXTURE_2D);   
        
        gl.glDisable(GL10.GL_DITHER);
        gl.glDisable(GL10.GL_LIGHTING);
        
        gl.glTexEnvx(GL10.GL_TEXTURE_ENV, GL10.GL_TEXTURE_ENV_MODE, GL10.GL_MODULATE);
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        // TODO call the mGame.onSurfaceCreated and then load textures
        // IMPORTANT BECAUSE textures can be deleted! NOTICE that if you leave app and come back
        // this function is called
        
        textureTest(gl);


    }
    
    public void textureTest(GL10 gl){
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

        
        int error = gl.glGetError();
        if (error != GL10.GL_NO_ERROR) {
            Log.e("SpriteMethodTest", "Texture Load GLError: " + error);
        }
    }
    
    public synchronized void waitDrawingComplete() {
    }

}
