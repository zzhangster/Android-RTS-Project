package com.electrofear;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11Ext;

public class DrawableBitmap extends DrawableObject{

    Texture drawableBitMapTexture;
    float width;
    float height;
    float angle;
    float zAxis; //my favorite axis
    float xPosition;
    float yPosition;
    float shadowDifference;
    boolean centerBasedOnImage = false;
    int priority = 0; //sets priority
    
    public float getShadowDifference(){
    	return shadowDifference;
    }
    
    public DrawableBitmap(float x, float y, float inputWidth, float inputHeight, float inputAngle, float inputZAxis, Texture inputTexture){
        width = inputWidth;
        height = inputHeight;
        zAxis = inputZAxis;
        drawableBitMapTexture = inputTexture;
        xPosition = x;
        yPosition = y;
        shadowDifference = 0.1f;
        angle = inputAngle;
    }
    
    public DrawableBitmap(Texture inputTexture){
        drawableBitMapTexture = inputTexture;
        width = 0;
        height = 0;
        zAxis = 0;
        shadowDifference = 0.1f;
        drawableBitMapTexture = inputTexture;
        xPosition = 0;
        yPosition = 0;
        angle = 0;        
    }    
    
    public DrawableBitmap(Texture inputTexture, float inputShadowDifference){
        drawableBitMapTexture = inputTexture;
        width = 0;
        height = 0;
        zAxis = 0;
        shadowDifference = inputShadowDifference;
        drawableBitMapTexture = inputTexture;
        xPosition = 0;
        yPosition = 0;
        angle = 0;        
    }
    
    @Override
	public void updateCoords( float calculatedX, 
                              float calculatedY,
                              float width, 
                              float height, 
                              float angle, 
                              float zAxis) {
        // TODO Auto-generated method stub
        xPosition = calculatedX;
        yPosition = calculatedY;
        this.width = width;
        this.height = height;
        this.angle = angle;
        this.zAxis = zAxis;
        this.centerBasedOnImage = true;
    }
    
	public void updateCoords( float calculatedX, 
            float calculatedY,
            float width, 
            float height, 
            float angle) {
	// TODO Auto-generated method stub
	xPosition = calculatedX;
	yPosition = calculatedY;
	this.width = width;
	this.height = height;
	this.angle = angle;
	this.centerBasedOnImage = true;
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
        //if (centerBasedOnImage){
            
            //SetupCoordinates and such
            //
            float vertices[] = { 
                    -width/2, -height/2, zAxis,     //Bottom Left
                    width/2, -height/2, zAxis,      //Bottom Right
                    -width/2, height/2, zAxis,      //Top Left
                    width/2, height/2, zAxis        //Top Right
                                    };
            ByteBuffer byteBuf = ByteBuffer.allocateDirect(vertices.length * 4);
            byteBuf.order(ByteOrder.nativeOrder());
            FloatBuffer vertexBuffer = byteBuf.asFloatBuffer();
            vertexBuffer.put(vertices);
            vertexBuffer.position(0);            
            
            float texture[] = {  
                    0.0f, 0.0f,
                    1.0f, 0.0f,
                    0.0f, 1.0f,
                    1.0f, 1.0f
            };
            byteBuf = ByteBuffer.allocateDirect(texture.length * 4);
            byteBuf.order(ByteOrder.nativeOrder());
            FloatBuffer textureBuffer = byteBuf.asFloatBuffer();
            textureBuffer.put(texture);
            textureBuffer.position(0);            
 
            byte indices[] = {
                    //Faces definition
                    0,1,3, 0,3,2};
            
            ByteBuffer indexBuffer = ByteBuffer.allocateDirect(indices.length);
            indexBuffer.put(indices);
            indexBuffer.position(0);            
            
            gl.glPushMatrix();

                gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
                gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
                //gl.glVertexPointer(3, GL10.GL_FLOAT, 0, markerBuffer);
                //gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, NUMERO_VERTICI);
                gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
                gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, textureBuffer);
                //Draw the vertices as triangles, based on the Index Buffer information
                
                gl.glTranslatef(xPosition, yPosition, 0);
                gl.glRotatef(angle, 0, 0, 1);   
                gl.glDrawElements(GL10.GL_TRIANGLES, indices.length, GL10.GL_UNSIGNED_BYTE, indexBuffer);                
                
                gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
                gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
                //((GL11Ext) gl).glDrawTexfOES(-width/2, -height/2, 0, width, height);

                        
            gl.glPopMatrix();
        //} else {
        //    ((GL11Ext) gl).glDrawTexfOES(xPosition, yPosition, 0, width, height);
        //}
        
        /*gl.glDisable(GL10.GL_BLEND);
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glPopMatrix();
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glPopMatrix();*/
    }
    
}