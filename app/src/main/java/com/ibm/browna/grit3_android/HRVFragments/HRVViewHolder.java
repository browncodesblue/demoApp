package com.ibm.browna.grit3_android.HRVFragments;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.androidplot.xy.CatmullRomInterpolator;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.PointLabelFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.ibm.browna.grit3_android.R;
import com.ibm.browna.grit3_android.Views.Assessments.AssessmentActivity;
import com.ibm.browna.grit3_android.Views.Assessments.PickerFragment;
import com.ibm.browna.grit3_android.Views.Goals.GoalPagerActivity;
import com.ibm.browna.grit3_android.Views.Values.ValueActivity;
import com.ibm.browna.grit3_android.WatsonTone.MainActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;

/**
 * Created by browna on 2/10/2017.
 */

public class HRVViewHolder extends ActionBarActivity {
    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private String mActivityTitle;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_values);

        mDrawerList = (ListView)findViewById(R.id.navList);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        Intent i = new Intent(getApplicationContext(),AssessmentActivity.class);
                        startActivity(i);
                        break;
                    case 1:
                        Intent i1 = new Intent(getApplicationContext(),ValueActivity.class);
                        startActivity(i1);
                        break;
                    case 2:
                        Intent i2 = new Intent(getApplicationContext(),GoalPagerActivity.class);
                        startActivity(i2);
                        break;
                    case 3:
                        Intent i3 = new Intent(getApplicationContext(),HRVViewHolder.class);
                        startActivity(i3);
                        break;
                    case 4:
                        Intent i4 = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(i4);
                        break;
                }

            }
        });
        setupDrawer();
        addDrawerItems();
        mActivityTitle = getTitle().toString();

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().add(R.id.fragmentContainer, new HRVFragment(),"");
        fragmentTransaction.commit();
    }



    /**
     * When menu button are pressed
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }



    public boolean onPrepareOptionsMenu(Menu menu) {
       // menu.findItem(R.id.action_settings).setEnabled(menuBool);
        //menu.findItem(R.id.action_settings).setVisible(menuBool);
        return true;
    }


    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
            }
            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    private void addDrawerItems() {
        String[] navArray = { "Level Set", "Values","Goals", "HRV", "ToneAnalyzer"};
        mAdapter = new ArrayAdapter<String>(this, R.layout.list_item_nav, navArray);
        mDrawerList.setAdapter(mAdapter);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
}
