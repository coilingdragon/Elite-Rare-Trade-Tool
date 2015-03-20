package edu.wcu.cs.eliteraretrader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * This is the general ListView for this application, instances of this ListView can look different
 *
 * Author Sean Facello
 * Version 18/3/15.
 */
public class SystemList extends Activity implements AdapterView.OnItemClickListener {

    //reference to the ListView being used by this instance of the Activity
    private ListView lv;

    //reference to the ArrayAdapter used by this activity
    private ArrayAdapter<System> adapter;

    //holds the mode the instance of the activity was given
    private int mode;

    /**
     * Creates the activity, sets up different ListViews depending on the Mode extra handed down
     * in the savedInstanceState.
     *
     * @param savedInstanceState the saved state of the previous activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        mode = extras.getInt(AppConstants.MODE);
        if(mode == AppConstants.PICKER_START || mode == AppConstants.PICKER_END) {
            setContentView(R.layout.activity_system_list);
            lv = (ListView) findViewById(R.id.systemslist);
            lv.setOnItemClickListener(this);
            adapter = new SystemListPick(this, R.layout.system_list_item, AppConstants.systems, mode);
            lv.setAdapter(adapter);
        }else{
            setContentView(R.layout.activity_system_list_alt);
            lv = (ListView) findViewById(R.id.routelist);
            adapter = new SystemListPick(this, R.layout.system_list_alt_item, AppConstants.route, mode);
            lv.setAdapter(adapter);
        }
    }

    /**
     * This will record the results if this instance was started for a Result
     * @param list last list adapter used
     * @param view the selected row in the list
     * @param position the position that maps to the ArrayList systems
     * @param id id of the row's base layout
     */
    @Override
    public void onItemClick(AdapterView<?> list, View view, int position, long id){
        String sysName = AppConstants.systems.get(position).getName();
        Intent intent = new Intent();
        intent.putExtra(AppConstants.SIGNAL, mode);
        intent.putExtra(AppConstants.SYSTEM, sysName);
        setResult(RESULT_OK, intent);
        finish();
    }


}
