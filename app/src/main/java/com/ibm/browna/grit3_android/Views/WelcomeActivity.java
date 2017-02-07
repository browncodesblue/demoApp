package com.ibm.browna.grit3_android.Views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ibm.browna.grit3_android.R;
import com.ibm.browna.grit3_android.Views.Assessments.AssessmentActivity;

/**
 * Created by browna on 2/2/2017.
 */

public class WelcomeActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Button startButton = (Button) findViewById(R.id.start_Button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AssessmentActivity.class);
                startActivity(i);
            }
        });

    }
}
