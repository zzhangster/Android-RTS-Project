package com.electrofear;

import javax.microedition.khronos.opengles.GL10;

public class Texture {
    public int hashID; //holds the id to refer to in TextureLibrary (this is set by Texture Library)
    public int rawResourceId; // Holds the actual R.drawable.stuff id's
    public int nameId; // The actual hash id, as in what the openGL number will refer to texture, we use this in gl.glBindTexture(GL10.GL_TEXTURE_2D, <Texture name>);
    public int width;
    public int height;
    public boolean finishedLoading;

    public Texture(){
        hashID = -1;
        rawResourceId = -1;
        nameId = -1;
        finishedLoading = false;
    }
    
    public Texture(Texture texture) {
        // TODO Auto-generated constructor stub
        this.hashID = texture.hashID;
        this.rawResourceId = texture.rawResourceId;
        this.nameId = texture.nameId;
        this.width = texture.width;
        this.height = texture.height;
        this.finishedLoading = texture.finishedLoading;
    }
    
    public void setResourceID(int resourceID){
        this.rawResourceId = resourceID;
    }

    public void remove(){
        rawResourceId = -1;
        nameId = -1;
        width = 0;
        height = 0;
        finishedLoading = false;
    }
}
