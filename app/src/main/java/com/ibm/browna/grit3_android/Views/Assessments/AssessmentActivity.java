package com.ibm.browna.grit3_android.Views.Assessments;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toolbar;

import com.ibm.browna.grit3_android.R;


/**
 * Created by browna on 2/2/2017.
 */

public class AssessmentActivity extends Activity {

    LinearLayout pickerButton, storyButton,lifeButton,squadButton;
    ImageView   pickerDot, storyDot, lifeDot, squadDot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assess);


        pickerButton = (LinearLayout) findViewById(R.id.word_nav);
        storyButton = (LinearLayout) findViewById(R.id.story_nav);
        lifeButton = (LinearLayout) findViewById(R.id.life_nav);
        squadButton = (LinearLayout) findViewById(R.id.squad_nav);

        pickerDot = (ImageView)findViewById(R.id.word_dot);
        storyDot = (ImageView)findViewById(R.id.story_dot);
        lifeDot = (ImageView)findViewById(R.id.life_dot);
        squadDot = (ImageView)findViewById(R.id.squad_dot);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        //setActionBar(myToolbar);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().add(R.id.fragmentContainer, new PickerFragment(),"");
        fragmentTransaction.commit();

        addOnClick();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
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
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().replace(R.id.fragmentContainer, new StoryFragment());
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
}
