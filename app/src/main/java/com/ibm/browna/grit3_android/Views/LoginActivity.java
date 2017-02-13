package com.ibm.browna.grit3_android.Views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.ibm.browna.grit3_android.R;


/**
 * Created by browna on 2/2/2017.
 */

public class LoginActivity extends Activity  {
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
