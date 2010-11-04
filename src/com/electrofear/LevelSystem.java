package com.electrofear;

import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import com.electrofear.GameObjectFactory.GameObjectType;
import com.electrofear.GameParticleEffectObjectFactory.GameParticleObjectType;
import com.electrofear.level.GlobalLevelProperties;
import com.electrofear.parser.GlobalDataGraphic;
import com.electrofear.parser.GlobalDataVehicle;
import com.electrofear.parser.GlobalTerrainObj;





public class LevelSystem {

    public GameObject mBackgroundObject;
    public GameObject mGameObject; //holds actual items       
    
    public void loadLevel(int level) {
        // TODO Auto-generated method stub
    	
    	//LOADING LEVEL AND CORRESPONDING VALUES TO GLOBAL
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            int resID = BaseObject.contextParameters.context.getResources().getIdentifier("map_level_"+level, "raw", "com.electrofear");
            
            //Parse Tank Data
            InputStream in = BaseObject.contextParameters.context.getResources().openRawResource(resID);
            Document dom = builder.parse(in);
            XMLParser.startParsingLevel(dom);
            
            //Parse Infantry Data
        } catch (Exception e) {
            throw new RuntimeException(e);
        } 
    	
    	
        ObjectManager root = BaseObject.contextParameters.gameRoot;
        LevelBuilder builder = BaseObject.levelBuilder; // for experimenting
        //mBackgroundObject = builder.buildBackground(0,330,972); // Replace with real values
        
        //Build Actual Map Background
        mBackgroundObject = buildLevel(0,330,972);
        root.add(mBackgroundObject);
        //Build Terrain Objects
        
        
        /////////////////////////////
        //BUILD ALL TERRAIN OBJECTS//
        /////////////////////////////
        GameObject terrainObject;
        RenderComponent terrainRenderComponent;
        RenderComponent shadowRenderComponent;
        DrawableBitmap terrainDrawable, shadowTerrainDrawable;
        GlobalTerrainObj tempInfoTerrainObj;
       
        //values are from LoadLevel function and parsing
        GlobalLevelProperties levelProperties = BaseObject.globalLevelProperties;
        int terrainObjResourceId;
        GlobalDataGraphic globalTerrainObjProperty;
        for (int i = 0; i < levelProperties.terrainObjects.size(); i++) {
        	tempInfoTerrainObj = levelProperties.terrainObjects.get(i);
        	terrainObject = new GameObject();
        	terrainObject.setPosition(tempInfoTerrainObj.positionX, tempInfoTerrainObj.positionY);        	
        	spawnTerrainObject(terrainObject, tempInfoTerrainObj );
        	root.add(terrainObject);
        }
        

        //LOAD ALL MAP FAUNAS ETC HERE!
        
        
        
        //Create Objects, temporary solution here! HERE how's to create objects, put this somewhere else later!
        //mGameObject = BaseObject.objFactory.spawn(GameObjectType.NACSCOUT, 50, 80); //Generate NACSCOUT at position 30, 30
        //root.add(mGameObject);
        //
        //mGameObject = BaseObject.objFactory.spawn(GameObjectType.NACSCOUT, 50, 200); //Generate NACSCOUT at position 30, 30
        //root.add(mGameObject);
        
        //LOAD ALL STATIC DATA TO TEXTURE!
        BaseObject.mapLibrary.loadStaticTextureData();
        
        mGameObject = BaseObject.objFactory.spawn(GameObjectType.NACHEAVYTANK, 250, 90, 90, true); //Generate NACSCOUT at position 30, 30
        root.add(mGameObject);
        
        mGameObject = BaseObject.objFactory.spawn(GameObjectType.AKAHEAVYTANK, 100, 1300, 270, true); //Generate NACSCOUT at position 30, 30
        root.add(mGameObject);     
        
        mGameObject = BaseObject.objFactory.spawn(GameObjectType.NACSCOUT, 250, 250, 90, false); //Generate NACSCOUT at position 30, 30
        root.add(mGameObject);   

        
        //SET TO 21 and 22 because fauna "TREES" is currently set to 20 in map_level_1.xml; will need to change this
        mGameObject = BaseObject.objParticleFactory.spawn(GameParticleObjectType.FIRE_01, 100, 450, 22);
        root.add(mGameObject);        
        
        mGameObject = BaseObject.objParticleFactory.spawn(GameParticleObjectType.SMOKE_01, 100, 450, 21);
        root.add(mGameObject);
        //mGameObject = BaseObject.objFactory.spawn(GameObjectType.NACWALKER, 190, 230); //Generate NACSCOUT at position 30, 30
        //root.add(mGameObject);
    }
    
    private void spawnTerrainObject(GameObject mParentObjManager, GlobalTerrainObj objInfo) {
    	GlobalDataGraphic globalTerrainObjProperty = BaseObject.contextGlobalXMLData.getGraphicById(objInfo.getObjType());

        int resID = BaseObject.contextParameters.context.getResources().getIdentifier(globalTerrainObjProperty.image, "drawable", "com.electrofear");
        //int resID = Resources.getSystem().getIdentifier(globalTankGraphicProperties.image, "drawable", "com.electrofear");
        
        //SHADOW ITEMSs
        int resShadowID = BaseObject.contextParameters.context.getResources().getIdentifier(globalTerrainObjProperty.image + "_shadow", "drawable", "com.electrofear");
        DrawableBitmap shadowDrawable;
        if (resShadowID > 0) {
        	shadowDrawable = new DrawableBitmap(BaseObject.mapLibrary.addTextureToLibrary(resShadowID));
        	shadowDrawable.setDrawableLightingType("Shadow");
        } else {
        	shadowDrawable = null;
        }
        //LIGHT ITEMS
        int resLightID = BaseObject.contextParameters.context.getResources().getIdentifier(globalTerrainObjProperty.image + "_light", "drawable", "com.electrofear");
        DrawableBitmap lightDrawable;
        if (resLightID > 0) {
        	lightDrawable = new DrawableBitmap(BaseObject.mapLibrary.addTextureToLibrary(resLightID));
        	lightDrawable.setDrawableLightingType("Light");
        } else {
        	lightDrawable = null;
        }
        
        
        //CREATE GRAPHIC PORTION AND FACTOR SHADOW MULTIPLIERS
        GameTerrainObject baseTerrainObj = new GameTerrainObject(   "Terrain",
		        													"00001",
		        													"",
		                                                            (float)globalTerrainObjProperty.width, 
		                                                            (float)globalTerrainObjProperty.height,
		                                                            0, //relative X
		                                                            0, //relative Y
		                                                            objInfo.angle,
		                                                            objInfo.positionZ,
		                                                            false,
		                                                            new DrawableBitmap(BaseObject.mapLibrary.addTextureToLibrary(resID), true),
		                                                            shadowDrawable, 
		                                                            lightDrawable);
        
        baseTerrainObj.setShadowMultiplier(objInfo.shadowMultipler);
        //CREATE GRAPHIC PORTION AND FACTOR SHADOW MULTIPLIERS
        mParentObjManager.add(baseTerrainObj);
    }      
    
    //X, Y, multipler is for longer shadow (give illussion of taller object, ususally be set to 1 but can use other values
    private Vector2 calculateShadowPositions(float x, float y, float multiplier){
    	float shadowX,shadowY;
    	float unitLength;
    	
    	unitLength = (float) Math.sqrt( BaseObject.directionShadow.x * BaseObject.directionShadow.x + BaseObject.directionShadow.y * BaseObject.directionShadow.y);
    	
    	shadowX = x + (BaseObject.directionShadow.x / unitLength) * BaseObject.heightShadow*multiplier;
    	shadowY = y + (BaseObject.directionShadow.y / unitLength) * BaseObject.heightShadow*multiplier;
    	
    	return new Vector2(shadowX, shadowY);
    }
    
    public GameObject buildLevel(int backgroundImage, int levelWidth, int levelHeight){
        // TODO CREATE BACKGROUND HERE FOR GAME
        //RenderComponent backgroundRender = new RenderComponent();
        GameObject background = new GameObject();
        RenderComponent backgroundRender = new RenderComponent();
        
        //values are from LoadLevel function and parsing
        GlobalLevelProperties levelProperties = BaseObject.globalLevelProperties;
        
        
        int backgroundResource = BaseObject.contextParameters.context.getResources().getIdentifier(levelProperties.mapBackGroundImage, "drawable", "com.electrofear");
        
        //BACKGROUND IMAGE!
        backgroundResource = R.drawable.back_ground_level_1;
        //backgroundResource = R.drawable.back_ground_level_1;
        //Create BackGround DrawableBitmap and add to RenderComponent
        //Z-Axis of 1 is lowest (drawn first)
        //MAPS STARTS AT (0, 0), since drawable starts drawing from center of image, we move the center to half width/half height
        DrawableBitmap backGroundDrawable = new DrawableBitmap(levelProperties.mapWidth/2, levelProperties.mapHeight/2, levelProperties.mapWidth, levelProperties.mapHeight, 0, 0, BaseObject.mapLibrary.addTextureToLibrary(backgroundResource));
        backgroundRender.setDrawable(backGroundDrawable);
        background.add(backgroundRender);
        
        //Setup Camera Restrictions
        BaseObject.cameraSystem.updateCameraCoords(levelProperties.cameraPositionX, levelProperties.cameraPositionY, levelProperties.cameraPositionZ,
        										   levelProperties.cameraPositionX, levelProperties.cameraPositionY, 0);
        BaseObject.cameraSystem.setBoundaryBox(levelProperties.mapWidth/2, levelProperties.mapHeight/2, levelProperties.mapWidth, levelProperties.mapHeight);
        return background;
    	
    }

}
