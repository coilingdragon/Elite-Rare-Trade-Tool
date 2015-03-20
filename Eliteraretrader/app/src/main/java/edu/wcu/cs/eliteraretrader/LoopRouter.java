package edu.wcu.cs.eliteraretrader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

/**
 * This Activity allows you to determine the values for a looping route, then calculate the route
 *
 * Author Sean Facello
 * Version 18/3/15.
 */
public class LoopRouter extends Activity implements View.OnClickListener{

    public static final int LOOP_REQUEST_CODE = 997;

    //buttons
    Button startlist;
    Button endlist;
    Button go;

    //check boxes
    CheckBox half;

    //edit texts
    EditText start;
    EditText end;
    EditText cargo;
    EditText legs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loop_router);
        startlist = (Button) findViewById(R.id.loop_start_list);
        endlist = (Button) findViewById(R.id.loop_end_list);
        go = (Button) findViewById(R.id.loop_go);
        half = (CheckBox) findViewById(R.id.loop_half);
        start = (EditText) findViewById(R.id.loop_start);
        end = (EditText) findViewById(R.id.loop_end);
        cargo = (EditText) findViewById(R.id.loop_cargo);
        legs = (EditText) findViewById(R.id.loop_legs);
        startlist.setOnClickListener(this);
        endlist.setOnClickListener(this);
        go.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == startlist){
            Intent i = setStartPickerExtras(v);
            this.startActivityForResult(i, LOOP_REQUEST_CODE);
        }else if(v == endlist){
            Intent i = setEndPickerExtras(v);
            this.startActivityForResult(i, LOOP_REQUEST_CODE);
        }else if(v == go){
            Intent i = setLoopRouterExtras(v);
            Toast.makeText(this, "calculating route now", Toast.LENGTH_LONG).show();
            Router r = new Router();
            r.routeMaker(i);
            this.startActivity(i);
        }
    }

    /**
     * Helper method to describe what is going on in onClick, setting extras for the starting system
     * picking ListView
     * @param v the view that sent the "clicked" message
     * @return the prepared Intent
     */
    private Intent setStartPickerExtras(View v){
        Intent i = new Intent(this, SystemList.class);
        i.putExtra(AppConstants.MODE, AppConstants.PICKER_START);
        return i;
    }

    /**
     * Helper method to describe what is going on in onClick, setting extras for the ending system
     * picking ListView
     * @param v the view that sent the "clicked" message
     * @return the prepared Intent
     */
    private Intent setEndPickerExtras(View v){
        Intent i = new Intent(this, SystemList.class);
        i.putExtra(AppConstants.MODE, AppConstants.PICKER_END);
        return i;
    }

    /**
     * Helper method to describe what is going on in onClick, setting all the necessary extras in
     * order to calculate a looping route.
     * @param v the view that sent the "clicked" message
     * @return the prepared Intent
     */
    private Intent setLoopRouterExtras(View v) {
        Intent i = new Intent(this, SystemList.class);
        i.putExtra(AppConstants.MODE, AppConstants.LOOP);
        String startsys = start.getText().toString();
        String endsys = end.getText().toString();
        boolean halfstat = half.isChecked();
        int cargoamt;
        int numlegs;
        try {
            cargoamt = Integer.parseInt(cargo.getText().toString());
            numlegs = Integer.parseInt(legs.getText().toString());

        } catch (NumberFormatException e) {
            Toast.makeText(this, "an invalid input for a number occurred", Toast.LENGTH_LONG).show();
            return i;
        }
        if (startsys.equals("") || endsys.equals("")) {
            Toast.makeText(this, "one of the text fields is blank", Toast.LENGTH_LONG).show();
            return i;
        }
        i.putExtra(AppConstants.START, startsys);
        i.putExtra(AppConstants.END, endsys);
        i.putExtra(AppConstants.CARGO, cargoamt);
        i.putExtra(AppConstants.HALFSTAT, halfstat);
        i.putExtra(AppConstants.LEGS, numlegs);
        return i;
    }

    /**
     * This method will set the starting system text field after either of the picking activities
     * are finished
     * @param requestCode the unique request identifier.
     * @param resultCode the result code received from the called activity
     * @param data the Intent returned by the called activity
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        Bundle extra = data.getExtras();
        if(extra != null){
            int sig = extra.getInt(AppConstants.SIGNAL);
            String system = extra.getString(AppConstants.SYSTEM);
            if(sig == AppConstants.PICKER_START) {
                start.setText(system);
            } else {
                end.setText(system);
            }
        }

        data.getData();
    }

}
