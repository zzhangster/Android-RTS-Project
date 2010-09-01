package com.electrofear;







public class LevelBuilder {

    public GameObject buildBackground(int backgroundImage, int levelWidth, int levelHeight) {
        // TODO CREATE BACKGROUND HERE FOR GAME
        //RenderComponent backgroundRender = new RenderComponent();
        GameObject background = new GameObject();
        RenderComponent backgroundRender = new RenderComponent();
        
        
        int backgroundResource = R.drawable.back_ground_test;
        //Create BackGround DrawableBitmap and add to RenderComponent
        //Z-Axis of 1 is lowest (drawn first)
        DrawableBitmap backGroundDrawable = new DrawableBitmap(0, 0, levelWidth, levelHeight, 0, 0, BaseObject.mapLibrary.addTextureToLibrary(backgroundResource));
        backgroundRender.setDrawable(backGroundDrawable);
        background.add(backgroundRender);
        
        //Setup Camera Restrictions
        BaseObject.cameraSystem.setBoundaryBox(0, 0, levelWidth, levelHeight);
        
        return background;
    }

}
