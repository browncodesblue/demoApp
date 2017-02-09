package com.ibm.browna.grit3_android.Views.Values;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ibm.browna.grit3_android.R;

/**
 * Created by browna on 2/8/2017.
 */

public class MissionFragment extends Fragment {

    Button mCommit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_mission, container,false);
        mCommit =(Button) v.findViewById(R.id.commit_button);
        mCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ValueActivity)getActivity()).swapFragments(new GoalDirectionsFragment());
            }
        });

        return v;
    }
}
