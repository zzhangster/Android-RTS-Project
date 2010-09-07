package com.electrofear;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.electrofear.level.GlobalLevelProperties;
import com.electrofear.parser.GlobalDataFaction;
import com.electrofear.parser.GlobalDataGraphic;
import com.electrofear.parser.GlobalDataGraphicAnimation;
import com.electrofear.parser.GlobalDataInfantry;
import com.electrofear.parser.GlobalDataTurret;
import com.electrofear.parser.GlobalDataUnitSubTurretData;
import com.electrofear.parser.GlobalDataVehicle;
import com.electrofear.parser.GlobalDataWeapon;
import com.electrofear.parser.GlobalParsedXMLDataRepository;
import com.electrofear.parser.GlobalTerrainFaunaObj;
import com.electrofear.parser.GlobalTerrainObj;


public class XMLParser {
    public static enum ParserType {
        INFANTRY,
        VEHICLE,
        HELICOPTER,
        TURRET,
        LEVEL
    }
    public static void parseByType(ParserType inputType, Document inputDom) {
        switch(inputType) {
            case VEHICLE :
                vehicleParser(inputDom);
                break;
            default :
                break;
        }
        
    }
    
    //Parse RULES PAGE
    public static void startParsing(Document inputDom) {
        GlobalParsedXMLDataRepository contextGlobalXMLData = BaseObject.contextGlobalXMLData;
        //PARSE THE FACTIONS
        GlobalDataFaction tempFactionParserObj;
        Element root = inputDom.getDocumentElement();
        NodeList items = root.getElementsByTagName("Factions");
        NodeList subItems = ((Element)items.item(0)).getElementsByTagName("FACTION");
        for(int i = 0; i < subItems.getLength(); i++){
            tempFactionParserObj = new GlobalDataFaction();
            Element element = (Element) subItems.item(i);
            tempFactionParserObj.ownerName = element.getElementsByTagName("OwnerName").item(0).getFirstChild().getNodeValue();
            tempFactionParserObj.name = element.getElementsByTagName("Name").item(0).getFirstChild().getNodeValue();
            tempFactionParserObj.firePower = Double.valueOf(element.getElementsByTagName("FirePower").item(0).getFirstChild().getNodeValue());
            tempFactionParserObj.groundSpeed = Double.valueOf(element.getElementsByTagName("GroundSpeed").item(0).getFirstChild().getNodeValue());
            tempFactionParserObj.airSpeed = Double.valueOf(element.getElementsByTagName("AirSpeed").item(0).getFirstChild().getNodeValue());
            tempFactionParserObj.armor = Double.valueOf(element.getElementsByTagName("Armor").item(0).getFirstChild().getNodeValue());
            tempFactionParserObj.rof = Double.valueOf(element.getElementsByTagName("ROF").item(0).getFirstChild().getNodeValue());
            tempFactionParserObj.pointCost = Double.valueOf(element.getElementsByTagName("PointCost").item(0).getFirstChild().getNodeValue());
            tempFactionParserObj.rechargeTime = Double.valueOf(element.getElementsByTagName("RechargeTime").item(0).getFirstChild().getNodeValue());
            
            BaseObject.contextGlobalXMLData.addFaction(tempFactionParserObj);
        }
        tempFactionParserObj = null;
        
        
        //Parse Vehicles first!
        GlobalDataVehicle tempVehicleParserObj;
        items = root.getElementsByTagName("UNITSTATISTICS");
        subItems = ((Element)items.item(0)).getElementsByTagName("VEHICLE");
        for(int i = 0; i < subItems.getLength(); i++){
            tempVehicleParserObj = new GlobalDataVehicle();
            Element element = (Element) subItems.item(i);
            tempVehicleParserObj.objectId = element.getElementsByTagName("ObjectId").item(0).getFirstChild().getNodeValue();
            tempVehicleParserObj.internalId = element.getElementsByTagName("InternalId").item(0).getFirstChild().getNodeValue();
            tempVehicleParserObj.name = element.getElementsByTagName("Name").item(0).getFirstChild().getNodeValue();
            tempVehicleParserObj.ownerName = element.getElementsByTagName("OwnerName").item(0).getFirstChild().getNodeValue();
            tempVehicleParserObj.speed = Double.valueOf(element.getElementsByTagName("Speed").item(0).getFirstChild().getNodeValue());
            tempVehicleParserObj.acceleration = Double.valueOf(element.getElementsByTagName("Acceleration").item(0).getFirstChild().getNodeValue());
            tempVehicleParserObj.rotateRate = Double.valueOf(element.getElementsByTagName("RotateRate").item(0).getFirstChild().getNodeValue());
            tempVehicleParserObj.tracked = element.getElementsByTagName("Tracked").item(0).getFirstChild().getNodeValue();
            tempVehicleParserObj.pointCost = Double.valueOf(element.getElementsByTagName("PointCost").item(0).getFirstChild().getNodeValue());
            tempVehicleParserObj.unitHP = Double.valueOf(element.getElementsByTagName("UnitHP").item(0).getFirstChild().getNodeValue());
            tempVehicleParserObj.graphicsId = element.getElementsByTagName("GraphicId").item(0).getFirstChild().getNodeValue();
            
            NodeList turretItems = element.getElementsByTagName("UnitTurret");
            GlobalDataUnitSubTurretData tempTurretParserObj;
            for(int j = 0; j < turretItems.getLength(); j++){
                tempTurretParserObj = new GlobalDataUnitSubTurretData();
                Element turretElement = (Element) turretItems.item(j);
                tempTurretParserObj.internalId = turretElement.getElementsByTagName("InternalId").item(0).getFirstChild().getNodeValue();
                tempTurretParserObj.turretId = turretElement.getElementsByTagName("TurretId").item(0).getFirstChild().getNodeValue();
                tempTurretParserObj.relativeToId = turretElement.getElementsByTagName("RelativeToID").item(0).getFirstChild().getNodeValue();
                tempTurretParserObj.turretOffSetX = Double.valueOf(turretElement.getElementsByTagName("TurretOffSetX").item(0).getFirstChild().getNodeValue());
                tempTurretParserObj.turretOffSetY = Double.valueOf(turretElement.getElementsByTagName("TurretOffsetY").item(0).getFirstChild().getNodeValue());
                
                //Get Arraylist of Weapons, remember turret can have more than one weapon
                /*NodeList weaponItems = turretElement.getElementsByTagName("Weapon");
                for (int k = 0; k < weaponItems.getLength(); k++){
                	Element weaponElement = (Element) weaponItems.item(k);
	                tempTurretParserObj.weapon.add(weaponElement.getNodeValue());
	                String testing = "coocoo";
                }*/
                for (int k = 0; k < turretElement.getElementsByTagName("Weapon").getLength(); k++){
                    tempTurretParserObj.weapon.add(turretElement.getElementsByTagName("Weapon").item(k).getFirstChild().getNodeValue());
                }                
                tempVehicleParserObj.addTurret(tempTurretParserObj);                
            }
            BaseObject.contextGlobalXMLData.addVehicleUnit(tempVehicleParserObj);
        }
        
        //Parse Infantry Here
        //Parse Vehicles first!
        GlobalDataInfantry tempInfantryParserObj;
        items = root.getElementsByTagName("UNITSTATISTICS");
        subItems = ((Element)items.item(0)).getElementsByTagName("INFANTRY");
        for(int i = 0; i < subItems.getLength(); i++){
            tempInfantryParserObj = new GlobalDataInfantry();
            Element element = (Element) subItems.item(i);
            tempInfantryParserObj.ownerName = element.getElementsByTagName("ObjectId").item(0).getFirstChild().getNodeValue();
            tempInfantryParserObj.name = element.getElementsByTagName("Name").item(0).getFirstChild().getNodeValue();
            tempInfantryParserObj.ownerName = element.getElementsByTagName("OwnerName").item(0).getFirstChild().getNodeValue();
            tempInfantryParserObj.speed = Double.valueOf(element.getElementsByTagName("Speed").item(0).getFirstChild().getNodeValue());
            tempInfantryParserObj.rotateRate = Double.valueOf(element.getElementsByTagName("RotateRate").item(0).getFirstChild().getNodeValue());
            tempInfantryParserObj.pointCost = Double.valueOf(element.getElementsByTagName("PointCost").item(0).getFirstChild().getNodeValue());
            tempInfantryParserObj.unitHP = Double.valueOf(element.getElementsByTagName("UnitHP").item(0).getFirstChild().getNodeValue());
            tempInfantryParserObj.animationStand = element.getElementsByTagName("AnimationStand").item(0).getFirstChild().getNodeValue();
            tempInfantryParserObj.animationCrawl = element.getElementsByTagName("AnimationCrawl").item(0).getFirstChild().getNodeValue();
            tempInfantryParserObj.animationWalk = element.getElementsByTagName("AnimationWalk").item(0).getFirstChild().getNodeValue();
            tempInfantryParserObj.animationStandFire = element.getElementsByTagName("AnimationStandFire").item(0).getFirstChild().getNodeValue();
            tempInfantryParserObj.animationCrawlFire = element.getElementsByTagName("AnimationCrawlFire").item(0).getFirstChild().getNodeValue();
            tempInfantryParserObj.numberPerUnit = Double.valueOf(element.getElementsByTagName("NumberPerUnit").item(0).getFirstChild().getNodeValue());
            tempInfantryParserObj.spreadPattern = element.getElementsByTagName("SpreadPattern").item(0).getFirstChild().getNodeValue();
            tempInfantryParserObj.weapon = element.getElementsByTagName("Weapon").item(0).getFirstChild().getNodeValue(); 
        
            BaseObject.contextGlobalXMLData.addInfantryUnit(tempInfantryParserObj);
        }
                
        //Parse Turret Here
        GlobalDataTurret tempTurretParserObj;
        items = root.getElementsByTagName("TURRETSTATISTICS");
        subItems = ((Element)items.item(0)).getElementsByTagName("TURRET");       
        for(int i = 0; i < subItems.getLength(); i++){
            tempTurretParserObj = new GlobalDataTurret();
            Element element = (Element) subItems.item(i);
            tempTurretParserObj.turretId = element.getElementsByTagName("TurretId").item(0).getFirstChild().getNodeValue();
            tempTurretParserObj.graphicId = element.getElementsByTagName("GraphicId").item(0).getFirstChild().getNodeValue();
            tempTurretParserObj.rotateRate = Double.valueOf(element.getElementsByTagName("RotateRate").item(0).getFirstChild().getNodeValue());
            
            //Get Arraylist of Weapons, remember turret can have more than one weapon, here we can have multiple offsets
             
            for (int k = 0; k < element.getElementsByTagName("WeaponOffSet").getLength(); k++){
                tempTurretParserObj.weaponDataString.add(element.getElementsByTagName("WeaponOffSet").item(k).getFirstChild().getNodeValue());
                String testing = "coocoo";
            }
            BaseObject.contextGlobalXMLData.addTurret(tempTurretParserObj);
        }
        
        //Parse Weapons
        GlobalDataWeapon tempWeaponParserObj;
        items = root.getElementsByTagName("WEAPONSTATISTICS");
        subItems = ((Element)items.item(0)).getElementsByTagName("WEAPON");
        for(int i = 0; i < subItems.getLength(); i++){
            tempWeaponParserObj = new GlobalDataWeapon();
            Element element = (Element) subItems.item(i);
            tempWeaponParserObj.weaponId = element.getElementsByTagName("WeaponId").item(0).getFirstChild().getNodeValue();
            tempWeaponParserObj.damage = Double.valueOf(element.getElementsByTagName("Damage").item(0).getFirstChild().getNodeValue());
            tempWeaponParserObj.typeDamage = element.getElementsByTagName("TypeDamage").item(0).getFirstChild().getNodeValue();
            tempWeaponParserObj.rof = Double.valueOf(element.getElementsByTagName("ROF").item(0).getFirstChild().getNodeValue());
            tempWeaponParserObj.range = Double.valueOf(element.getElementsByTagName("Range").item(0).getFirstChild().getNodeValue());
            tempWeaponParserObj.speed = Double.valueOf(element.getElementsByTagName("Speed").item(0).getFirstChild().getNodeValue());
            tempWeaponParserObj.ballisticType = element.getElementsByTagName("BallisticType").item(0).getFirstChild().getNodeValue();
            tempWeaponParserObj.animatedGraphicsFireId = element.getElementsByTagName("AnimatedGraphicsFireId").item(0).getFirstChild().getNodeValue();
            tempWeaponParserObj.graphicsBallisticId = element.getElementsByTagName("GraphicsBallisticId").item(0).getFirstChild().getNodeValue();
        
            BaseObject.contextGlobalXMLData.addWeapon(tempWeaponParserObj);
        }          
        
        
        //Parse Graphic elements
        //Parse GRAPHICS HERE - STATIC ONLY!
        GlobalDataGraphic tempGraphicParserObj;
        items = root.getElementsByTagName("GRAPHICSTATIC");
        subItems = ((Element)items.item(0)).getElementsByTagName("Graphics");       
        for(int i = 0; i < subItems.getLength(); i++){
            tempGraphicParserObj = new GlobalDataGraphic();
            Element element = (Element) subItems.item(i);
            tempGraphicParserObj.nameId = element.getElementsByTagName("NameId").item(0).getFirstChild().getNodeValue();
            tempGraphicParserObj.height = Double.valueOf(element.getElementsByTagName("Height").item(0).getFirstChild().getNodeValue());
            tempGraphicParserObj.width = Double.valueOf(element.getElementsByTagName("Width").item(0).getFirstChild().getNodeValue());
            tempGraphicParserObj.image = element.getElementsByTagName("Image").item(0).getFirstChild().getNodeValue();
            
            BaseObject.contextGlobalXMLData.addGraphic(tempGraphicParserObj);
        }
                
        //Parse Turret Here
        GlobalDataGraphicAnimation tempGraphicAnimationParserObj;
        items = root.getElementsByTagName("ANIMATEDGRAPHICS");
        subItems = ((Element)items.item(0)).getElementsByTagName("AnimationGraphics");       
        for(int i = 0; i < subItems.getLength(); i++){
            tempGraphicAnimationParserObj = new GlobalDataGraphicAnimation();
            Element element = (Element) subItems.item(i);
            tempGraphicAnimationParserObj.nameId = element.getElementsByTagName("NameId").item(0).getFirstChild().getNodeValue();
            tempGraphicAnimationParserObj.height = Double.valueOf(element.getElementsByTagName("Height").item(0).getFirstChild().getNodeValue());
            tempGraphicAnimationParserObj.width = Double.valueOf(element.getElementsByTagName("Width").item(0).getFirstChild().getNodeValue());
            tempGraphicAnimationParserObj.imageBase = element.getElementsByTagName("ImageBase").item(0).getFirstChild().getNodeValue();
            tempGraphicAnimationParserObj.imageCount = Double.valueOf(element.getElementsByTagName("ImageCount").item(0).getFirstChild().getNodeValue());
            
            BaseObject.contextGlobalXMLData.addGraphicAnimation(tempGraphicAnimationParserObj);
            
        }        
        
    }
    
    /* levelPropertiesParser
     * ====================================================
     * Parses incoming level properties and then update
     * global values such as camera, wind, lighting, etc
     * ====================================================
     */
    
    public static void startParsingLevel(Document inputDom) {
        GlobalLevelProperties levelXMLData = BaseObject.globalLevelProperties;
        //PARSE THE FACTIONS


        Element root = inputDom.getDocumentElement();
        NodeList items = root.getElementsByTagName("MapBackGroundProperties");
        levelXMLData.mapName = ((Element)items.item(0)).getElementsByTagName("Name").item(0).getFirstChild().getNodeValue();
        levelXMLData.mapBackGroundImage = ((Element)items.item(0)).getElementsByTagName("ImageBackGround").item(0).getFirstChild().getNodeValue();    
        levelXMLData.mapHeight = Float.valueOf(((Element)items.item(0)).getElementsByTagName("Height").item(0).getFirstChild().getNodeValue());    
        levelXMLData.mapWidth = Float.valueOf(((Element)items.item(0)).getElementsByTagName("Width").item(0).getFirstChild().getNodeValue());

        items = root.getElementsByTagName("CameraStartCoord");
        levelXMLData.cameraPositionX = Float.valueOf(((Element)items.item(0)).getElementsByTagName("StartCoordX").item(0).getFirstChild().getNodeValue());    
        levelXMLData.cameraPositionY = Float.valueOf(((Element)items.item(0)).getElementsByTagName("StartCoordY").item(0).getFirstChild().getNodeValue());    
        levelXMLData.cameraPositionZ= Float.valueOf(((Element)items.item(0)).getElementsByTagName("StartCoordZ").item(0).getFirstChild().getNodeValue());
                
        
        items = root.getElementsByTagName("Lighting");
        levelXMLData.lightingAngle = Float.valueOf(((Element)items.item(0)).getElementsByTagName("Angle").item(0).getFirstChild().getNodeValue());    
        levelXMLData.lightingIntesity = Float.valueOf(((Element)items.item(0)).getElementsByTagName("Intensity").item(0).getFirstChild().getNodeValue());    
        levelXMLData.lightingShadowLength= Float.valueOf(((Element)items.item(0)).getElementsByTagName("ShadowLength").item(0).getFirstChild().getNodeValue());
        
        items = root.getElementsByTagName("Wind");
        levelXMLData.windAngle = Float.valueOf(((Element)items.item(0)).getElementsByTagName("Angle").item(0).getFirstChild().getNodeValue());
        levelXMLData.windIntesity = Float.valueOf(((Element)items.item(0)).getElementsByTagName("Magnitude").item(0).getFirstChild().getNodeValue());
        
        items = root.getElementsByTagName("Weather");
        levelXMLData.weatherType = ((Element)items.item(0)).getElementsByTagName("WeatherType").item(0).getFirstChild().getNodeValue();
        levelXMLData.weatherIntensity = Float.valueOf(((Element)items.item(0)).getElementsByTagName("Intensity").item(0).getFirstChild().getNodeValue());
        
        items = root.getElementsByTagName("MapObjects");
        
        
        GlobalTerrainObj tempTerrainObj;
        NodeList subItems = ((Element)items.item(0)).getElementsByTagName("VegetationObject");
        
        for(int i = 0; i < subItems.getLength(); i++){
        	tempTerrainObj = new GlobalTerrainFaunaObj();
            Element element = (Element) subItems.item(i);
            tempTerrainObj.positionX = Float.valueOf(element.getElementsByTagName("LocationX").item(0).getFirstChild().getNodeValue());
            tempTerrainObj.positionY = Float.valueOf(element.getElementsByTagName("LocationY").item(0).getFirstChild().getNodeValue());
            tempTerrainObj.positionZ = Float.valueOf(element.getElementsByTagName("LocationZ").item(0).getFirstChild().getNodeValue());
            tempTerrainObj.angle = Float.valueOf(element.getElementsByTagName("Angle").item(0).getFirstChild().getNodeValue());
            tempTerrainObj.objType = element.getElementsByTagName("Type").item(0).getFirstChild().getNodeValue();
            
            BaseObject.globalLevelProperties.addTerrainObject(tempTerrainObj);
        }
        
        subItems = ((Element)items.item(0)).getElementsByTagName("BuildingObject");
        
        for(int i = 0; i < subItems.getLength(); i++){
        	tempTerrainObj = new GlobalTerrainFaunaObj();
            Element element = (Element) subItems.item(i);
            tempTerrainObj.positionX = Float.valueOf(element.getElementsByTagName("LocationX").item(0).getFirstChild().getNodeValue());
            tempTerrainObj.positionY = Float.valueOf(element.getElementsByTagName("LocationY").item(0).getFirstChild().getNodeValue());
            tempTerrainObj.positionZ = Float.valueOf(element.getElementsByTagName("LocationZ").item(0).getFirstChild().getNodeValue());
            tempTerrainObj.shadowMultipler = Float.valueOf(element.getElementsByTagName("ShadowMultiplier").item(0).getFirstChild().getNodeValue());
            tempTerrainObj.angle = Float.valueOf(element.getElementsByTagName("Angle").item(0).getFirstChild().getNodeValue());
            tempTerrainObj.objType = element.getElementsByTagName("Type").item(0).getFirstChild().getNodeValue();
            
            BaseObject.globalLevelProperties.addTerrainObject(tempTerrainObj);
        }         
        
    }
    
    
    private static void vehicleParser(Document inputDom){
        BaseUnitParserObject tempParserObj;
        Element root = inputDom.getDocumentElement();
        NodeList items = root.getElementsByTagName("VehicleObject");
        
        for (int i = 0; i < items.getLength(); i++){
            tempParserObj = new BaseUnitParserObject();
            Element element = (Element) items.item(i);
            tempParserObj.objectID = element.getElementsByTagName("ObjectID").item(0).getFirstChild().getNodeValue();
            tempParserObj.name = element.getElementsByTagName("Name").item(0).getFirstChild().getNodeValue();
            tempParserObj.height = Float.valueOf(element.getElementsByTagName("Height").item(0).getFirstChild().getNodeValue());
            tempParserObj.width = Float.valueOf(element.getElementsByTagName("Width").item(0).getFirstChild().getNodeValue());
            tempParserObj.image = element.getElementsByTagName("Image").item(0).getFirstChild().getNodeValue();
            tempParserObj.objectType = element.getElementsByTagName("ObjectType").item(0).getFirstChild().getNodeValue();
            tempParserObj.speed = Integer.valueOf(element.getElementsByTagName("Speed").item(0).getFirstChild().getNodeValue());
            tempParserObj.turnRate = Integer.valueOf(element.getElementsByTagName("TurnRate").item(0).getFirstChild().getNodeValue());
            
            
            //Add the Turret and weapons
            NodeList turretItems = element.getElementsByTagName("Turret");
            for (int j = 0; j < turretItems.getLength(); j++) {
                Element turretElement = (Element) turretItems.item(j);
                tempParserObj.turret_id.add(turretElement.getElementsByTagName("TurretID").item(0).getFirstChild().getNodeValue());
                tempParserObj.turret_x.add(turretElement.getElementsByTagName("PositionX").item(0).getFirstChild().getNodeValue());
                tempParserObj.turret_y.add(turretElement.getElementsByTagName("PositionY").item(0).getFirstChild().getNodeValue());
                tempParserObj.turrent_turn_rate.add(turretElement.getElementsByTagName("TurretTurnRate").item(0).getFirstChild().getNodeValue());
                
                NodeList weaponItems = turretElement.getElementsByTagName("Weapon");
                ArrayList tempWeaponList = new ArrayList();
                for (int k = 0; k < weaponItems.getLength(); k++) {
                    Element weaponElement = (Element) weaponItems.item(k);
                    tempWeaponList.add(weaponElement.getElementsByTagName("WeaponID").item(0).getFirstChild().getNodeValue());
                }
                tempParserObj.turret_weapon_id.add(tempWeaponList);
            }
            
            //PUT PARSE OBJ TO GLOBAL static array
            BaseObject.globalUnitObjData.put(tempParserObj.objectID, tempParserObj);
        }
        
    }
}
