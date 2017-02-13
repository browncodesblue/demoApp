package com.ibm.browna.grit3_android.Views.Values;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ibm.browna.grit3_android.R;

/**
 * Created by browna on 2/8/2017.
 */

public class TextFeedbackFragment extends Fragment{

    Button mBack;
    int selector;
    TextView mTitle, mFeedback;

    public TextFeedbackFragment(){
        selector = 3;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_text_feedback,container,false);
        mBack =(Button) v.findViewById(R.id.text_feedback_back);
        mTitle = (TextView)v.findViewById(R.id.title);
        mFeedback = (TextView)v.findViewById(R.id.feedback);

        switch (selector){
            case 1:
                mTitle.setText(R.string.family);
                mFeedback.setText(R.string.family_feedback);
                break;
            case 2:
                mTitle.setText(R.string.Well_being);
                mFeedback.setText(R.string.well_being_feedback);
                break;
            case 3:
                mTitle.setText(R.string.community);
                mFeedback.setText(R.string.community_feedback);
                break;

        }

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ValueActivity)getActivity()).swapFragments(new ValuesFragment());
            }
        });
        return v;
    }

    public void setSelector(int selector) {
        this.selector = selector;
    }
}
