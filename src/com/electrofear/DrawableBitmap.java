package com.electrofear;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11Ext;

public class DrawableBitmap extends DrawableObject {

    private ArrayList<Texture> drawableBitMapTexture;
    // Texture drawableBitMapTexture;
    int currentFrameNumber = 0;
    float width;
    float height;
    float angle;
    //float zAxis; // my favorite axis
    float xPosition;
    float yPosition;
    float mAlpha = 1.0f;

    // PRETTY MUCH defined as:
    // Full => will be affected by "lighting"
    // UI => user interface items will not be affected by "lighting"
    // Light => will NOT be affected by "lighting"
    // Auto sets to "Full"
    String drawableLightingType = "Full"; // Auto sets to opaque

    // For Shadows
    float shadowDifference;
    boolean ignoreShadows = false;

    // For Centering Image based on center coordinates
    boolean centerBasedOnImage = false;
    int priority = 0; // sets priority

    public float getShadowDifference() {
        return shadowDifference;
    }

    public DrawableBitmap(float x, float y, float inputWidth,
            float inputHeight, float inputAngle, float inputZAxis,
            Texture inputTexture) {
        width = inputWidth;
        height = inputHeight;
        zAxis = inputZAxis;
        drawableBitMapTexture = new ArrayList();
        drawableBitMapTexture.add(inputTexture);
        xPosition = x;
        yPosition = y;
        shadowDifference = 0.1f;
        angle = inputAngle;
    }

    public DrawableBitmap(float x, float y, float inputZAxis, float inputWidth,
                          float inputHeight, float inputAngle, 
                          String inputTextureName, int inputFrameNumber) {
        width = inputWidth;
        height = inputHeight;
        zAxis = inputZAxis;
        xPosition = x;
        yPosition = y;
        shadowDifference = 0.1f;
        angle = inputAngle;
        setTextures(inputTextureName, inputFrameNumber);
    }    
    
    public void setDrawableLightingType(String type) {
        if (type == "Full" || type == "UI" || type == "Light"
                || type == "Shadow") {
            drawableLightingType = type;
        }
    }

    public DrawableBitmap(Texture inputTexture) {
        width = 0;
        height = 0;
        zAxis = 0;
        shadowDifference = 0.1f;
        drawableBitMapTexture = new ArrayList();
        drawableBitMapTexture.add(inputTexture);
        xPosition = 0;
        yPosition = 0;
        angle = 0;
    }

    public DrawableBitmap(Texture inputTexture, boolean ignoreShadow) {
        width = 0;
        height = 0;
        zAxis = 0;
        shadowDifference = 0.1f;
        this.ignoreShadows = ignoreShadow;
        drawableBitMapTexture = new ArrayList();
        drawableBitMapTexture.add(inputTexture);
        xPosition = 0;
        yPosition = 0;
        angle = 0;
    }

    public DrawableBitmap(Texture inputTexture, float inputShadowDifference) {
        width = 0;
        height = 0;
        zAxis = 0;
        shadowDifference = inputShadowDifference;
        drawableBitMapTexture = new ArrayList();
        drawableBitMapTexture.add(inputTexture);
        xPosition = 0;
        yPosition = 0;
        angle = 0;
    }

    @Override
    public void updateCoords(float calculatedX, float calculatedY, float width,
            float height, float angle, float zAxis) {
        // TODO Auto-generated method stub
        xPosition = calculatedX;
        yPosition = calculatedY;
        this.width = width;
        this.height = height;
        this.angle = angle;
        this.zAxis = zAxis;
        this.centerBasedOnImage = true;
    }
    
    public void updateDrawableFrame(int inputFrame) {
        if (inputFrame < drawableBitMapTexture.size()) {
            currentFrameNumber = inputFrame;
        } else {
            currentFrameNumber = drawableBitMapTexture.size() - 1;
        }
    }
    
    public void updateAlphaValue(float inputAlpha) {
        if (inputAlpha < 0.0f) {
            mAlpha = 0.0f;
        } else if (inputAlpha > 1.0f) {
            mAlpha = 1.0f;
        } else {
            mAlpha = inputAlpha;
        }
    }

    public void updateCoords(float calculatedX, float calculatedY, float width,
            float height, float angle) {
        // TODO Auto-generated method stub
        xPosition = calculatedX;
        yPosition = calculatedY;
        this.width = width;
        this.height = height;
        this.angle = angle;
        this.centerBasedOnImage = true;
    }
    
    public void setTextures(String textureName, int numberOfFrames) {
        int imageId = 0;

        // Checks
        if (drawableBitMapTexture == null) {
            drawableBitMapTexture = new ArrayList();
        } else if (drawableBitMapTexture.size() > 0) {
            drawableBitMapTexture.clear();
        }

        for (int i = 1; i < numberOfFrames + 1; i++) {
            imageId = BaseObject.contextParameters.context.getResources()
                    .getIdentifier(textureName + "_" + i, "drawable",
                            "com.electrofear");
            if (imageId > 0) {
                drawableBitMapTexture.add(BaseObject.mapLibrary.addTextureToLibrary(imageId));
            }
        }
    }    

    public void startDrawing(GL10 gl) {

        // Set Base Color to which texture will be drawn over, this will
        // simulate Day/Night cycle
        if (this.drawableLightingType == "Full") {
            gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
            // gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
            // gl.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            gl.glColor4f(BaseObject.lightSystem.red / 255f,
                    BaseObject.lightSystem.green / 255f,
                    BaseObject.lightSystem.blue / 255f, mAlpha);
        } else if (this.drawableLightingType == "Light") {
            gl.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE_MINUS_SRC_ALPHA);
            // gl.glColor4f(BaseObject.lightSystem.currentLightAlpha/255f,
            // BaseObject.lightSystem.currentLightAlpha/255f,
            // BaseObject.lightSystem.currentLightAlpha/255f,
            // BaseObject.lightSystem.currentLightAlpha/255f);
            gl.glColor4f(1.0f, 1.0f, 1.0f, mAlpha);
            // BaseObject.lightSystem.currentLightAlpha/255f
        } else if (this.drawableLightingType == "Shadow") {
            gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
            // gl.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            gl.glColor4f(BaseObject.lightSystem.currentShadowAlpha / 255f,
                    BaseObject.lightSystem.currentShadowAlpha / 255f,
                    BaseObject.lightSystem.currentShadowAlpha / 255f,
                    BaseObject.lightSystem.currentShadowAlpha / 255f);
        }
        

        gl.glBindTexture(GL10.GL_TEXTURE_2D, drawableBitMapTexture.get(currentFrameNumber).nameId); // nameId
                                                                            // is
                                                                            // textureID
                                                                            // for
                                                                            // opengl
        // if (centerBasedOnImage){

        // SetupCoordinates and such
        //
        float vertices[] = { -width / 2, -height / 2, zAxis, // Bottom Left
                width / 2, -height / 2, zAxis, // Bottom Right
                -width / 2, height / 2, zAxis, // Top Left
                width / 2, height / 2, zAxis // Top Right
        };
        ByteBuffer byteBuf = ByteBuffer.allocateDirect(vertices.length * 4);
        byteBuf.order(ByteOrder.nativeOrder());
        FloatBuffer vertexBuffer = byteBuf.asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);

        float texture[] = { 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f };
        byteBuf = ByteBuffer.allocateDirect(texture.length * 4);
        byteBuf.order(ByteOrder.nativeOrder());
        FloatBuffer textureBuffer = byteBuf.asFloatBuffer();
        textureBuffer.put(texture);
        textureBuffer.position(0);

        byte indices[] = {
                // Faces definition
                0, 1, 3, 0, 3, 2 };

        ByteBuffer indexBuffer = ByteBuffer.allocateDirect(indices.length);
        indexBuffer.put(indices);
        indexBuffer.position(0);

        gl.glPushMatrix();
        //
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        // gl.glVertexPointer(3, GL10.GL_FLOAT, 0, markerBuffer);
        // gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, NUMERO_VERTICI);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, textureBuffer);
        // Draw the vertices as triangles, based on the Index Buffer information

        gl.glTranslatef(xPosition, yPosition, 0);
        gl.glRotatef(angle, 0, 0, 1);
        gl.glDrawElements(GL10.GL_TRIANGLES, indices.length,
                GL10.GL_UNSIGNED_BYTE, indexBuffer);

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        // ((GL11Ext) gl).glDrawTexfOES(-width/2, -height/2, 0, width, height);

        gl.glPopMatrix();
        // } else {
        // ((GL11Ext) gl).glDrawTexfOES(xPosition, yPosition, 0, width, height);
        // }

        /*
         * gl.glDisable(GL10.GL_BLEND); gl.glMatrixMode(GL10.GL_PROJECTION);
         * gl.glPopMatrix(); gl.glMatrixMode(GL10.GL_MODELVIEW);
         * gl.glPopMatrix();
         */
    }

}
