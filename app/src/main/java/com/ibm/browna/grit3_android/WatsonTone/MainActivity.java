package com.ibm.browna.grit3_android.WatsonTone;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.ibm.browna.grit3_android.HRVFragments.HRVViewHolder;
import com.ibm.browna.grit3_android.Views.Assessments.AssessmentActivity;
import com.ibm.browna.grit3_android.Views.Assessments.PickerFragment;
import com.ibm.browna.grit3_android.Views.Assessments.WatsonToneFragment;
import com.ibm.browna.grit3_android.Views.Goals.GoalPagerActivity;
import com.ibm.browna.grit3_android.Views.Values.ValueActivity;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.ToneAnalyzer;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.SentenceTone;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneAnalysis;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneCategory;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneScore;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneOptions;


import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;
import com.ibm.browna.grit3_android.R;


public class MainActivity extends AppCompatActivity {


    private EditText messageToAnalyze;
    private TextView analsisTextView;
    private TextView analysisButton;
    private Map emotionValuesMap = new HashMap(5);
    private String probabilityEmotions[] = new String[5];
    private String probabilitySocial[] = new String[5];
    private  Map socialValuesMap = new HashMap(5);

    private final String  LIKELY = "Likely";
    private final  String VERYLIKELY = "Very Likely";
    private final  String UNLIKELY = "Not likely";
    private TableLayout tableLayout;
    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private String mActivityTitle;

/*
    private final int ColorUnlikely = Color.GRAY;
    private final int ColorLikely = Color.MAGENTA;
    private final int ColorVeryLikely = Color.RED;


    **/

   /* public String getTextToAnalyze() {
        return textToAnalyze;
    }

    public void setTextToAnalyze(String textToAnalyze) {
        this.textToAnalyze = textToAnalyze;
    }

    private String textToAnalyze = null;


    public MainActivity() {
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_values);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        mDrawerList = (ListView)findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        setupDrawer();
        addDrawerItems();

        mActivityTitle = getTitle().toString();

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().add(R.id.fragmentContainer, new WatsonToneFragment(),"");
        fragmentTransaction.commit();


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
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }






    private class ToneAnalyzerCall extends AsyncTask<String, Void, Void> {
        ProgressDialog pdLoading = new ProgressDialog(MainActivity.this);
        ToneAnalysis tone = null;
        boolean success = false;

        @Override
        protected Void doInBackground(String... params) {

            ToneAnalyzer service = new ToneAnalyzer(ToneAnalyzer.VERSION_DATE_2016_05_19);
            service.setUsernameAndPassword("260ba98a-1ec2-405b-8795-55b861930b54", "6Lth0ZwxtJE6");
            //  service.setEndPoint("https://gateway.watsonplatform.net/tone-analyzer/api");
            // EditText text = (EditText) findViewById(R.id.analyzeEditText);
            Log.e("Param", params[0]);
            String value = params[0];

          //  tone.addSentencesTone(sentenceTone);

            // Call the service and get the tone
            tone = service.getTone(value,null ).execute();




            success = true;


            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            pdLoading.setMessage("\tLoading...");
            pdLoading.show();
        }


        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            TextView viewEmotions;
            TextView viewEmotionsScore;
            TextView viewSocial;
            TextView viewSocialScore;
            tableLayout.removeAllViews();
            int probColor;

            //this method will be running on UI thread
            if (success) {
                pdLoading.dismiss();
                emotionValuesMap.clear();
                socialValuesMap.clear();



                for (ToneCategory tc : tone.getDocumentTone().getTones()) {


                    for (ToneScore ts : tc.getTones()) {


                        String probability;

                        if (ts.getScore() < 0.3) {

                            probability = UNLIKELY;


                        } else if (ts.getScore() >= 0.3 && ts.getScore() < 0.6) {

                            probability = LIKELY;


                        } else {

                            probability = VERYLIKELY;

                        }


                        if (tc.getId().equals("emotion_tone")) {

                            emotionValuesMap.put(ts.getName(), probability);


                        } else if (tc.getId().equals("social_tone")) {

                            Log.e("social tone:", tc.getName());

                            socialValuesMap.put(ts.getName(), probability);


                        }

                    }


                }



            // now dynamically populate our grid with Emotions and Social tones

         //       tl.addView(tr, new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

                TableRow headRowEmotions = new TableRow(getApplicationContext());

                viewEmotions = new TextView(getApplicationContext());
                viewEmotions.setText("Emotions");
                viewEmotions.setTextColor(Color.BLACK);
                viewEmotions.setTextSize(20f);

                headRowEmotions.addView(viewEmotions);
                tableLayout.addView(headRowEmotions);
                headRowEmotions = null;

             //   GridLayout.LayoutParams param =new GridLayout.LayoutParams();

             //   param.setGravity(Gravity.LEFT);

            //    viewEmotions.setLayoutParams (param);

            //    viewEmotionsScore = new TextView(getApplicationContext());
           //     viewEmotionsScore.setText("");
             //   tableLayout.addView(viewEmotionsScore);

          //      GridLayout.LayoutParams param1 =new GridLayout.LayoutParams();

          //      param1.setGravity(Gravity.CENTER);
            //    viewEmotionsScore.setLayoutParams(param1);



                Iterator entries = emotionValuesMap.entrySet().iterator();
                while (entries.hasNext()) {

                    Map.Entry thisEntry = (Map.Entry) entries.next();
                    Log.e("Entry", (String) thisEntry.getKey());


                    viewEmotions = new TextView(getApplicationContext());
                    viewEmotionsScore = new TextView(getApplicationContext());




                    viewEmotions.setText((CharSequence) thisEntry.getKey() + ": ");
                    viewEmotions.setTextColor(Color.BLACK);
                    viewEmotions.setTextSize(18f);
                    viewEmotions.setGravity(Gravity.LEFT);


                    viewEmotionsScore.setText((CharSequence)thisEntry.getValue());
                    viewEmotionsScore.setTextColor(showColor((String)thisEntry.getValue()));
                    viewEmotionsScore.setTextSize(18f);

                    createTableRow(viewEmotions,viewEmotionsScore);



                    /**
                    GridLayout.LayoutParams paramEmotions =new GridLayout.LayoutParams();

                    paramEmotions.setGravity(Gravity.LEFT);

                    viewEmotions.setLayoutParams (paramEmotions);



                    tableLayout.addView(viewEmotionsScore);

                    GridLayout.LayoutParams paramScore =new GridLayout.LayoutParams();

                    paramScore.setGravity(Gravity.CENTER);

                    viewEmotions.setLayoutParams (paramScore);

                    **/

                }

                TableRow socialHead = new TableRow(getApplicationContext());

                viewSocial = new TextView(getApplicationContext());
                viewSocial.setText("Social Tone");
                viewSocial.setTextColor(Color.BLACK);
                viewSocial.setTextSize(20f);
                socialHead.addView(viewSocial);
                tableLayout.addView(socialHead);
                socialHead = null;


                /**
                GridLayout.LayoutParams paramS =new GridLayout.LayoutParams();

                paramS.setGravity(Gravity.LEFT);

                viewSocial.setLayoutParams (paramS);

                viewSocialScore = new TextView(getApplicationContext());
                viewSocialScore.setText("");
                gridView.addView(viewSocialScore);

                GridLayout.LayoutParams paramS1 =new GridLayout.LayoutParams();

                paramS1.setGravity(Gravity.CENTER);
                viewSocialScore.setLayoutParams(paramS1);

                **/

                Iterator socials = socialValuesMap.entrySet().iterator();
                while (socials.hasNext()) {

                    Map.Entry thisEntry = (Map.Entry)socials.next();
                    Log.e("Entry", (String) thisEntry.getKey());

                    TableRow socialRow = new TableRow(getApplicationContext());
                    viewSocial = new TextView(getApplicationContext());
                    viewSocialScore = new TextView(getApplicationContext());




                    viewSocial.setText((CharSequence) thisEntry.getKey() + ": ");
                    viewSocial.setTextColor(Color.BLACK);
                    viewSocial.setTextSize(18f);


                    viewSocialScore.setText((CharSequence)thisEntry.getValue());
                    viewSocialScore.setTextColor(showColor((String)thisEntry.getValue()));
                    viewSocialScore.setTextSize(18f);


                    createTableRow(viewSocial, viewSocialScore);




                    /**

                    GridLayout.LayoutParams paramEmotions =new GridLayout.LayoutParams();

                    paramEmotions.setGravity(Gravity.LEFT);

                    viewSocial.setLayoutParams (paramEmotions);



                    gridView.addView(viewSocialScore);

                    GridLayout.LayoutParams paramScore =new GridLayout.LayoutParams();

                    paramScore.setGravity(Gravity.CENTER);

                    viewSocial.setLayoutParams (paramScore);

                     **/






                }



            }


    }


    }


    public void createTableRow(View v1, View v2) {

        TableRow tr = new TableRow(this);

        tr.addView(v1);
        tr.addView(v2);
        tableLayout.addView(tr);



    }


    private int showColor(String probability) {

        int color;

            if (probability.equals(UNLIKELY)) {

                color = Color.GRAY;

            } else if (probability.equals(LIKELY)) {

                color = Color.MAGENTA;
            } else {

                color = Color.RED;
            }
        return  color;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class MyFocusChangeListener implements  View.OnFocusChangeListener {

        public void onFocusChange(View v, boolean hasFocus){

            if(v.getId() == R.id.messageEditText && !hasFocus) {

                InputMethodManager imm =  (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

            }
        }
    }


}