package edu.wcu.cs.eliteraretrader;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.content.Intent;
import android.os.Handler;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * This is the splash to show disclaimer and also to allow for some set up time.
 *
 * Author Sean Facello
 * Version 18/3/15.
 */
public class splash extends Activity {

    //for invoking new process
    Handler handle;

    //reference to next screen
    Class next = Main.class;

    //setup runnable for timing purposes
    Runnable r = new Runnable() {
        public void run() {
            goToMain();
        }
    };

    /**
     * This method will create the instance of this activity. \
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        AppConstants.systems = new ArrayList<System>();
        AppConstants.route = new ArrayList<System>();
        readSystems();
    }

    /**
     * Set up the timer, and handler.
     */
    @Override
    public void onStart(){
        super.onStart();
        handle = new Handler();

        handle.postDelayed(r, AppConstants.WARMUP);
    }

    /**
     * This method will go to the main screen.
     */
    public void goToMain(){
        Intent nextScreen = new Intent(this, next);
        this.startActivity(nextScreen);
    }

    /**
     * This method builds the list of Systems.
     */
    private void readSystems(){
        BufferedReader reader = null;
        SharedPreferences prefs = getSharedPreferences(AppConstants.PREF_NAME, Context.MODE_PRIVATE);
        boolean shinCheck = prefs.getBoolean(AppConstants.SHINRARTA, false);
        boolean vegaCheck = prefs.getBoolean(AppConstants.VEGA, false);
        try {
            AssetManager am = getAssets();

            InputStream is = am.open("SystemData.csv");
            InputStreamReader isr = new InputStreamReader(is);
            reader = new BufferedReader(isr);
        } catch(IOException e) {
            Toast.makeText(this, "a reading error occurred", Toast.LENGTH_LONG).show();
            finish();
        }
        String line = "";
        String splitChar = ",";
        try{
            while((line = reader.readLine()) != null) {
                String[] sys = line.split(splitChar);
                System current = makeSystem(sys);
                AppConstants.systems.add(current);
                if(current.getName().equals(AppConstants.BEFORE_SHIN) && shinCheck){
                    current = new System(AppConstants.shinrarta[AppConstants.SYS_NAME],
                            AppConstants.shinrarta[AppConstants.ITEM],
                            AppConstants.shinrarta[AppConstants.STATION_NAME],
                            Integer.parseInt(AppConstants.shinrarta[AppConstants.DISTANCE]),
                            Integer.parseInt(AppConstants.shinrarta[AppConstants.ALLOTMENT]), "buy",
                            Double.parseDouble(AppConstants.shinrarta[AppConstants.ELEMENT_X]),
                            Double.parseDouble(AppConstants.shinrarta[AppConstants.ELEMENT_Y]),
                            Double.parseDouble(AppConstants.shinrarta[AppConstants.ELEMENT_Z]));
                    AppConstants.systems.add(current);
                }
                if(current.getName().equals(AppConstants.BEFORE_VEGA) && vegaCheck){
                    current = new System(AppConstants.vega[AppConstants.SYS_NAME],
                            AppConstants.vega[AppConstants.ITEM],
                            AppConstants.vega[AppConstants.STATION_NAME],
                            Integer.parseInt(AppConstants.vega[AppConstants.DISTANCE]),
                            Integer.parseInt(AppConstants.vega[AppConstants.ALLOTMENT]), "buy",
                            Double.parseDouble(AppConstants.vega[AppConstants.ELEMENT_X]),
                            Double.parseDouble(AppConstants.vega[AppConstants.ELEMENT_Y]),
                            Double.parseDouble(AppConstants.vega[AppConstants.ELEMENT_Z]));
                    AppConstants.systems.add(current);
                }
            }
            reader.close();
        }catch(IOException e){
            Toast.makeText(this, "an IO error occured on closing file",Toast.LENGTH_LONG).show();
            finish();

        }

    }

    /**
     * This method will create a new System object.
     * @return returns the initialized System
     */
    private System makeSystem(String[] args){
        System sys = new System(args[AppConstants.SYS_NAME], args[AppConstants.ITEM],
                args[AppConstants.STATION_NAME], Integer.parseInt(args[AppConstants.DISTANCE]),
                Integer.parseInt(args[AppConstants.ALLOTMENT]), "buy",
                Double.parseDouble(args[AppConstants.ELEMENT_X]),
                Double.parseDouble(args[AppConstants.ELEMENT_Y]),
                Double.parseDouble(args[AppConstants.ELEMENT_Z]));
        return sys;
    }
}
