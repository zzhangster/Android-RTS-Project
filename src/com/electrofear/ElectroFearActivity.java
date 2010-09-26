package com.electrofear;

import java.io.InputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import android.opengl.GLSurfaceView;
import com.electrofear.Game;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MotionEvent;

/* Core Activity that main menu or other path will call to start game engine
 * This will create Game object, the game object will handle gamethreads
 */
public class ElectroFearActivity extends Activity implements SensorEventListener{

    private GLSurfaceView myGLSurfaceView;
    private Game myGame;
    private float mLastTouchTime = 0;
    
    @Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);
        //Finding the view in main layout
        myGLSurfaceView = (GLSurfaceView) findViewById(R.id.glsurfaceview);
        
        //16 bit no z-buffer :( need to investigate further
        myGLSurfaceView.setEGLConfigChooser(false);
        
        // Create new game and pass myGLSurfaceView
        myGame = new Game();
        myGame.setSurfaceView(myGLSurfaceView);
        
        

        
        //PARSING VARIABLE PARAMETERS
        startParse(); // Parse all main functions before bootstrapping
        
        
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int defaultWidth = 480;
        int defaultHeight = 320;
        if (dm.widthPixels != defaultWidth) {
            float ratio =((float)dm.widthPixels) / dm.heightPixels;
            defaultWidth = (int)(defaultHeight * ratio);
        }       
        //Call Bootstrap
        myGame.bootstrap(this, dm.widthPixels, dm.heightPixels, defaultWidth, defaultHeight);
        myGLSurfaceView.setRenderer(myGame.getRenderer());
    }
    


    /**
     * This methos is called when the Parsing Button is passed to begin parse XML file content
     * @return
     * @throws Exception
     */
    /*private String getParsedMyXML() throws Exception {
        StringBuffer inLine = new StringBuffer();

        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser sp = spf.newSAXParser();


        XMLReader xr = sp.getXMLReader();

        XMLHandler myExampleHandler = new XMLHandler();
        xr.setContentHandler(myExampleHandler);

        InputStream in = this.getResources().openRawResource(R.raw.infantry_object_propertis); 

        xr.parse(new InputSource(in));
        XMLDataSet parsedExampleDataSet = myExampleHandler.getParsedData();
        inLine.append(parsedExampleDataSet.toString());
        in.close();
        return inLine.toString();
    }*/
    
    public String startParse() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            
            //Parse Tank Data
            InputStream in = this.getResources().openRawResource(R.raw.rules); 
            Document dom = builder.parse(in);
            XMLParser.startParsing(dom);
            
            //Parse Infantry Data
            

        } catch (Exception e) {
            throw new RuntimeException(e);
        } 
        return "";
    }


    
    @Override
	protected void onResume() {
        super.onResume();
        myGLSurfaceView.onResume();
        myGame.onResume();
    }
    
    @Override
	protected void onPause() {
        super.onPause();
        myGLSurfaceView.onPause();
        myGame.onPause();
    }
    
    protected void onDestroy() {
        //DebugLog.d("AndouKun", "onDestroy()");
    	myGame.stop();
        super.onDestroy();
        
    }

    
    public boolean onTouchEvent(MotionEvent event) {
    	//if (!myGame.isPaused()) {
    	myGame.onTouchEvent(event);
	    	
	        final long time = System.currentTimeMillis();
	        if (event.getAction() == MotionEvent.ACTION_MOVE && time - mLastTouchTime < 32) {
		        try {
		            Thread.sleep(32);
		        } catch (InterruptedException e) {
		            
		        }
		        //Locking rendering thread
		        myGame.getRenderer().checkRenderingIsFinshed();
	        }
	        mLastTouchTime = time;
    	//}
        return true;
    }    
    
    public boolean onKeyDown(int keyCode, KeyEvent event){
    	if (keyCode == KeyEvent.KEYCODE_BACK) {
    		showDialog(20);
    		return true;
    	} else {
	    	myGame.onKeyDownEvent(keyCode);
			return true;
    	}
    }
    
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // TODO Auto-generated method stub
        
    }

    public void onSensorChanged(SensorEvent event) {
        // TODO Auto-generated method stub
        
    }
    
    /* Create Dialog Section
     * =====================
     * 20 => Quit Dialog
     * =====================
     */
    protected Dialog onCreateDialog(int id) {
        Dialog dialog = null;
        if (id == 20) {
        	
            dialog = new AlertDialog.Builder(this)
                .setTitle(R.string.quit_game_dialog_title)
                .setPositiveButton(R.string.quit_game_dialog_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    	finish();
                    }
                })
                .setNegativeButton(R.string.quit_game_dialog_cancel, null)
                .setMessage(R.string.quit_game_dialog_message)
                .create();
        }
        return dialog;
    }

}
