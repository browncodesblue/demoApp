package com.ibm.browna.grit3_android.Views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ibm.browna.grit3_android.R;


/**
 * Created by browna on 2/2/2017.
 */

public class LoginActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_login);

        Button signFacebook =(Button) findViewById(R.id.facebook_Button);
        signFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), WelcomeActivity.class);
                startActivity(i);
            }
        });


    }
}
