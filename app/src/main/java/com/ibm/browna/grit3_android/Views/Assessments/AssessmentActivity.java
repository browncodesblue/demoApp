package com.ibm.browna.grit3_android.Views.Assessments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.ibm.browna.grit3_android.HRVFragments.HRVViewHolder;
import com.ibm.browna.grit3_android.R;
import com.ibm.browna.grit3_android.Views.Goals.GoalPagerActivity;
import com.ibm.browna.grit3_android.Views.Values.ValueActivity;
import com.ibm.browna.grit3_android.WatsonTone.MainActivity;


/**
 * Created by browna on 2/2/2017.
 */

public class AssessmentActivity extends ActionBarActivity {

    /**
     * This class is the activity that holds the initial assessement of the user after login
     * In the application it is referred to as the level-set because no one likes being assessed
     * The general model here is that the activity holds the nav and the fragment
     */

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

        //Assigning the variables to resource elements


        //Assigning the member variable to elements of the layoutfile

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

        //Setting up the actionbar
        setSupportActionBar(myToolbar);

        //Setting up the Drawer nav and the hamburger menu
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        setupDrawer();
        addDrawerItems();

        //Creates the intial fragment and places it in this activity
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().add(R.id.fragmentContainer, new PickerFragment(),"");
        fragmentTransaction.commit();

        /**Adds all click listeners to navigation elements*/
        addOnClick();

        /**Allows navigation to a specific page upon starting this activity by pulling data from the
        * intent that starts the activity if data was sent with the intent */
        if (getIntent().hasExtra("Page")){
            int page = getIntent().getIntExtra("Page",0);
            switch (page){
                case 0:
                    pickerButton.callOnClick();
                    break;
                case 1:
                    storyButton.callOnClick();
                    break;
                case 2:
                    lifeButton.callOnClick();
                    break;
                case 3:
                    squadButton.callOnClick();
                    break;
            }
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {}
            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {}
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    /**this method set all of the click listeners in the nav for the buttons it replaces the current
     * fragment with the fragemtn associate with the button click and set the indicator to active and
     * turns off all other indicators.
     */
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

    public void swapFragments(Fragment fragment){
        /** removes the current fragment and repalces it with a new fragment*/
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment,"");
        fragmentTransaction.commit();
    }
    private void addDrawerItems() {
        /**creates the list of items for the nav drawer and sets the adapter*/
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

    /**
     * This method and getLocactionOnScreen both identify an edit text on the screen
     * and close the soft keyboard if it is open
     * */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        boolean handleReturn = super.dispatchTouchEvent(ev);

        View view = getCurrentFocus();

        int x = (int) ev.getX();
        int y = (int) ev.getY();

        if(view instanceof EditText){
            View innerView = getCurrentFocus();

            if (ev.getAction() == MotionEvent.ACTION_UP &&
                    !getLocationOnScreen(innerView).contains(x, y)) {

                InputMethodManager input = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                input.hideSoftInputFromWindow(getWindow().getCurrentFocus()
                        .getWindowToken(), 0);
            }
        }

        return handleReturn;
    }
    protected Rect getLocationOnScreen(View mEditText) {
        Rect mRect = new Rect();
        int[] location = new int[2];

        mEditText.getLocationOnScreen(location);

        mRect.left = location[0];
        mRect.top = location[1];
        mRect.right = location[0] + mEditText.getWidth();
        mRect.bottom = location[1] + mEditText.getHeight();

        return mRect;
    }


}
