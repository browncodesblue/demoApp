package com.ibm.browna.grit3_android.Views.Values;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ibm.browna.grit3_android.R;
import com.ibm.browna.grit3_android.Views.Goals.GoalPagerActivity;

/**
 * Created by browna on 2/8/2017.
 */

public class GoalDirectionsFragment extends Fragment {

    Button mNext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_goal_directions,container,false);
        mNext = (Button) v.findViewById(R.id.goals_next_button);
        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 Intent i = new Intent(getActivity(), GoalPagerActivity.class);
                 startActivity(i);
            }
        });
        return v;
    }
}
