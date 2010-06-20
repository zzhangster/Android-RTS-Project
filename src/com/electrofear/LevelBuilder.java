package com.electrofear;







public class LevelBuilder {

    public GameObject buildBackground(int backgroundImage, int levelWidth, int levelHeight) {
        // TODO CREATE BACKGROUND HERE FOR GAME
        //RenderComponent backgroundRender = new RenderComponent();
        GameObject background = new GameObject();
        RenderComponent backgroundRender = new RenderComponent();
        
        int backgroundResource = R.drawable.back_ground_test;
        
        background.add(backgroundRender);
        return background;
    }

}
