package com.electrofear;

public class GameParticleUnitObject extends GameObject{
    DrawableBitmap mAnimDrawable;
    
    //Frame Change if applicable -- will need to be set later in constructor
    int currentFrame = 1;
    int totalFrame = 10;
    float currentFrameTime = 0f;
    float totalFrameTime = 1f;
    float particleDurationTime = 4f;
    String alphaChange = "FADE";
    String sizeChange = "CONSTANT";
    float startingAlphaChange = 1.00f;
    //For Wind
    Boolean affectedByWind = true;
    float windDirectionMagnitudeX = 0f;
    float windDirectionMagnitudeY = 0f;
    boolean destroyParticle = false;
    
    //Size of Drawable
    float drawableWidth, drawableHeight;
    
    //Size Changer
    String alphaControl;
    
    
    public GameParticleUnitObject( String inputAlphaChange, 
                                   String inputSizeChange, 
                                   float inputWidth, 
                                   float inputHeight ) {
        
        float unitLength = (float) Math.sqrt( BaseObject.directionWind.x * BaseObject.directionWind.x + BaseObject.directionWind.y * BaseObject.directionWind.y);
        windDirectionMagnitudeX = (BaseObject.directionWind.x / unitLength) * BaseObject.magnitudeWind;
        windDirectionMagnitudeY = (BaseObject.directionWind.y / unitLength) * BaseObject.magnitudeWind;
        alphaChange = inputAlphaChange;
        sizeChange = inputSizeChange;
        drawableWidth = inputWidth;
        drawableHeight = inputHeight;
        
        if (alphaChange.equals("LINEAR_FADING")) {
            startingAlphaChange = 1.0f;
            alphaControl = "DECREASE_ALPHA";
        } else if (alphaChange.equals("UNFADE_FADE")) {
            startingAlphaChange = 0.0f;
            alphaControl = "INCREASE_ALPHA";
        }
        
    }
    
    public void setAnimationDrawable(DrawableBitmap tempParticle) {
        mAnimDrawable = tempParticle;
        mAnimDrawable.updateAlphaValue(startingAlphaChange);
        mAnimDrawable.angle = (float) (Math.random() * 360);
    }

    public void scheduleDrawables(float timeDelta) {
        // TODO Auto-generated method stub
        

        
        if (mAnimDrawable != null) {
            BaseObject.renderSystem.scheduleForDraw(mAnimDrawable);
        }
    }


    
    
    /*
     * Updates Drawables and then schedule them in Renderer
     * 
     */
    public void updateParticle(float timeDelta) {
        currentFrameTime += timeDelta;
        //Set Particle to "Finished" if alpha is 0
        if (this.particleDurationTime < currentFrameTime) {
            destroyParticle = true;
        } else {
            // TODO Auto-generated method stub
            if (mAnimDrawable != null) {
                //startingAlphaChange -= 0.015f;
                alphaAndSizeUpdateFunction(timeDelta); // Changes the "startingAlphaChange" and updates the mAnimDrawable
                
                mAnimDrawable.updateAlphaValue(startingAlphaChange);
                //Calculate Frame if applicable
                if (currentFrame < totalFrame) {                   
                    currentFrame = (int) Math.ceil( ((currentFrameTime / totalFrameTime)) * totalFrame );
                    mAnimDrawable.updateDrawableFrame(currentFrame);
                }
                
                mAnimDrawable.updateCoords( mAnimDrawable.xPosition + windDirectionMagnitudeX, 
                                        mAnimDrawable.yPosition + windDirectionMagnitudeY, drawableWidth,
                                        drawableHeight, mAnimDrawable.angle + 5f);
    
                BaseObject.renderSystem.scheduleForDraw(mAnimDrawable);
            }
        }   
    }
    
    
    
    public void alphaAndSizeUpdateFunction (float timeDelta) {
        if (alphaChange.equals("LINEAR_FADING")) {
            startingAlphaChange -= 0.025f;            
        } else if (alphaChange.equals("UNFADE_FADE")) {
            
            
            if (alphaControl.equals("INCREASE_ALPHA")) {
                startingAlphaChange += 0.015f;
                
                if (startingAlphaChange > 0.7f) {
                    alphaControl = "DECREASE_ALPHA";
                }
                
            } else if (alphaControl.equals("DECREASE_ALPHA")) {
                startingAlphaChange -= 0.015f;
            }
        }
        
        //state changes
        
        if (sizeChange.equals("LINEAR_ENLARGE") ) {
            drawableWidth += 0.8f;
            drawableHeight += 0.8f;
        }
        
    }    
    
}
