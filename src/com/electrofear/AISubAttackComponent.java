package com.electrofear;

import java.util.ArrayList;

import android.util.Log;

import com.electrofear.parser.GlobalDataTurret;
import com.electrofear.parser.GlobalDataWeapon;

//A SUB AI COMPONENT IS ATTACHED TO A TURRET, this turret can have multiple weapons attached
//

public class AISubAttackComponent extends BaseObject {

    private ArrayList<GlobalDataWeapon> weaponData;
    private GameUnitObject controlPoint; // turret control point
    private ArrayList<GameUnitObject> weaponControlPoint;
    private String turretId; // Id with all turret properties
    private GlobalDataTurret turretProperties;
    private Boolean hasTarget;

    
    public boolean getHasTarget() {
        return hasTarget;
    }
    
    public AISubAttackComponent() {
        weaponData = new ArrayList();
        weaponControlPoint = new ArrayList();
        hasTarget = false;
    }

    public AISubAttackComponent(String inputId) {
        weaponData = new ArrayList();
        weaponControlPoint = new ArrayList();
        turretId = inputId;
        turretProperties = BaseObject.contextGlobalXMLData
                .getTurretById(turretId);
        hasTarget = false;
    }

    public void addWeaponData(GlobalDataWeapon inputWeaponObj) {
        weaponData.add(inputWeaponObj);
    }

    public void addControlPoint(GameUnitObject inputObj) {
        controlPoint = inputObj;
    }

    public void addWeaponControlPoint(GameUnitObject inputWeaponObj) {
        weaponControlPoint.add(inputWeaponObj);
    }

    @Override
    public void update(float timeDelta, BaseObject parent) {
        
        this.updateAttackAngle(timeDelta, parent);

        
    }
    
    private void updateAttackAngle(float timeDelta, BaseObject parent) {
        // Loop through images
        ArrayList<GameObject> inRangeObjects = BaseObject.globalWorldRegister
                .getObjectsInRange("HOSTILE", (GameObject) (parent),
                        (float) (weaponData.get(0).range));

        // TEST GET FIRST OBJECT AND IF RANGE MOVE TURRET TO POINT AT IT
        float controlPointX, controlPointy, targetX, targetY;
        float targetAngle, currentAngle, diffAngle;

        currentAngle = controlPoint.getAngle() % 360f; // GET CURRENT ANGLE

        // RIGHT NOW ONLY GET THE FIRST ONE FOUND, IN FUTURE CHECK FOR PRIORITY
        if (inRangeObjects != null && inRangeObjects.size() != 0) {
            hasTarget = true;
            
            Vector2 targetPosition = inRangeObjects.get(0).getPosition();
            Vector2 controlPosition = controlPoint.getPosition();

            float diffX = targetPosition.x - controlPosition.x;
            float diffY = targetPosition.y - controlPosition.y;

            if (diffX == 0f) {
                if (diffY > 0f) {
                    targetAngle = 90f;
                } else {
                    targetAngle = 270f;
                }

            } else {
                targetAngle = (float) Math.toDegrees((float) Math.atan(diffY
                        / diffX));

                // ANGLE OFFSET
                if (diffY > 0 && diffX < 0) {
                    targetAngle += 180f;
                } else if (diffX < 0 && diffY > 0) {
                    targetAngle += 180f;
                } else if (diffX < 0 && diffY < 0) {
                    targetAngle += 180f;
                } else if (diffX > 0 && diffY < 0) {
                    targetAngle += 360f;
                }
            }
            targetAngle %= 360f;
        } else {
            // IF NOTHING IS FOUND, MOVE TURRENT BACK TO DEFAULT ANGLE
            targetAngle = controlPoint.getDefaultAngle();
            hasTarget = false;
        }

        // FOUND TARGET ANGLE, NOT FOR THE FUN PART
        if (targetAngle > currentAngle){
            diffAngle = targetAngle - currentAngle;
        } else {
            diffAngle = 360f - currentAngle + targetAngle;
        }
        
        if (diffAngle < 180f) {
            if (Math.abs(diffAngle) > turretProperties.rotateRate * timeDelta) {
                currentAngle += (turretProperties.rotateRate * timeDelta);
                if (currentAngle > 360f) {
                    currentAngle %= 360f;
                }
            } else {
                currentAngle = targetAngle;
            }       
            
        } else {
            if (Math.abs(diffAngle) > turretProperties.rotateRate * timeDelta) {
                currentAngle -= (turretProperties.rotateRate * timeDelta);
                
                if (currentAngle < 0f) {
                    currentAngle += 360f;
                }
            } else {
                currentAngle = targetAngle;
            }       
        }
        controlPoint.setAngle(currentAngle);
    }

    public void startAttack() {

    }

}
