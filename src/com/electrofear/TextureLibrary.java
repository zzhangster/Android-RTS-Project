package com.electrofear;

import java.io.IOException;
import java.io.InputStream;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;
import javax.microedition.khronos.opengles.GL11Ext;





import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

public class TextureLibrary {
    public static int TEXTURE_SIZE = 1024;
    Texture[] mTextureLibraryHash;
    public static int currentIndexSize = 0;
    
    public TextureLibrary(){
        mTextureLibraryHash = new Texture[TEXTURE_SIZE];
        for (int i = 0; i < mTextureLibraryHash.length; i++) {
            mTextureLibraryHash[i] = new Texture();
        }
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
    
    //Initial LOAD INTO OPENGL
    //Get a texture, find the "rawResource" which is R.drawable something in the Texture Object
    //and then add it into the opengl, note that mTextureID is the id that opengl refers to
    //we use this id and place it in Texture so that the texture knows the actual index for the
    //openGL -- refactor
    protected Texture loadBitmap(Context mContext, GL10 gl, Texture texture) {
        //Texture Test
        if (!texture.finishedLoading){
            int[] textures = new int[1];
            int[] mCropWorkspace = new int[4];
            gl.glGenTextures(1, textures, 0);
        
            int mTextureID = textures[0]; //OPENGL texture ID!
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
}
