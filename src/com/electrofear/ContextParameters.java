package com.electrofear;

import android.content.Context;

public class ContextParameters {

    public int gameWidth;
    public int gameHeight;
    public int viewWidth;
    public int viewHeight;
    public float viewScaleX;
    public float viewScaleY;
    public Context context;
    public ObjectManager gameRoot; //For other function to access, will need gameRoot for objects to draw
}
