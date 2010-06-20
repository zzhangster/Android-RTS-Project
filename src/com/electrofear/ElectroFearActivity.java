package com.electrofear;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.opengl.GLSurfaceView;
import com.electrofear.Game;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.animation.AnimationUtils;

/* Core Activity that main menu or other path will call to start game engine
 * This will create Game object, the game object will handle gamethreads
 */
public class ElectroFearActivity extends Activity implements SensorEventListener{

    private GLSurfaceView myGLSurfaceView;
    private Game myGame;

    
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
            InputStream in = this.getResources().openRawResource(R.raw.tank_object_properties); 
            Document dom = builder.parse(in);
            XMLParser.parseByType(XMLParser.ParserType.VEHICLE, dom);

            //Parse Infantry Data
            

        } catch (Exception e) {
            throw new RuntimeException(e);
        } 
        return "";
    }


    
    protected void onResume() {
        super.onResume();
        myGLSurfaceView.onResume();
        myGame.onResume();
    }
    
    protected void onPause() {
        super.onPause();
        myGLSurfaceView.onPause();
        myGame.onPause();
    }
    
    protected void onDestroy(){
        
    }
    
    
    
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // TODO Auto-generated method stub
        
    }

}
