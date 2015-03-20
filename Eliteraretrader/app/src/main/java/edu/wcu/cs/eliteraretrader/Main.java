package edu.wcu.cs.eliteraretrader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * This Activity will direct you to the major Activities of the Application.
 *
 * Author Sean Facello
 * Version 18/3/15.
 */
public class Main extends Activity implements View.OnClickListener{

    //button to go to permit page
    Button gotoPermits;

    //button to go to loop router
    Button gotoLoop;

    //button to go to single router
    Button gotoRouter;

    //Class references for linking purposes
    Class permitScrn = PermitActivity.class;
    Class loopScrn = LoopRouter.class;
    Class routeScrn = RouteTo.class;


    /**
     * This is will initalize the buttons
     *
     * @param savedInstanceState not used, inherited from override method
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gotoPermits = (Button) findViewById(R.id.permits);
        gotoLoop = (Button) findViewById(R.id.mult_dir);
        gotoRouter= (Button) findViewById(R.id.one_dir);
        gotoPermits.setOnClickListener(this);
        gotoLoop.setOnClickListener(this);
        gotoRouter.setOnClickListener(this);
    }

    /**
     * This will detect which button was pressed, and create an Intent going to the correct Activity
     * according to the button pressed.
     *
     * @param v the view that was pressed
     */
    @Override
    public void onClick(View v){
        Intent next = null;
        if(v == gotoPermits){
            next = new Intent(this, permitScrn);
        }else if(v == gotoLoop){
            next = new Intent(this, loopScrn);
        }else if(v == gotoRouter){
            next = new Intent(this, routeScrn);
        }
        this.startActivity(next);
    }


}
