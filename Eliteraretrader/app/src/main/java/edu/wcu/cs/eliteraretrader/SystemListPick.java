package edu.wcu.cs.eliteraretrader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * This class is a custom ArrayAdapter, it is designed for the SystemList.java class
 *
 * Author Sean Facello
 * Version 18/3/15.
 */
public class SystemListPick extends ArrayAdapter<System>{

    //reference to the ArrayList handed down by the caller
    private ArrayList<System> systemList;

    //the context holding the list view
    private Context context;

    //number referring to the resource of the view
    private int viewSource;

    //reference to the version of xml being run
    private int mode;

    /**
     * initializing the fields, and calling super
     *
     * @param cont the context holding the list view
     * @param textViewResource a number referring to the resource of the view displaying the contents
     * @param sys list of systems to display
     * @param mode this is a variable that indicates which xml the SystemList is using.
     */
    public SystemListPick(Context cont, int textViewResource, ArrayList<System> sys, int mode){
        super(cont, textViewResource, sys);
        this.context = cont;
        this.viewSource = textViewResource;
        this.systemList = sys;
        this.mode = mode;
    }

    /**
     * This function will set each of the individual items in the list as they are needed.
     *
     * @param position the position in the list that corresponds to the item
     * @param listItemView defines the design of the item in the list
     * @param parent parent container holding the list
     * @return the completed item is returned
     */
    @Override
    public View getView(int position, View listItemView, ViewGroup parent){
        View v = listItemView;

        if(v == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(viewSource, parent, false);
        }

        if(mode == AppConstants.PICKER_START || mode == AppConstants.PICKER_END) {
            setPickerItem(v, position);
        }else{
            setDisplayItem(v, position);
        }

        return v;
    }

    /**
     * This method will set up the list item that allows for a star system to be choosen
     *
     * @param v the list item
     * @param position the position of the element in systemList mapping to the new item
     */
    public void setPickerItem(View v, int position){
        TextView name = (TextView) v.findViewById(R.id.system_name);
        name.setText(systemList.get(position).getName());
    }

    /**
     * This method will set up the list item that displays a step in a route.
     *
     * @param v the list item
     * @param position the position of the element in systemList mapping to the new item
     */
    public void setDisplayItem(View v, int position){
        if(systemList.get(position).getBuy().equals("sell")){
            TextView buyOrSell = (TextView) v.findViewById(R.id.buy_sell);
            buyOrSell.setText(systemList.get(position).getBuy());

            TextView system = (TextView) v.findViewById(R.id.sys_name);
            system.setText(systemList.get(position).getName());

            TextView station = (TextView) v.findViewById(R.id.station_name);
            station.setText("");

            TextView commodity = (TextView) v.findViewById(R.id.commodity);
            commodity.setText("");

            TextView dist = (TextView) v.findViewById(R.id.distance);
            dist.setText("");

            TextView num = (TextView) v.findViewById(R.id.allotment);
            num.setText("");
            return;
        }
        TextView buyOrSell = (TextView) v.findViewById(R.id.buy_sell);
        buyOrSell.setText(systemList.get(position).getBuy());

        TextView station = (TextView) v.findViewById(R.id.station_name);
        station.setText(systemList.get(position).getStation());

        TextView commodity = (TextView) v.findViewById(R.id.commodity);
        commodity.setText(systemList.get(position).getCommodity());

        TextView name = (TextView) v.findViewById(R.id.sys_name);
        name.setText(systemList.get(position).getName());

        TextView dist = (TextView) v.findViewById(R.id.distance);
        dist.setText(Integer.toString(systemList.get(position).getDistance()));

        TextView num = (TextView) v.findViewById(R.id.allotment);
        num.setText(Integer.toString(systemList.get(position).getCap()));
    }
}
