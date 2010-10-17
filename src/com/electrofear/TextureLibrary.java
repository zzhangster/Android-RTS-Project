package com.electrofear;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;
import javax.microedition.khronos.opengles.GL11Ext;

import com.electrofear.parser.GlobalDataGraphic;
import com.electrofear.parser.GlobalDataGraphicAnimation;





import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

public class TextureLibrary {
    public static int TEXTURE_SIZE = 1024;
    Texture[] mTextureLibraryHash;
   
    int[] texturePlaceHolder;
    
    public static int currentIndexSize = 0;
    
    public TextureLibrary(){
        mTextureLibraryHash = new Texture[TEXTURE_SIZE];
        for (int i = 0; i < mTextureLibraryHash.length; i++) {
            mTextureLibraryHash[i] = new Texture();
        }
        texturePlaceHolder = new int[1];
    }
    
    //Loads a specific texture from R.drawable id
    //Returns a texture, if nothing found then add texture and 
    protected Texture loadTexture(Context context, GL10 gl, int rawResourceID){
        if (rawResourceID > -1){
            for (int i = 0; i < currentIndexSize; i++){
                if (mTextureLibraryHash[i].rawResourceId == rawResourceID){
                    if(mTextureLibraryHash[i].rawResourceId > -1 && !mTextureLibraryHash[i].finishedLoading) {
                        return loadBitmap(context, gl, mTextureLibraryHash[i]);
                    }
                }
            }
            //If nothing found, add texture and then load it
            addTextureToLibrary(rawResourceID);
            return loadTexture(context, gl, rawResourceID);
        }
        else {
            return null;
        }
    }
    
    //Load Preliminary textures into arrays
    //Does not load the actual textures
    protected void loadStaticTextureData() {
        //Loads all graphics and animation graphics into texture library
        ArrayList<GlobalDataGraphic> tempGraphic = BaseObject.contextGlobalXMLData.getAllGraphicStaticData();
        ArrayList<GlobalDataGraphicAnimation> tempGraphicAnimation = BaseObject.contextGlobalXMLData.getAllAnimationGraphicStaticData();
        
        int resID = 0;
        for (int i = 0; i < tempGraphic.size(); i++) {
            resID = BaseObject.contextParameters.context.getResources().getIdentifier(tempGraphic.get(i).image, "drawable", "com.electrofear");
            if (resID > 0) {
                addTextureToLibrary(resID);
            }
        }
        
        //Load All Animation Textures
        for (int i = 0; i < tempGraphicAnimation.size(); i++) {
            if (tempGraphicAnimation.get(i).imageCount == 1) {
                resID = BaseObject.contextParameters.context.getResources().getIdentifier(tempGraphicAnimation.get(i).imageBase, "drawable", "com.electrofear");
                if (resID > 0) {
                    addTextureToLibrary(resID);
                }
            } else {
                //For image count > 1, we except the imageBase String + frame #   EX: anim_gfx_fir_type_01_frame is "imageBase" and "_1" is frame number
                for (int j = 1; j < tempGraphicAnimation.get(i).imageCount + 1; j++) {
                    resID = BaseObject.contextParameters.context.getResources().getIdentifier(tempGraphicAnimation.get(i).imageBase + "_" + j, "drawable", "com.electrofear");
                    if (resID > 0) {
                        addTextureToLibrary(resID);
                    }                   
                }             
            }
        }        
        
    }
    
    
    //Loads all textures, checks if those are loaded and those that are not
    protected void loadAllTextures(Context context, GL10 gl) {
        for (int i = 0; i < currentIndexSize; i++){
            if(mTextureLibraryHash[i].rawResourceId > -1 && !mTextureLibraryHash[i].finishedLoading) {
                loadBitmap(context, gl, mTextureLibraryHash[i]);
            }
        }
    }

    //Add texture, return old texture if already added
    //resourceID is the R.drawable id and will be used to check if texture if here
    public Texture addTextureToLibrary(int resourceID){
        if (resourceID > -1) {
            for (int i = 0; i < currentIndexSize; i++){
                if (mTextureLibraryHash[i].rawResourceId == resourceID){
                    return mTextureLibraryHash[i];
                }
            }
            //If nothing is found create new texture
            //Also checks if over limit, then we add one
            if (currentIndexSize > TEXTURE_SIZE){
                TEXTURE_SIZE = currentIndexSize;
                mTextureLibraryHash[TEXTURE_SIZE] = new Texture();
            }
            Texture newTexture = new Texture();
            newTexture.setResourceID(resourceID);
            //newTexture = loadBitmap(mContext, gl, newTexture); 
            mTextureLibraryHash[currentIndexSize] = newTexture;
            currentIndexSize++;
            return newTexture;
        }
        else {
            return null; // nope nothing here, bad resource id
        }
    }
    
    /* Reset All Textures in the Buffer so that we can load later */
    public void resetTextures() {
        for (int i = 0; i < currentIndexSize; i++){
            if(mTextureLibraryHash[i] != null) {
                mTextureLibraryHash[i].restLoading();
            }
        }    	
    }
    
    //Initial LOAD INTO OPENGL
    //Get a texture, find the "rawResource" which is R.drawable something in the Texture Object
    //and then add it into the opengl, note that mTextureID is the id that opengl refers to
    //we use this id and place it in Texture so that the texture knows the actual index for the
    //openGL -- refactor
    protected Texture loadBitmap(Context mContext, GL10 gl, Texture texture) {
        //Texture Test
        if (!texture.finishedLoading){
            
            int[] mCropWorkspace = new int[4];
            gl.glGenTextures(1, texturePlaceHolder, 0);
        
            int mTextureID = texturePlaceHolder[0]; //OPENGL texture ID!
            gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextureID);
        
            gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
            gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
        
            gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_CLAMP_TO_EDGE);
            gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_CLAMP_TO_EDGE);
        
            gl.glTexEnvf(GL10.GL_TEXTURE_ENV, GL10.GL_TEXTURE_ENV_MODE, GL10.GL_MODULATE);
        
            InputStream is = mContext.getResources().openRawResource(texture.rawResourceId);
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

            
            //Fill Texture Object!
            texture.nameId = mTextureID;
            texture.width = bitmap.getWidth();
            texture.height = bitmap.getHeight();
            texture.finishedLoading = true;
        }
        
        return texture;
    }
    
    public void removeAll(GL10 gl) {
    	for (int x= 0; x < mTextureLibraryHash.length; x++) {
    		if (mTextureLibraryHash[x].rawResourceId != -1) {
    			texturePlaceHolder[0] = mTextureLibraryHash[x].nameId;
    			gl.glDeleteTextures(1, texturePlaceHolder, 0);
    		}
    	}
    	
    }
}
