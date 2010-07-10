package com.electrofear;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11Ext;

public class DrawableBitmap extends DrawableObject{

    Texture drawableBitMapTexture;
    float width;
    float height;
    float zAxis; //my favorite axis
    float xPosition;
    float yPosition;
    int priority; //sets priority
    
    public DrawableBitmap(float x, float y, float inputWidth, float inputHeight, float inputZAxis, int inputPriority, Texture inputTexture){
        width = inputWidth;
        height = inputHeight;
        zAxis = inputZAxis;
        priority = inputPriority;
        drawableBitMapTexture = inputTexture;
        xPosition = x;
        yPosition = y;
    }
    
    public void startDrawing(GL10 gl){
        /*gl.glShadeModel(GL10.GL_FLAT);
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
        gl.glEnable(GL10.GL_TEXTURE_2D);*/
    
        //DRAW STUFF HERE!
        //Get objects from DRAWING QUEUE
        //also call draw function!
        // TODO DO ABOVE TWO
        
        //END OF DRAWING STUFF HERE
        gl.glBindTexture(GL10.GL_TEXTURE_2D, drawableBitMapTexture.nameId); //nameId is textureID for opengl 
        ((GL11Ext) gl).glDrawTexfOES(xPosition, yPosition, 0, width, height);
        
        
        /*gl.glDisable(GL10.GL_BLEND);
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glPopMatrix();
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glPopMatrix();*/
    }
    
}
