package com.ibm.browna.grit3_android.Views.Assessments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.ibm.browna.grit3_android.R;
import com.ibm.browna.grit3_android.WatsonTone.MainActivity;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.ToneAnalyzer;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneAnalysis;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneCategory;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneScore;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by browna on 2/9/2017.
 */

public class WatsonToneFragment extends Fragment {

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
    private Button mSave;
/*
    private final int ColorUnlikely = Color.GRAY;
    private final int ColorLikely = Color.MAGENTA;
    private final int ColorVeryLikely = Color.RED;

    **/

    public String getTextToAnalyze() {
        return textToAnalyze;
    }

    public void setTextToAnalyze(String textToAnalyze) {
        this.textToAnalyze = textToAnalyze;
    }

    private String textToAnalyze = null;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tone_results,container,false);


        messageToAnalyze = (EditText) v.findViewById(R.id.watson_tone_text_returned);
        analysisButton = (TextView) v.findViewById(R.id.analyze_me);
        tableLayout = (TableLayout) v.findViewById(R.id.tone_table);
        mSave = (Button)v.findViewById(R.id.tone_save_button);
        mSave.setClickable(false);
        // gridView.setRowCount(12);

        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if (getActivity() instanceof AssessmentActivity){
                   ((AssessmentActivity)getActivity()).lifeButton.callOnClick();
               }else{
                   Intent i = new Intent(getActivity(),AssessmentActivity.class);
                   i.putExtra("Page",2);
                   startActivity(i);
               }
            }
        });

        analysisButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                InputMethodManager imm =  (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                if (!messageToAnalyze.getText().toString().isEmpty()) {

                    String message = messageToAnalyze.getText().toString();
                    new ToneAnalyzerCall().execute(message, null, null);
                }

            }

        });

        return v;
    }

    private class ToneAnalyzerCall extends AsyncTask<String, Void, Void> {
        ProgressDialog pdLoading = new ProgressDialog(getActivity());
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
            tableLayout.setVisibility(View.VISIBLE);
            mSave.setClickable(true);
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

                TableRow headRowEmotions = new TableRow(getActivity());

                viewEmotions = new TextView(getActivity());
                viewEmotions.setText("Emotions");
                viewEmotions.setTextColor(Color.BLACK);
                viewEmotions.setTextSize(25f);

                headRowEmotions.addView(viewEmotions);
                tableLayout.addView(headRowEmotions);
                headRowEmotions = null;

                Iterator entries = emotionValuesMap.entrySet().iterator();
                while (entries.hasNext()) {

                    Map.Entry thisEntry = (Map.Entry) entries.next();
                    Log.e("Entry", (String) thisEntry.getKey());


                    viewEmotions = new TextView(getActivity());
                    viewEmotionsScore = new TextView(getActivity());




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

                TableRow socialHead = new TableRow(getActivity());

                viewSocial = new TextView(getActivity());
                viewSocial.setText("Social Tone");
                viewSocial.setTextColor(Color.BLACK);
                viewSocial.setTextSize(25f);
                socialHead.addView(viewSocial);
                tableLayout.addView(socialHead);
                socialHead = null;


                Iterator socials = socialValuesMap.entrySet().iterator();
                while (socials.hasNext()) {

                    Map.Entry thisEntry = (Map.Entry)socials.next();
                    Log.e("Entry", (String) thisEntry.getKey());

                    TableRow socialRow = new TableRow(getActivity());
                    viewSocial = new TextView(getActivity());
                    viewSocialScore = new TextView(getActivity());


                    viewSocial.setText((CharSequence) thisEntry.getKey() + ": ");
                    viewSocial.setTextColor(Color.BLACK);
                    viewSocial.setTextSize(18f);


                    viewSocialScore.setText((CharSequence)thisEntry.getValue());
                    viewSocialScore.setTextColor(showColor((String)thisEntry.getValue()));
                    viewSocialScore.setTextSize(18f);


                    createTableRow(viewSocial, viewSocialScore);

                }



            }


        }


    }


    public void createTableRow(View v1, View v2) {

        TableRow tr = new TableRow(getActivity());

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



    private class MyFocusChangeListener implements  View.OnFocusChangeListener {

        public void onFocusChange(View v, boolean hasFocus){

            if(v.getId() == R.id.messageEditText && !hasFocus) {

                InputMethodManager imm =  (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

            }
        }
    }
}
