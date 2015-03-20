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
 * This Activity allows you to determine the values for a single way route, then calculate the route
 *
 * Author Sean Facello
 * Version 18/3/15.
 */
public class RouteTo extends Activity implements View.OnClickListener{

    public static final int REQUEST_CODE = 999;

    //Buttons in the layout
    Button list;
    Button go;

    //Check boxes
    CheckBox half;

    //EditTexts
    EditText end;
    EditText start;
    EditText x;
    EditText y;
    EditText z;
    EditText cargo;

    /**
     * This sets up references to the xml Views, and also sets up onClickListeners for the
     * items with interactions.
     * @param savedInstanceState inherited, not used
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_to);
        list = (Button) findViewById(R.id.single_list);
        go = (Button) findViewById(R.id.single_go);
        half = (CheckBox) findViewById(R.id.single_half);
        start = (EditText) findViewById(R.id.single_start);
        end = (EditText) findViewById(R.id.single_end);
        x = (EditText) findViewById(R.id.xval);
        y = (EditText) findViewById(R.id.yval);
        z = (EditText) findViewById(R.id.zval);
        cargo = (EditText) findViewById(R.id.single_cargo);
        list.setOnClickListener(this);
        go.setOnClickListener(this);
    }

    /**
     * This method will determine what to do when something has been clicked.
     *
     * @param v the view that sent the "clicked" message
     */
    @Override
    public void onClick(View v){
        if(v == list){
            Intent i = setPickerExtras(v);
            this.startActivityForResult(i, REQUEST_CODE);
        }else if(v == go){
            Intent i = setRouterExtras(v);
            if(i != null) {
                this.startActivity(i);
            }
        }
    }

    /**
     * Helper method to describe what is going on in onClick, setting extras for the picking ListView
     * @param v the view that sent the "clicked" message
     * @return the prepared Intent
     */
    private Intent setPickerExtras(View v){
        Intent i = new Intent(this, SystemList.class);
        i.putExtra(AppConstants.MODE, AppConstants.PICKER_START);
        return i;
    }

    /**
     * Helper method to describe what is going on in onClick, setting all the necessary extras in
     * order to calculate a one way route.
     * @param v the view that sent the "clicked" message
     * @return the prepared Intent
     */
    private Intent setRouterExtras(View v){
        Intent i = null;
        String startsys = start.getText().toString();
        String endsys = end.getText().toString();
        boolean halfstat = half.isChecked();
        double xpos;
        double ypos;
        double zpos;
        int cargoamt;
        try {
            xpos = Double.parseDouble(x.getText().toString());
            ypos = Double.parseDouble(y.getText().toString());
            zpos = Double.parseDouble(z.getText().toString());
            cargoamt = Integer.parseInt(cargo.getText().toString());
        }catch(NumberFormatException e){
            Toast.makeText(this, "an invalid input for a number occurred", Toast.LENGTH_LONG).show();
            return i;
        }
        if(startsys.equals("") || endsys.equals("")){
            Toast.makeText(this, "one of the text fields is blank", Toast.LENGTH_LONG).show();
            return i;
        }
        i = new Intent(this, SystemList.class);
        i.putExtra(AppConstants.MODE, AppConstants.SINGLES);
        i.putExtra(AppConstants.START, startsys);
        i.putExtra(AppConstants.END, endsys);
        i.putExtra(AppConstants.X, xpos);
        i.putExtra(AppConstants.Y, ypos);
        i.putExtra(AppConstants.Z, zpos);
        i.putExtra(AppConstants.CARGO, cargoamt);
        i.putExtra(AppConstants.HALFSTAT, halfstat);
        Toast.makeText(this, "calculating route now", Toast.LENGTH_LONG).show();
        Router r = new Router();
        r.routeMaker(i);
        return i;
    }

    /**
     * This method will set the starting system text field after the picking activity finishes.
     * @param requestCode the unique request identifier.
     * @param resultCode the result code received from the called activity
     * @param data the Intent returned by the called activity
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        Bundle extra = data.getExtras();
        if(extra != null){
            String system = extra.getString(AppConstants.SYSTEM);
            start.setText(system);
        }

        data.getData();
    }

}
