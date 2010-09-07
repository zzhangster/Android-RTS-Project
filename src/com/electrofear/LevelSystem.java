package com.electrofear;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import com.electrofear.GameObjectFactory.GameObjectType;
import com.electrofear.level.GlobalLevelProperties;
import com.electrofear.parser.GlobalDataGraphic;
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
        
        
        
        GameObject terrainObject;
        RenderComponent terrainRenderComponent;
        RenderComponent shadowRenderComponent;
        DrawableBitmap terrainDrawable, shadowTerrainDrawable;
        GlobalTerrainObj tempTerrainObj;
       
        //values are from LoadLevel function and parsing
        GlobalLevelProperties levelProperties = BaseObject.globalLevelProperties;
        int terrainObjResourceId;
        GlobalDataGraphic globalTerrainObjProperty;
        for (int i = 0; i < levelProperties.terrainObjects.size(); i++) {
        	tempTerrainObj = levelProperties.terrainObjects.get(i);
	        terrainObject = new GameObject();
	        terrainRenderComponent = new RenderComponent();
	        
	        
	        
	        //Get the image Id from globalxmldata
	        globalTerrainObjProperty = BaseObject.contextGlobalXMLData.getGraphicById(tempTerrainObj.getObjType());
	        terrainObjResourceId = BaseObject.contextParameters.context.getResources().getIdentifier(globalTerrainObjProperty.image, "drawable", "com.electrofear");
	        //Create BackGround DrawableBitmap and add to RenderComponent
	        //Z-Axis of 1 is lowest (drawn first)
	        //MAPS STARTS AT (0, 0), since drawable starts drawing from center of image, we move the center to half width/half height
	        terrainDrawable = new DrawableBitmap(tempTerrainObj.positionX, 
	        									tempTerrainObj.positionY, 
	        									(float) globalTerrainObjProperty.width, 
	        									(float) globalTerrainObjProperty.height, 
	        									tempTerrainObj.angle, 
	        									tempTerrainObj.positionZ, 
	        									BaseObject.mapLibrary.addTextureToLibrary(terrainObjResourceId));
			terrainRenderComponent.setDrawable(terrainDrawable);
			terrainObject.add(terrainRenderComponent);
	        
	        //SHADOWS
	        Vector2 tempShadowPosition;
	        shadowRenderComponent = new RenderComponent();
	        terrainObjResourceId = BaseObject.contextParameters.context.getResources().getIdentifier(globalTerrainObjProperty.image + "_shadow", "drawable", "com.electrofear");
	        //if shadow create rendercomponent and drawable shadowTerrainDrawable
	        if (terrainObjResourceId > 0) {
	        	tempShadowPosition = this.calculateShadowPositions(tempTerrainObj.positionX, tempTerrainObj.positionY, tempTerrainObj.getShadowMultipler());
	        	shadowTerrainDrawable = new DrawableBitmap(tempShadowPosition.x, 
			        									tempShadowPosition.y, 
														(float) globalTerrainObjProperty.width, 
														(float) globalTerrainObjProperty.height, 
														tempTerrainObj.angle, 
														tempTerrainObj.positionZ - 1.0f, //subtraction so that shadow is a litle lower than actual objects but higher than others
														BaseObject.mapLibrary.addTextureToLibrary(terrainObjResourceId));
	        	shadowRenderComponent.setDrawable(shadowTerrainDrawable);
				terrainObject.add(shadowRenderComponent);	        	
	        }
	        
	        
	        root.add(terrainObject);
        }
        

        //LOAD ALL MAP FAUNAS ETC HERE!
        
        
        
        //Create Objects, temporary solution here! HERE how's to create objects, put this somewhere else later!
        //mGameObject = BaseObject.objFactory.spawn(GameObjectType.NACSCOUT, 50, 80); //Generate NACSCOUT at position 30, 30
        //root.add(mGameObject);
        //
        //mGameObject = BaseObject.objFactory.spawn(GameObjectType.NACSCOUT, 50, 200); //Generate NACSCOUT at position 30, 30
        //root.add(mGameObject);
        
        mGameObject = BaseObject.objFactory.spawn(GameObjectType.NACHEAVYTANK, 250, 250, true); //Generate NACSCOUT at position 30, 30
        root.add(mGameObject);
        
        mGameObject = BaseObject.objFactory.spawn(GameObjectType.NACHEAVYTANK, 100, 90, false); //Generate NACSCOUT at position 30, 30
        root.add(mGameObject);     
        
        mGameObject = BaseObject.objFactory.spawn(GameObjectType.NACSCOUT, 250, 90, false); //Generate NACSCOUT at position 30, 30
        root.add(mGameObject);   
       
        //mGameObject = BaseObject.objFactory.spawn(GameObjectType.NACWALKER, 190, 230); //Generate NACSCOUT at position 30, 30
        //root.add(mGameObject);
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
