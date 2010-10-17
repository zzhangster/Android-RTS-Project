package com.electrofear.parser;

import java.util.ArrayList;

public class GlobalParsedXMLDataRepository {
    private ArrayList<GlobalDataFaction> mFactionData;
    private ArrayList<GlobalDataVehicle> mVehicleData;
    private ArrayList<GlobalDataInfantry> mInfantryData;
    private ArrayList<GlobalDataTurret> mTurretData;
    private ArrayList<GlobalDataGraphic> mGraphic;
    private ArrayList<GlobalDataGraphicAnimation> mGraphicAnimation;
    private ArrayList<GlobalDataWeapon> mWeapon;
    private ArrayList<GlobalDataParticle> mParticle;
    
    
    
    
    public GlobalParsedXMLDataRepository(){
        mFactionData = new ArrayList();
        mVehicleData = new ArrayList();
        mInfantryData = new ArrayList();
        mTurretData = new ArrayList();
        mGraphic = new ArrayList();
        mWeapon = new ArrayList();
        mGraphicAnimation = new ArrayList();
        mParticle = new ArrayList();
    }

    public void addParticle(GlobalDataParticle inputParticle){
        mParticle.add(inputParticle);
    }

    public GlobalDataParticle getParticleById(String id){
        GlobalDataParticle iterateParticle;
        for (int i = 0; i < mParticle.size(); i++) {
            iterateParticle = mParticle.get(i); 
            if (iterateParticle.objectId.equals(id)){
                return iterateParticle;
            }
        }
        return null;
    }
    
    public ArrayList<GlobalDataGraphic> getAllGraphicStaticData () {
        return mGraphic;
    }
    
    public ArrayList<GlobalDataGraphicAnimation> getAllAnimationGraphicStaticData () {
        return mGraphicAnimation;
    }    
        
    public void addGraphicAnimation(GlobalDataGraphicAnimation inputGraphicAnimation){
        mGraphicAnimation.add(inputGraphicAnimation);
    }

    public GlobalDataGraphicAnimation getGraphicAnimationById(String id){
        GlobalDataGraphicAnimation iterateGraphicAnimation;
        for (int i = 0; i < mGraphicAnimation.size(); i++) {
            iterateGraphicAnimation = mGraphicAnimation.get(i); 
            if (iterateGraphicAnimation.nameId.equals(id)){
                return iterateGraphicAnimation;
            }
        }
        return null;
    }       
    
    public void addWeapon(GlobalDataWeapon inputWeapon){
        mWeapon.add(inputWeapon);
    }

    public GlobalDataWeapon getWeaponById(String id){
        GlobalDataWeapon iterateWeapon;
        for (int i = 0; i < mWeapon.size(); i++) {
            iterateWeapon = mWeapon.get(i); 
            if (iterateWeapon.weaponId.equals(id)){
                return iterateWeapon;
            }
        }
        return null;
    }        
    
    public void addGraphic(GlobalDataGraphic inputGraphic){
        mGraphic.add(inputGraphic);
    }

    public GlobalDataGraphic getGraphicById(String id){
        GlobalDataGraphic iterateGraphic;
        for (int i = 0; i < mGraphic.size(); i++) {
            iterateGraphic = mGraphic.get(i); 
            if (iterateGraphic.nameId.equals(id)){
                return iterateGraphic;
            }
        }
        return null;
    }       
    
    
    public void addTurret(GlobalDataTurret inputTurret){
        mTurretData.add(inputTurret);
    }

    public GlobalDataTurret getTurretById(String id){
        GlobalDataTurret iterateTurret;
        for (int i = 0; i < mTurretData.size(); i++) {
            iterateTurret = mTurretData.get(i); 
            if (iterateTurret.turretId.equals(id)){
                return iterateTurret;
            }
        }
        return null;
    }     
    
    public void addInfantryUnit(GlobalDataInfantry inputUnit){
    	mInfantryData.add(inputUnit);
    }

    public GlobalDataInfantry getInfantryUnitById(String id){
    	GlobalDataInfantry iterateUnit;
        for (int i = 0; i < mInfantryData.size(); i++) {
        	
        	iterateUnit = mInfantryData.get(i);
            if (iterateUnit.objectId.equals(id)){
                return iterateUnit;
            }
        }
        return null;
    }    
    
    
    public void addVehicleUnit(GlobalDataVehicle inputUnit){
    	mVehicleData.add(inputUnit);
    }

    public GlobalDataVehicle getVehicleUnitById(String id){
    	GlobalDataVehicle iterateUnit;
        for (int i = 0; i < mVehicleData.size(); i++) {
        	
        	iterateUnit = mVehicleData.get(i);
            if (iterateUnit.objectId.equals(id)){
                return iterateUnit;
            }
        }
        return null;
    }      
    
    public void addFaction(GlobalDataFaction inputFaction){
        mFactionData.add(inputFaction);
    }

    public GlobalDataFaction getFactionById(String id){
        GlobalDataFaction iterateFaction;
        for (int i = 0; i < mFactionData.size(); i++) {
            iterateFaction = mFactionData.get(i); 
            if (iterateFaction.name.equals(id)){
                return iterateFaction;
            }
        }
        return null;
    }
    
    
    
    
}

