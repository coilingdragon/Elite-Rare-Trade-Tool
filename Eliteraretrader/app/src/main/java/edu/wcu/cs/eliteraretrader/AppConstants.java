package edu.wcu.cs.eliteraretrader;

import android.content.Intent;

import java.util.ArrayList;

/**
 * Holds constant values for the application, exists for organizational purposes.
 *
 * Author Sean Facello
 * Version 18/3/15.
 */
public class AppConstants {

    //List view modes, used as an extra
    static final String MODE = "Mode";
    static final int PICKER_START = 1;
    static final int PICKER_END = 2;
    static final int SINGLES = 3;
    static final int LOOP = 4;

    //splash delay
    static final int WARMUP = 5000;

    //systems alphabetically before permit systems
    static final String BEFORE_SHIN = "Sanuma";
    static final String BEFORE_VEGA = "V1090 Herculis";

    //Shared preferences name and fields
    static final String PREF_NAME = "Permits";
    static final String SHINRARTA = "Shinrarta Dezhra";
    static final String VEGA = "Vega";

    //indexes of permits to add
    static final int SHINRARTA_INDEX = 83;
    static final int VEGA_INDEX = 93;

    //Route loop Intent extras
    static final String LEGS = "Legs";

    //Route common Intent extras
    static final String START = "Start";
    static final String END = "End";
    static final String CARGO = "Cargo";
    static final String HALFSTAT = "HalfStat";

    //Single route Intent extras
    static final String X = "X";
    static final String Y = "Y";
    static final String Z = "Z";

    //activity return extras
    static final String SIGNAL = "Signal";
    static final String SYSTEM = "System";

    //stores the constant list of systems.
    static ArrayList<System> systems;

    //stores the generated route.
    static ArrayList<System> route;

    //constants to refer to different items in the elements of the ArrayLists
    static final int SYS_NAME = 0;
    static final int STATION_NAME = 1;
    static final int DISTANCE = 2;
    static final int ITEM = 3;
    static final int ALLOTMENT = 4;
    static final int ELEMENT_X = 5;
    static final int ELEMENT_Y = 6;
    static final int ELEMENT_Z = 7;

    //permit system entries
    static final String[] shinrarta = {"Shinrarta Dezhra","Jameson Memorial","347",
                                        "Waters of Shinrarta","6","55.72","17.59","27.16"};
    static final String[] vega = {"Vega","Taylor City","1100","Vega Slimeweed","8",
                                    "-21.91","8.13","9.00"};

}
