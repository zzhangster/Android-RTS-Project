package com.electrofear;



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
        
    public GameObject spawn(GameObjectType type, float x, float y) {
        GameObject newObject = null;
        switch (type){
            case NACHEAVYTANK:
                newObject = spawnNACHeavyTank(x,y);
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
        int nacScoutImage = R.drawable.testing_humvee;
        DrawableBitmap nacScoutDrawable = new DrawableBitmap(x, y, 41, 53, 1, 0, BaseObject.mapLibrary.addTextureToLibrary(nacScoutImage));
        nacScoutObjRenderer.setDrawable(nacScoutDrawable);
        nacScoutObj.add(nacScoutObjRenderer); 
        return nacScoutObj;
    }
    
    private GameObject spawnNACAFV(float x, float y){
        GameObject nacAFVObj = new GameObject();
        nacAFVObj.setPosition(x, y);
        
        RenderComponent nacAFVObjRenderer = new RenderComponent();
        int nacAFVImage = R.drawable.testing_ifv;
        DrawableBitmap nacAFVDrawable = new DrawableBitmap(x, y, 62, 77, 1, 0, BaseObject.mapLibrary.addTextureToLibrary(nacAFVImage));
        nacAFVObjRenderer.setDrawable(nacAFVDrawable);
        nacAFVObj.add(nacAFVObjRenderer); 
        return nacAFVObj;
    }
    //SPAWNS HEAVY TANK - ObjectGameManager 
    private GameObject spawnNACHeavyTank(float x, float y) {
        // A method to create heavy tank
        // Create a two animation component for treads or one
        // Create one base render component (hull)
        // Create one render component (turret/MAINGUN)
        // Create one render component (turrent MG)
        GameObject nacHeavyTankObj = new GameObject();
        nacHeavyTankObj.setPosition(x, y);
        
        RenderComponent nacHeavyTankObjRenderer = new RenderComponent();
        int nacHeavyTankImage = R.drawable.testing_armor;
        DrawableBitmap nacHeavyTankDrawable = new DrawableBitmap(x, y, 91, 106, 1, 0, BaseObject.mapLibrary.addTextureToLibrary(nacHeavyTankImage));
        nacHeavyTankObjRenderer.setDrawable(nacHeavyTankDrawable);
        nacHeavyTankObj.add(nacHeavyTankObjRenderer); 
        return nacHeavyTankObj;
    }
    
}
