package com.ibm.browna.grit3_android.Views.Assessments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ibm.browna.grit3_android.R;


/**
 * Created by browna on 2/2/2017.
 */

public class CheckFragment extends Fragment {

    ImageView mButton1, mButton2, mButton3, mButton4, mButton5, mButton6, mButton7;
    Button mCheckSaveButton;
    TextView mCountofFive, mQuestion;
    int count;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_life_check,null,false);
        initViews(v);

        mCheckSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (count){
                    case 1:
                        setNewQuestion(2,R.string.nunber_two_of_five,R.string.life_check_question_2);
                        count++;
                        break;
                    case 2:
                        setNewQuestion(2,R.string.nunber_three_of_five,R.string.life_check_question_3);
                        count++;
                        break;
                    case 3:
                        setNewQuestion(2,R.string.nunber_four_of_five,R.string.life_check_question_4);
                        count++;
                        break;
                    case 4:
                        setNewQuestion(2,R.string.nunber_five_of_five,R.string.life_check_question_5);
                        count++;
                        break;
                    case 5:
                        ((AssessmentActivity)getActivity()).squadButton.callOnClick();
                        count=1;
                        break;
                }

            }
        });

        return v;
    }
    public void setNewQuestion(int i, int newText, int newQuestion){
        mCountofFive.setText(getActivity().getString(newText));
        mQuestion.setText(getActivity().getString(newQuestion));
    }

    private void initViews(View v){
        mButton1 = (ImageView)v.findViewById(R.id.life_check_button_1) ;
        mButton2 = (ImageView)v.findViewById(R.id.life_check_button_2);
        mButton3 = (ImageView)v.findViewById(R.id.life_check_button_3);
        mButton4 = (ImageView)v.findViewById(R.id.life_check_button_4);
        mButton5 = (ImageView)v.findViewById(R.id.life_check_button_5);
        mButton6 = (ImageView)v.findViewById(R.id.life_check_button_6);
        mButton7 = (ImageView)v.findViewById(R.id.life_check_button_7);

        mCountofFive = (TextView) v.findViewById(R.id.life_check_count);
        mQuestion = (TextView) v.findViewById(R.id.life_check_prompt);
        mCheckSaveButton = (Button)v.findViewById(R.id.life_check_save) ;
        count = 1;
    }
}
