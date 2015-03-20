package edu.wcu.cs.eliteraretrader;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CheckBox;

/**
 * This Activity holds the shared preferences of system permits, and automatically saves them upon
 * leaving the Activity.
 *
 * Author Sean Facello
 * Version 18/3/15.
 */
public class PermitActivity extends Activity {

    //the check boxes on this screen
    CheckBox shin;
    CheckBox vega;

    //saved state indicators (for updating system list if needed
    boolean shinstat;
    boolean vegastat;

    /**
     * Setting up the initial state of the screen.
     *
     * @param savedInstanceState not used, artifact from the super class
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permit);

        shin = (CheckBox) this.findViewById(R.id.shinrarta);
        vega = (CheckBox) this.findViewById(R.id.vega);

        restoreState();
    }

    /**
     * Special version of onPause that automatically saves the preferences for the permits
     */
    @Override
    protected void onPause(){
        super.onPause();
        saveState();
    }

    /**
     * This method will restore the values of the permits as of the last time you edited this screen
     */
    public void restoreState(){
        SharedPreferences prefs = getSharedPreferences(AppConstants.PREF_NAME, Context.MODE_PRIVATE);
        boolean shinCheck = prefs.getBoolean(AppConstants.SHINRARTA, false);
        boolean vegaCheck = prefs.getBoolean(AppConstants.VEGA, false);
        shin.setChecked(shinCheck);
        vega.setChecked(vegaCheck);
        shinstat = shinCheck;
        vegastat = vegaCheck;
    }

    /**
     * This method saves the state of the screen during the pausing of the screen.
     */
    public void saveState(){
        SharedPreferences prefs = getSharedPreferences(AppConstants.PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        boolean shinCheck = shin.isChecked();
        boolean vegaCheck = vega.isChecked();
        editor.putBoolean(AppConstants.SHINRARTA, shinCheck);
        editor.putBoolean(AppConstants.VEGA, vegaCheck);
        if(shinstat != shinCheck){
            System current = new System(AppConstants.shinrarta[AppConstants.SYS_NAME],
                    AppConstants.shinrarta[AppConstants.ITEM],
                    AppConstants.shinrarta[AppConstants.STATION_NAME],
                    Integer.parseInt(AppConstants.shinrarta[AppConstants.DISTANCE]),
                    Integer.parseInt(AppConstants.shinrarta[AppConstants.ALLOTMENT]), "buy",
                    Double.parseDouble(AppConstants.shinrarta[AppConstants.ELEMENT_X]),
                    Double.parseDouble(AppConstants.shinrarta[AppConstants.ELEMENT_Y]),
                    Double.parseDouble(AppConstants.shinrarta[AppConstants.ELEMENT_Z]));
            AppConstants.systems.add(AppConstants.SHINRARTA_INDEX, current);
        }
        if(vegastat != vegaCheck){
            System current = new System(AppConstants.shinrarta[AppConstants.SYS_NAME],
                    AppConstants.shinrarta[AppConstants.ITEM],
                    AppConstants.shinrarta[AppConstants.STATION_NAME],
                    Integer.parseInt(AppConstants.shinrarta[AppConstants.DISTANCE]),
                    Integer.parseInt(AppConstants.shinrarta[AppConstants.ALLOTMENT]), "buy",
                    Double.parseDouble(AppConstants.shinrarta[AppConstants.ELEMENT_X]),
                    Double.parseDouble(AppConstants.shinrarta[AppConstants.ELEMENT_Y]),
                    Double.parseDouble(AppConstants.shinrarta[AppConstants.ELEMENT_Z]));
            if(shinstat || shinCheck){
                AppConstants.systems.add(AppConstants.VEGA_INDEX + 1, current);
            } else {
                AppConstants.systems.add(AppConstants.VEGA_INDEX, current);
            }
        }
        editor.commit();
    }
}

