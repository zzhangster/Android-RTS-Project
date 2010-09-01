package com.electrofear;


/* Main Camera System - start of camera position is defined in Maps or center of screen by default
 * 
 */
public class CameraMainSystem extends BaseObject{
	float cameraX,cameraY,cameraZ;
	float lookAtX, lookAtY, lookAtZ;
	
	//BoundaryBoxRestriction
	float centerBoundaryX, centerBoundaryY;
	float boundaryWidth, boundaryHeight;
	
	//Vector Version
	Vector3 cameraPosition, cameraLookAt;
	float deceleration = 2.0f;
	GameObject cameraTarget; //in the future if camera needs to follow target
	
	public CameraMainSystem(){
		cameraPosition = new Vector3(0,0,600); //initially set to height 600
		cameraLookAt = new Vector3(0,0,0);
	}
	
	
	public void updateCameraCoords(float cameraX, float cameraY, float cameraZ, float lookAtX, float lookAtY, float lookAtZ) {
		/*this.cameraX = cameraX;
		this.cameraY = cameraY;
		this.cameraZ = cameraZ;
		this.lookAtX = lookAtX;
		this.lookAtY = lookAtY;
		this.lookAtZ = lookAtZ;*/
		cameraPosition.set(cameraX, cameraY, cameraZ);
		cameraLookAt.set(lookAtX, lookAtY, lookAtZ);
		
	}
	
	public void updateDeltaChangeCoords(float changeCameraX, float changeCameraY, float changeCameraZ,
			                            float changeLookAtX, float changeLookAtY, float changeLookAtZ) {
		
		//Set Restrictions to Coordinates, check only Y here
		//Keep it + changeCameraY in condition because it can be positive and negative!
		//1/4 to compensate for current camera height
		if (cameraPosition.y + changeCameraY < centerBoundaryY + boundaryHeight * 1/4 && 
			cameraPosition.y + changeCameraY > centerBoundaryY - boundaryHeight * 1/4) {
			
			cameraPosition.set(cameraPosition.x + changeCameraX, 
							   cameraPosition.y + changeCameraY,
							   cameraPosition.z + changeCameraZ);
			
			cameraLookAt.set(cameraLookAt.x + changeLookAtX, 
							 cameraLookAt.y + changeLookAtY,
							 cameraLookAt.z + changeLookAtZ);
		} else {
			if (cameraPosition.y + changeCameraY > centerBoundaryY + boundaryHeight * 1/4){
				cameraPosition.y = centerBoundaryY + boundaryHeight * 1/4;
				cameraLookAt.y = centerBoundaryY + boundaryHeight * 1/4;
			} else if (cameraPosition.y + changeCameraY < centerBoundaryY - boundaryHeight * 1/4){
				cameraPosition.y = centerBoundaryY - boundaryHeight * 1/4;
				cameraLookAt.y = centerBoundaryY - boundaryHeight * 1/4;
			}
		}
		
	}
	
	public void setBoundaryBox(float centerX, float centerY, float width, float height) {
		centerBoundaryX = centerX;
		centerBoundaryY = centerY;
		boundaryWidth = width;
		boundaryHeight = height;
	}
	
	public Vector3 getCameraPosition(){
		return cameraPosition;
	}
	
	public Vector3 getCameraLookAt(){
		return cameraLookAt;
	}
	
	public void setCameraTarget(GameObject inputObj){
		this.cameraTarget = inputObj;
	}
	
	public void update(float timeDelta, BaseObject parent){
		
	}
	
}
