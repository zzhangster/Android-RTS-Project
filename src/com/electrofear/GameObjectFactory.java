package com.electrofear;

import java.util.ArrayList;

import com.electrofear.parser.GlobalDataGraphic;
import com.electrofear.parser.GlobalDataGraphicAnimation;
import com.electrofear.parser.GlobalDataTurret;
import com.electrofear.parser.GlobalDataUnitSubTurretData;
import com.electrofear.parser.GlobalDataVehicle;


//SPAWN THE OBJECT, create GameObject (obj managers)
//and add RenderComponent with them,
//the rendercomponent has drawableBitmap, set to priority of following
//PRIORITY CHECKS => 1 is all base shadows
//                   2 is all actual ground objects
//                   3 is all turret shadows or things of equal height
//                   4 is all turret textures
//                  10 is all airborne shadows
//                  11 is all airborne objects

public class GameObjectFactory extends BaseObject {
    private static int MAX_OBJECTS = 200;
    public enum GameObjectType {
        INVALID(-1),
        
        PLAYER (0),
        
        // Collectables
        COIN (1),
        RUBY (2),
        DIARY (3),
        
        // Infantry Types
        ELISIANINFANTRYLIGHT (10),
        ELISIANINFANTRYHEAVY (11),
        ELISIANINFANTRYSUPPORT (12),
        ALKAZIANINFANTRYLIGHT (13),
        ALKAZIANINFANTRYHEAVY (26),
        ALKAZIANINFANTRYSUPPORT (27),
        NACINFANTRYLIGHT (28),
        NACINFANTRYHEAVY (29),
        NACINFANTRYSUPPORT (30),
        
        // Vehicles
        ELISIANHEAVYTANK (16),
        ELISIANAFV (17),
        ELISIANSCOUT (18),
        ALKAZIANHEAVYTANK (19),
        ALKAZIANAFV (20),
        ALKAZIANSCOUT (21),
        NACHEAVYTANK (22),
        NACAFV (23),
        NACSCOUT (24),

        ELISIANWALKER (6),
        ALKAZIANWALKER(7),
        NACWALKER(8),
        
        // Objects
        DOOR_RED (32),
        DOOR_BLUE (33),
        DOOR_GREEN (34),
        BUTTON_RED (35),
        BUTTON_BLUE (36),
        BUTTON_GREEN (37),
        CANNON (38),
        BROBOT_SPAWNER (39),
        BROBOT_SPAWNER_LEFT (40),
        BREAKABLE_BLOCK(41),
        THE_SOURCE(42),
        
        // Effects
        DUST(48),
        EXPLOSION_SMALL(49),
        EXPLOSION_LARGE(50),
        EXPLOSION_GIANT(51),
        
        
        // Special Spawnable
        DOOR_RED_NONBLOCKING (52),
        DOOR_BLUE_NONBLOCKING (53),
        DOOR_GREEN_NONBLOCKING (54),
        
        GHOST_NPC(55),
        
        CAMERA_BIAS(56),
        
        FRAMERATE_WATCHER(57),
        INFINITE_SPAWNER(58),
        
        SMOKE_BIG(59),
        SMOKE_SMALL(60),
        
        CRUSH_FLASH(61),
        FLASH(62),
        
        
        
        // Projectiles
        ENERGY_BALL(68),
        CANNON_BALL(65),
        TURRET_BULLET(66),
        BROBOT_BULLET(67),
        BREAKABLE_BLOCK_PIECE(68),
        BREAKABLE_BLOCK_PIECE_SPAWNER(69),
        WANDA_SHOT(70);
        
        private final int mIndex;
        GameObjectType(int index) {
            this.mIndex = index;
        }
    }
        
    public GameObject spawn(GameObjectType type, float x, float y, boolean testingTOBEREMOVED) {
        GameObject newObject = null;
        switch (type){
            case NACWALKER:
                newObject = spawnNACHeavyMech(x,y);
                break;
            case NACHEAVYTANK:
                newObject = spawnNACHeavyTank(x,y,1, testingTOBEREMOVED);
                break;
            case NACSCOUT:
                newObject = spawnNACScout(x,y);
                break;
            case NACAFV:
                newObject = spawnNACAFV(x,y);
                break;
            default:
                break;
        }
        return newObject;
    }

    private GameObject spawnNACScout(float x, float y){
        GameObject nacScoutObj = new GameObject();
        nacScoutObj.setPosition(x, y);
        
        RenderComponent nacScoutObjRenderer = new RenderComponent();
        int nacScoutImage = R.drawable.nac_scout_jeep_test;
        DrawableBitmap nacScoutDrawable = new DrawableBitmap(x, y, 51, 74, 1, 1, BaseObject.mapLibrary.addTextureToLibrary(nacScoutImage));
        nacScoutObjRenderer.setDrawable(nacScoutDrawable);
        nacScoutObj.add(nacScoutObjRenderer); 
        
        return nacScoutObj;
    }
    
    private GameObject spawnNACAFV(float x, float y){
        GameObject nacAFVObj = new GameObject();
        nacAFVObj.setPosition(x, y);
        
        RenderComponent nacAFVObjRenderer = new RenderComponent();
        int nacAFVImage = R.drawable.anim_gfx_nac_tnk_gun; //TEMPORARY PLACEHOLDER 
        DrawableBitmap nacAFVDrawable = new DrawableBitmap(x, y, 62, 77, 1, 1, BaseObject.mapLibrary.addTextureToLibrary(nacAFVImage));
        nacAFVObjRenderer.setDrawable(nacAFVDrawable);
        nacAFVObj.add(nacAFVObjRenderer); 
        return nacAFVObj;
    }
    
    //SPAWNS HEAVY TANK - ObjectGameManager 
    private GameObject spawnNACHeavyTank(float x, float y, float ratio, boolean testingTOBEREMOVED) {
        // A method to create heavy tank
        // Create a two animation component for treads or one
        // Create one base render component (hull)
        // Create one render component (turret/MAINGUN)
        // Create one render component (turrent MG)

        GameObject nacHeavyTankObjManager = new GameObject();
        nacHeavyTankObjManager.setPosition(x, y);
        nacHeavyTankObjManager.setFaction("NACTEST");
        nacHeavyTankObjManager.setMovable(testingTOBEREMOVED);
        
        
        
        
        //ADDS GRAPHIC COMPONENTS TO NACHEAVYTANKOBJMANAGER AND ALSO ADD SUB AI
        //COMPONENTS FOR ANY TURRETS AVALIABLE
        spawnTrackedVehicleComponents(nacHeavyTankObjManager, "NAC_HVY_TNK", 90);
        
        
        //CREATES MOVEMENT
        
        //CREATE MAIN AI
        
        //CREATE MAIN
        
        //REGISTER TO WORLD
        BaseObject.globalWorldRegister.registerToGlobalRegistry(nacHeavyTankObjManager);
        
        return nacHeavyTankObjManager;
    }

    
    //SPAWNS LIGHT JEEP
    private GameObject spawnNACScoutJeep(float x, float y, float ratio, boolean testingTOBEREMOVED) {
        // A method to create heavy tank
        // Create a two animation component for treads or one
        // Create one base render component (hull)
        // Create one render component (turret/MAINGUN)
        // Create one render component (turrent MG)

        GameObject nacHeavyTankObjManager = new GameObject();
        nacHeavyTankObjManager.setPosition(x, y);
        nacHeavyTankObjManager.setFaction("NACTEST");
        nacHeavyTankObjManager.setMovable(testingTOBEREMOVED);
        
        
        
        
        //ADDS GRAPHIC COMPONENTS TO NACHEAVYTANKOBJMANAGER AND ALSO ADD SUB AI
        //COMPONENTS FOR ANY TURRETS AVALIABLE
        spawnTrackedVehicleComponents(nacHeavyTankObjManager, "NAC_SCOUT_JEEP", 90);
        
        
        //CREATES MOVEMENT
        
        //CREATE MAIN AI
        
        //CREATE MAIN
        
        //REGISTER TO WORLD
        BaseObject.globalWorldRegister.registerToGlobalRegistry(nacHeavyTankObjManager);
        
        return nacHeavyTankObjManager;
    }    
    
    /* autoSpawnVehicle
     * ===============================================================
     * Adds chasis and turrets, vehicleId matches XML, EX: NAC_HVY_TNK
     * ===============================================================
     */   
    private void spawnTrackedVehicleComponents(GameObject mParentObjManager, String vehicleId, float defaultAngle) {
        GlobalDataVehicle globalTankProperties = BaseObject.contextGlobalXMLData.getVehicleUnitById(vehicleId);
        
        
        /* Movement COMPONENT */
        MovementComponent objMovementComponent = new MovementComponent();
        mParentObjManager.add(objMovementComponent);
        /* Create AI COMPONENT */
        

        /* Graphic Structure
         * ===========================
         * 
         * ===========================
         */
        GlobalDataGraphic globalTankGraphicProperties = BaseObject.contextGlobalXMLData.getGraphicById(globalTankProperties.graphicsId);
        //globalTankGraphicProperties.image;
        //Bad Case
        //if (globalTankProperies == null || globalTankGraphicProperties == null){
        //    return;
        // }
        int resID = BaseObject.contextParameters.context.getResources().getIdentifier(globalTankGraphicProperties.image, "drawable", "com.electrofear");
        //int resID = Resources.getSystem().getIdentifier(globalTankGraphicProperties.image, "drawable", "com.electrofear");
        
        //SHADOW ITEMSs
        int resShadowID = BaseObject.contextParameters.context.getResources().getIdentifier(globalTankGraphicProperties.image + "_shadow", "drawable", "com.electrofear");
        
        
        GameUnitObject baseObjVehicleChasis = new GameUnitObject( "nacHeavyTankChasis",
        													globalTankProperties.internalId,
        													"",
                                                            (float)globalTankGraphicProperties.width, 
                                                            (float)globalTankGraphicProperties.height,
                                                            0, //relative X
                                                            0, //relative Y
                                                            defaultAngle,
                                                            1,
                                                            false,
                                                            new DrawableBitmap(BaseObject.mapLibrary.addTextureToLibrary(resID)),
                                                            new DrawableBitmap(BaseObject.mapLibrary.addTextureToLibrary(resShadowID)));
        
        baseObjVehicleChasis.setCalculatedTranslateXY(mParentObjManager.getPosition().x, mParentObjManager.getPosition().y);
        //creates all turrets into a list and order by interal id's and then add them accordingly
        ArrayList<GameUnitObject> tempItemList = new ArrayList();
        
        GameUnitObject tempTurret;
        GlobalDataGraphic tempGraphic;
        for (int i = 0; i < globalTankProperties.mSubTurretData.size(); i++) {
        	tempTurret = spawnTurret(mParentObjManager, globalTankProperties.mSubTurretData.get(i), defaultAngle);        	
        	baseObjVehicleChasis.addGameUnitObjectByInternalId(tempTurret);
        }
        
        mParentObjManager.add(baseObjVehicleChasis);
    }    
    
    /* spawnTurret
     * ==============================
     * Spawns generic turret
     * returns a GameUnitObject
     * ==============================
     */
    private GameUnitObject spawnTurret(GameObject mParentObjManager, GlobalDataUnitSubTurretData inputTurretData, float defaultAngle) {
    	
    	
    	
    	

        GlobalDataTurret globalTurretProperties = BaseObject.contextGlobalXMLData.getTurretById(inputTurretData.turretId);
        GlobalDataGraphic globalTurretGraphicProperties = BaseObject.contextGlobalXMLData.getGraphicById(globalTurretProperties.graphicId);
        int resID = BaseObject.contextParameters.context.getResources().getIdentifier(globalTurretGraphicProperties.image, "drawable", "com.electrofear");
      
        //SHADOW ITEMSs
        int resShadowID = BaseObject.contextParameters.context.getResources().getIdentifier(globalTurretGraphicProperties.image + "_shadow", "drawable", "com.electrofear");        
        
        GameUnitObject turretUnitObj = new GameUnitObject(  globalTurretProperties.turretId,
        													inputTurretData.internalId,
        													inputTurretData.relativeToId,
															(float)globalTurretGraphicProperties.width, 
											                (float)globalTurretGraphicProperties.height,
											                (float)inputTurretData.turretOffSetX, //relative X
											                (float)inputTurretData.turretOffSetY, //relative Y
											                defaultAngle,
											                2,
											                false,
											                new DrawableBitmap(BaseObject.mapLibrary.addTextureToLibrary(resID)),
											                new DrawableBitmap(BaseObject.mapLibrary.addTextureToLibrary(resShadowID)));
        
        //DrawCannon Portion if Applicable
        int weaponDataStringSize;
        GlobalDataGraphicAnimation globalTurretGraphicAnimationProperties;
        String[] globalTurretPropertyString;
        //int resID = BaseObject.contextParameters.context.getResources().getIdentifier(globalTurretGraphicProperties.image, "drawable", "com.electrofear");        
        
        
        
        //CREATE SUB AI COMPONENT => GET WEAPON PROPERTIES AND ETC and add to mParentObjectManager => Remember that range is from center of
        //object not from the barrel, only one per turret but can maintain multiple weapons
        AISubAttackComponent turretSubComponentAI = new AISubAttackComponent(inputTurretData.turretId);
        turretSubComponentAI.addControlPoint(turretUnitObj); //Reference to Turret so we can control it's angle
        for (int z = 0; z < inputTurretData.weapon.size(); z++){
        	turretSubComponentAI.addWeaponData(BaseObject.contextGlobalXMLData.getWeaponId(inputTurretData.weapon.get(z)));
        }
        
        
        //ATTACHES THE APPROPRIATE WEAPON AND THE BARREL (IF APPLICABLE) TO BE DRAWN, ALSO SHOULD CREATE SUB AI COMPONENT FOR RANGE CHECK
        //AND ATTACK
        for (int i = 0; i < globalTurretProperties.weaponDataString.size(); i++){
        	//Parse the string
            globalTurretPropertyString = globalTurretProperties.weaponDataString.get(i).split("#");
        	globalTurretGraphicAnimationProperties = BaseObject.contextGlobalXMLData.getGraphicAnimationId(globalTurretPropertyString[2]);
        	resID = BaseObject.contextParameters.context.getResources().getIdentifier(globalTurretGraphicAnimationProperties.imageBase, "drawable", "com.electrofear");
        	
        	
        	//Create drawable for Shadow, note will create null if none found
        	resShadowID = BaseObject.contextParameters.context.getResources().getIdentifier(globalTurretGraphicAnimationProperties.imageBase + "_shadow", "drawable", "com.electrofear");
	        DrawableBitmap shadowBitmap;
        	
        	if (resShadowID > 0) {
	        	shadowBitmap = new DrawableBitmap(BaseObject.mapLibrary.addTextureToLibrary(resShadowID), 1.1f);
	        } else {
	        	shadowBitmap = null;
	        }
        	
        	GameUnitObject turretUnitCannonObj = new GameUnitObject(  globalTurretGraphicAnimationProperties.nameId,
	        														  "",
																	  "",
																	  (float)globalTurretGraphicAnimationProperties.width, 
														              (float)globalTurretGraphicAnimationProperties.height,
														              Float.valueOf(globalTurretPropertyString[0]).floatValue(), //relative X
														              Float.valueOf(globalTurretPropertyString[1]).floatValue(), //relative Y
														              defaultAngle,
														              3,
														              true,
														              new DrawableBitmap(BaseObject.mapLibrary.addTextureToLibrary(resID)),
	        														  shadowBitmap);        
	        turretUnitObj.addGameUnit(turretUnitCannonObj);
	        turretSubComponentAI.addWeaponControlPoint(turretUnitCannonObj); //Add weapon control point here
	        

        }
        
        mParentObjManager.add(turretSubComponentAI);
        
        
    	return turretUnitObj;
    }
    
    /* spawnHeavyTank
     * ==============================================================
     * The nacHeavyTankObjManager (GameObject) will be passed here
     * spawnNACHeavyTank use sub AI (FOR TARGETING AND TURRET CONTROL)
     * and creates the Turret/Cannon bones of the object 
     * ==============================================================
     */
    private void spawnNACHeavyTank(GameObject parentManager, float x, float y){
        GlobalDataVehicle globalTankProperies = BaseObject.contextGlobalXMLData.getVehicleUnitById("NAC_HVY_TNK");
        GlobalDataGraphic globalTankGraphicProperties = BaseObject.contextGlobalXMLData.getGraphicById(globalTankProperies.graphicsId);
        
        float tankChasisWidth = (float) globalTankGraphicProperties.width;
        float tankChasisHeight = (float) globalTankGraphicProperties.height;
        float defaultFacingAngle = 0;
        //Base GameUnitObject does nothing but sets the desired location of the object
        //This is the parent of the tank the child are chasis, whose child is turret and
        //whose child is cannon
        
        //Chasis - basically, width, height, x, y, angle, z-axis => pretty much x and y are relative position to parent, in this case there's no parent so this is absolute
       /* GameUnitObject nacHeavyTankChasis = new GameUnitObject( "nacHeavyTankChasis",
                                                                tankChasisWidth, 
                                                                tankChasisHeight,
                                                                0, //relative X
                                                                0, //relative Y
                                                                defaultFacingAngle,
                                                                2,
                                                                false,
                                                                new DrawableBitmap(BaseObject.mapLibrary.addTextureToLibrary(R.drawable.nac_heavy_tank_chasis)));
        
        nacHeavyTankChasis.setCalculatedTranslateXY(x, y);
        
        GameUnitObject nacHeavyTankChasisShadow = new GameUnitObject( "nacHeavyTankChasisShadow",
                                                                      tankChasisWidth, 
                                                                      tankChasisHeight, 
                                                                      10, 
                                                                      10, 
                                                                      defaultFacingAngle, 
                                                                      1,
                                                                      true,
                                                                      new DrawableBitmap (BaseObject.mapLibrary.addTextureToLibrary(R.drawable.nac_heavy_tank_chasis_shadow)));

        nacHeavyTankChasis.addGameUnitObject(nacHeavyTankChasisShadow);
        
        spawnNACHeavyTankTurretNew(nacHeavyTankChasis, -6, 0, defaultFacingAngle);*/
        
        
        //Add to Parent Game Object Manager!
        //parentManager.add(nacHeavyTankChasis);
        
        
    }
    

    
    
    //relativeX and relativeY are not workin
    private void spawnNACHeavyTankTurretNew(GameUnitObject parent, float relativeX, float relativeY, float defaultAngle){
        float tankTurretWidth = 75;
        float tankTurretHeight = 67;
        
        float tankTurretCannonWidth = 45;
        float tankTurretCannonHeight = 6;
        
        float relativeTurretX = -10;
        float relativeTurretY = 0;
        
        //Turret
        /* GameUnitObject nacHeavyTankTurret = new GameUnitObject( "nacHeavyTankTurret",
                                                                tankTurretWidth, 
                                                                tankTurretHeight,
                                                                relativeTurretX,
                                                                relativeTurretY,
                                                                defaultAngle,
                                                                4,
                                                                false,
                                                                new DrawableBitmap(BaseObject.mapLibrary.addTextureToLibrary(R.drawable.nac_heavy_tank_turret)));
        //Turret Shadow
        GameUnitObject nacHeavyTankTurretShadow = new GameUnitObject( "nacHeavyTankTurretShadow",
                                                                      tankTurretWidth,
                                                                      tankTurretHeight,
                                                                      10, //relative to parentX
                                                                      10, //relative to parentY
                                                                      defaultAngle, //angle of shadow turret right now
                                                                      3,
                                                                      true,
                                                                      new DrawableBitmap(BaseObject.mapLibrary.addTextureToLibrary(R.drawable.nac_heavy_tank_turret_shadow)));

        GameUnitObject nacHeavyTankTurretCannon = new GameUnitObject( "nacHeavyTankTurretCannon",
                                                                      tankTurretCannonWidth,
                                                                      tankTurretCannonHeight,
                                                                      tankTurretWidth/2 + tankTurretCannonWidth/2, //relative to parentX
                                                                      0, //relative to parentY
                                                                      defaultAngle, //angle of shadow turret right now
                                                                      4,
                                                                      true,
                                                                      new DrawableBitmap(BaseObject.mapLibrary.addTextureToLibrary(R.drawable.nac_heavy_tank_turret_gun)));
        
        parent.addGameUnitObject(nacHeavyTankTurret);
        nacHeavyTankTurret.addGameUnitObject(nacHeavyTankTurretShadow);
        nacHeavyTankTurret.addGameUnitObject(nacHeavyTankTurretCannon);*/
    }
    
    //private void spawnNACHeavyTankTurret(float x, float y, GameObject parent){
        //Add Corresponding Shadow
        //Add Chasis or base of object, add turret by location, also this provides shadow support
        /*float tankTurretWidth = 67;
        float tankTurretHeight = 75;
        
        float tankTurretCannonWidth = 6;

        RenderComponent nacHeavyTankObjTurretRenderer = new RenderComponent();
        nacHeavyTankObjTurretRenderer.setDrawable(new DrawableBitmap(x - tankTurretWidth/2, y - tankTurretHeight/2, tankTurretWidth, tankTurretHeight, 1, 4, BaseObject.mapLibrary.addTextureToLibrary(R.drawable.nac_heavy_tank_turret)));
        parent.add(nacHeavyTankObjTurretRenderer);*/
      
        //Add Corresponding Shadow
        //Add Chasis or base of object, add turret by location, also this provides shadow support
        //RenderComponent nacHeavyTankObjTurretShadowRenderer = new RenderComponent();
        //nacHeavyTankObjTurretShadowRenderer.setDrawable(new DrawableBitmap(x - tankTurretWidth/2 - 8, y - tankTurretHeight/2, tankTurretWidth + 8, tankTurretHeight + 5, 1, 3, BaseObject.mapLibrary.addTextureToLibrary(R.drawable.nac_heavy_tank_turret_shadow)));
        //parent.add(nacHeavyTankObjTurretShadowRenderer);
        
        //Add Corresponding Shadow
        //Add Chasis or base of object, add turret by location, also this provides shadow support
        //RenderComponent nacHeavyTankObjTurretCannonRenderer = new RenderComponent();
        //nacHeavyTankObjTurretCannonRenderer.setDrawable(new DrawableBitmap(x - tankTurretCannonWidth/2, y + tankTurretHeight/2, tankTurretCannonWidth, tankTurretCannonHeight, 1, 3, BaseObject.mapLibrary.addTextureToLibrary(R.drawable.nac_heavy_tank_turret_gun)));
        //parent.add(nacHeavyTankObjTurretCannonRenderer);        
    //}
    
    //SPAWNS HEAVY MECH - ObjectGameManager 
    private GameObject spawnNACHeavyMech(float x, float y) {
        // A method to create heavy tank
        // Create a two animation component for treads or one
        // Create one base render component (hull)
        // Create one render component (turret/MAINGUN)
        // Create one render component (turrent MG)
        GameObject nacHeavyMechObj = new GameObject();
        nacHeavyMechObj.setPosition(x, y);
        
        RenderComponent nacHeavyMechObjRenderer = new RenderComponent();
        int nacHeavyMechImage = R.drawable.nac_heavy_mech_test;
        DrawableBitmap nacHeavyMechDrawable = new DrawableBitmap(x, y, 98, 93, 1, 0, BaseObject.mapLibrary.addTextureToLibrary(nacHeavyMechImage));
        nacHeavyMechObjRenderer.setDrawable(nacHeavyMechDrawable);
        nacHeavyMechObj.add(nacHeavyMechObjRenderer); 
        return nacHeavyMechObj;
    }
    
}
