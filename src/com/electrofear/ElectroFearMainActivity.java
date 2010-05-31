package com.electrofear;



import java.util.ArrayList;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class ElectroFearMainActivity extends Activity {

	private View mContinueButton;
	private View mQuitButton;
    private View mStartButton;
    private View mOptionsButton;
    private View mBackground;
    private ArrayList<AnimationDrawable> mAnimation;

    /*-- Button Setup for Main Page, Create an Intent with each Button Press --*/
    private View.OnClickListener startNewGameButtonListener = new View.OnClickListener() {
        public void onClick(View v) {
        	
        }
    };
    
    private View.OnClickListener continueButtonListener = new View.OnClickListener() {
        public void onClick(View v) {
        	
        }
    };
    
    private View.OnClickListener optionsButtonListener = new View.OnClickListener() {
        public void onClick(View v) {
        	
        }
    };
    
    private View.OnClickListener quitButtonListener = new View.OnClickListener() {
        public void onClick(View v) {
        	
        }
    };      
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmenu);

        mStartButton = findViewById(R.id.MainMenuStartButton);
        //mContinueButton = findViewById(R.id.);  //TODO HERE
        mOptionsButton = findViewById(R.id.MainMenuOptionsButton);
        mQuitButton = findViewById(R.id.MainMenuQuitButton); //TODO HERE
        mBackground = findViewById(R.id.mainMenuBackground);
        
        /* Add Listeners */
        mStartButton.setOnClickListener(startNewGameButtonListener);
        //mContinueButton.setOnClickListener(continueButtonListener);
        mOptionsButton.setOnClickListener(optionsButtonListener);
        mQuitButton.setOnClickListener(quitButtonListener);
        
        
       
        //View image = (View) findViewById(R.id.mainMenuPlanes1);
        //Animation hyperspaceJump = AnimationUtils.loadAnimation(this, R.anim.main_menu_bomber_one_animation);
        //image.startAnimation(hyperspaceJump);
        
        //ImageView rocketImage = (ImageView) findViewById(R.id.mainMenuExplosionCityOne);
        //Animation backgroundAnim = AnimationUtils.loadAnimation(this, R.anim.main_menu_city_explosion_one_animation);
        ImageView cityExplosionOne = (ImageView) findViewById(R.id.mainMenuExplosionCityOne);
        ImageView cityExplosionTwo = (ImageView) findViewById(R.id.mainMenuExplosionCityTwo);
        ImageView cityExplosionThree = (ImageView) findViewById(R.id.mainMenuExplosionCityThree);
        ImageView tracerRoundFireOne = (ImageView) findViewById(R.id.mainMenuTracerFireOne);
        ImageView tracerRoundFireTwo = (ImageView) findViewById(R.id.mainMenuTracerFireTwo);
        ImageView tracerRoundFireThree = (ImageView) findViewById(R.id.mainMenuTracerFireThree);
        ImageView tracerRoundFireFour = (ImageView) findViewById(R.id.mainMenuTracerFireFour);
        ImageView tracerRoundFireFive = (ImageView) findViewById(R.id.mainMenuTracerFireFive);
        ImageView bomberOne = (ImageView) findViewById(R.id.mainMenuBomberOne);
        ImageView fightersOne = (ImageView) findViewById(R.id.mainMenuFightersOne);
        ImageView planes = (ImageView) findViewById(R.id.mainMenuPlanes1);
        
        //Start/Setup Interpolators
        Animation bomberOneAnimation = AnimationUtils.loadAnimation(this, R.anim.main_menu_bomber_two_animation);  
        bomberOne.startAnimation(bomberOneAnimation);
        Animation fightersOneAnimation = AnimationUtils.loadAnimation(this, R.anim.main_menu_fighters_one_animation);  
        fightersOne.startAnimation(fightersOneAnimation);       
        Animation planesAnimation = AnimationUtils.loadAnimation(this, R.anim.main_menu_bomber_one_animation);
        planes.startAnimation(planesAnimation);
        
        //City Explosions
        cityExplosionOne.setImageResource(R.anim.main_menu_city_explosion_one_animation);
        cityExplosionTwo.setImageResource(R.anim.main_menu_city_explosion_two_animation);
        cityExplosionThree.setImageResource(R.anim.main_menu_city_explosion_three_animation);
        
        
        //Tracer Fire
        tracerRoundFireOne.setImageResource(R.anim.main_menu_tracerfire_one);
        tracerRoundFireTwo.setImageResource(R.anim.main_menu_tracerfire_two);
        tracerRoundFireThree.setImageResource(R.anim.main_menu_tracerfire_one);
        tracerRoundFireFour.setImageResource(R.anim.main_menu_tracerfire_one);
        tracerRoundFireFive.setImageResource(R.anim.main_menu_tracerfire_two);        
        
        
        mAnimation = new ArrayList<AnimationDrawable>();
        mAnimation.add((AnimationDrawable) cityExplosionOne.getDrawable());
        mAnimation.add((AnimationDrawable) cityExplosionTwo.getDrawable());
        mAnimation.add((AnimationDrawable) cityExplosionThree.getDrawable());
        mAnimation.add((AnimationDrawable) tracerRoundFireOne.getDrawable());
        mAnimation.add((AnimationDrawable) tracerRoundFireTwo.getDrawable());
        mAnimation.add((AnimationDrawable) tracerRoundFireThree.getDrawable());
        mAnimation.add((AnimationDrawable) tracerRoundFireFour.getDrawable());
        mAnimation.add((AnimationDrawable) tracerRoundFireFive.getDrawable());
    }
    
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        
        /*-- Start All Frame Animations --*/
        for(int i = 0; i < mAnimation.size(); i++) {
            if (!((AnimationDrawable)mAnimation.get(i)).isRunning()) {
                ((AnimationDrawable)mAnimation.get(i)).start();
            }
        }
    }
    
    
}