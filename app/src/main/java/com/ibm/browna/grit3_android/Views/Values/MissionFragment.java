package com.ibm.browna.grit3_android.Views.Values;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ibm.browna.grit3_android.R;

/**
 * Created by browna on 2/8/2017.
 */

public class MissionFragment extends Fragment {

    Button mCommit;
    TextView mMissionText, mTimeText;
    EditText mMissionEdit, mTimeEdit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_mission, container,false);
        mCommit =(Button) v.findViewById(R.id.commit_button);
        mMissionEdit=(EditText) v.findViewById(R.id.mission_substance_edit);
        mMissionText =(TextView) v.findViewById(R.id.mission_substance_text);
        mTimeEdit =(EditText) v.findViewById(R.id.mission_time_edit);
        mTimeText =(TextView) v.findViewById(R.id.mission_time_text);


        mTimeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTimeText.setVisibility(View.INVISIBLE);
                mTimeEdit.setVisibility(View.VISIBLE);
            }
        });
        mMissionText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMissionText.setVisibility(View.INVISIBLE);
                mMissionEdit.setVisibility(View.VISIBLE);
            }
        });

        mCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ValueActivity)getActivity()).swapFragments(new GoalDirectionsFragment());
            }
        });

        return v;
    }
}
