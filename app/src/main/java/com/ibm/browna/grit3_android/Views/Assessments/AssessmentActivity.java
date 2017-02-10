package com.ibm.browna.grit3_android.Views.Assessments;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.Toast;

import com.ibm.browna.grit3_android.HRV.HRVActivity;
import com.ibm.browna.grit3_android.HRVFragments.HRVViewHolder;
import com.ibm.browna.grit3_android.R;
import com.ibm.browna.grit3_android.Views.Goals.GoalDirectionsActivity;
import com.ibm.browna.grit3_android.Views.Goals.GoalPagerActivity;
import com.ibm.browna.grit3_android.Views.Values.ValueActivity;
import com.ibm.browna.grit3_android.Views.WelcomeActivity;
import com.ibm.browna.grit3_android.WatsonTone.MainActivity;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;



/**
 * Created by browna on 2/2/2017.
 */

public class AssessmentActivity extends ActionBarActivity {

    LinearLayout pickerButton, storyButton,lifeButton,squadButton;
    ImageView   pickerDot, storyDot, lifeDot, squadDot;
    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private String mActivityTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assess);

        mDrawerList = (ListView)findViewById(R.id.navList);
        pickerButton = (LinearLayout) findViewById(R.id.word_nav);
        storyButton = (LinearLayout) findViewById(R.id.story_nav);
        lifeButton = (LinearLayout) findViewById(R.id.life_nav);
        squadButton = (LinearLayout) findViewById(R.id.squad_nav);

        pickerDot = (ImageView)findViewById(R.id.word_dot);
        storyDot = (ImageView)findViewById(R.id.story_dot);
        lifeDot = (ImageView)findViewById(R.id.life_dot);
        squadDot = (ImageView)findViewById(R.id.squad_dot);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        setupDrawer();
        addDrawerItems();

        mActivityTitle = getTitle().toString();

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().add(R.id.fragmentContainer, new PickerFragment(),"");
        fragmentTransaction.commit();

        addOnClick();

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

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
               /* onDrawerOpened(drawerView);
                getActionBar().setTitle("Navigation!");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()*/
            }
            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                /*onDrawerClosed(view);
                getActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()*/
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }



    private void addOnClick(){
        pickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (pickerDot.getVisibility() != View.VISIBLE){
                    squadDot.setVisibility(View.INVISIBLE);
                    lifeDot.setVisibility(View.INVISIBLE);
                    storyDot.setVisibility(View.INVISIBLE);
                    pickerDot.setVisibility(View.VISIBLE);
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().replace(R.id.fragmentContainer, new PickerFragment());
                    fragmentTransaction.commit();
                }

            }
        });
        storyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    squadDot.setVisibility(View.INVISIBLE);
                    lifeDot.setVisibility(View.INVISIBLE);
                    storyDot.setVisibility(View.VISIBLE);
                    pickerDot.setVisibility(View.INVISIBLE);
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().replace(R.id.fragmentContainer, new WatsonToneFragment());
                    fragmentTransaction.commit();


            }
        });
        lifeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (lifeDot.getVisibility() != View.VISIBLE){
                    squadDot.setVisibility(View.INVISIBLE);
                    lifeDot.setVisibility(View.VISIBLE);
                    storyDot.setVisibility(View.INVISIBLE);
                    pickerDot.setVisibility(View.INVISIBLE);
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().replace(R.id.fragmentContainer, new CheckFragment(),"");
                    fragmentTransaction.commit();
                }

            }
        });
        squadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (squadDot.getVisibility() != View.VISIBLE){
                    squadDot.setVisibility(View.VISIBLE);
                    lifeDot.setVisibility(View.INVISIBLE);
                    storyDot.setVisibility(View.INVISIBLE);
                    pickerDot.setVisibility(View.INVISIBLE);
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().replace(R.id.fragmentContainer, new SquadDirectionsFragment(),"");
                    fragmentTransaction.commit();
                }

            }
        });

    }
    public void swapFragments(Fragment fragment){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment,"");
        fragmentTransaction.commit();
    }
    private void addDrawerItems() {
        String[] navArray = { "Level Set", "Values","Goals", "HRV", "ToneAnalyzer"};
        mAdapter = new ArrayAdapter<String>(this, R.layout.list_item_nav, navArray);
        mDrawerList.setAdapter(mAdapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);

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
