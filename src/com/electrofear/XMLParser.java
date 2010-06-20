package com.electrofear;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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
