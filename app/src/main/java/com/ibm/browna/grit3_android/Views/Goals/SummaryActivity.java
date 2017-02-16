package com.ibm.browna.grit3_android.Views.Goals;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ibm.browna.grit3_android.HRVFragments.HRVViewHolder;
import com.ibm.browna.grit3_android.R;
import com.ibm.browna.grit3_android.Views.Assessments.AssessmentActivity;
import com.ibm.browna.grit3_android.Views.Values.ValueActivity;
import com.ibm.browna.grit3_android.WatsonTone.MainActivity;

/**
 * Created by browna on 2/8/2017.
 */

public class SummaryActivity extends ActionBarActivity {


    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_summary);

        mDrawerList = (ListView)findViewById(R.id.navList);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        setupDrawer();
        addDrawerItems();


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

    private void addDrawerItems() {
        String[] navArray = { "Level Set", "Values","Goals", "HRV", "ToneAnalyzer"};
        mAdapter = new ArrayAdapter<String>(this, R.layout.list_item_nav, navArray);
        mDrawerList.setAdapter(mAdapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return false;
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
