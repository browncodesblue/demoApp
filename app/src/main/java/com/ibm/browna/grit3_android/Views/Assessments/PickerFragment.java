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

public class PickerFragment extends Fragment {

    ImageView mLeftTumbler1,mLeftTumbler2,mLeftTumbler3,mLeftTumbler4,
            mRightTumbler1,mRightTumbler2,mRightTumbler3,mRightTumbler4;
    TextView mTumbler1,mTumbler2,mTumbler3,mTumbler4;
    Button mSaveButton;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_word_pick,null,false);
        initViews(v);

        String [] tumblerWords1 = {"Hat", "Cat", "Words", "Sit"};
        String [] tumblerWords2 = {"Hat", "Cat", "Words", "Sit"};
        String [] tumblerWords3 = {"Hat", "Cat", "Words", "Sit"};
        String [] tumblerWords4 = {"Hat", "Cat", "Words", "Sit"};

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AssessmentActivity)getActivity()).storyButton.callOnClick();
            }
        });

        return v;
    }

    public void initViews(View v){
        mLeftTumbler1 =(ImageView) v.findViewById(R.id.leftArrow1) ;
        mLeftTumbler2 = (ImageView) v.findViewById(R.id.leftArrow2);
        mLeftTumbler3 = (ImageView) v.findViewById(R.id.leftArrow3);
        mLeftTumbler4 = (ImageView) v.findViewById(R.id.leftArrow4);
        mRightTumbler1 = (ImageView) v.findViewById(R.id.RightArrow1);
        mRightTumbler2 = (ImageView) v.findViewById(R.id.RightArrow2);
        mRightTumbler3 = (ImageView) v.findViewById(R.id.RightArrow3);
        mRightTumbler4 = (ImageView) v.findViewById(R.id.RightArrow4);
        mTumbler1 = (TextView) v.findViewById(R.id.word_tumbler1);
        mTumbler2 = (TextView) v.findViewById(R.id.word_tumbler2);
        mTumbler3 = (TextView) v.findViewById(R.id.word_tumbler3);
        mTumbler4 = (TextView) v.findViewById(R.id.word_tumbler4);
        mSaveButton = (Button) v.findViewById(R.id.picker_save_button);
    }
}
