package com.ibm.browna.grit3_android.Views.Values;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ibm.browna.grit3_android.R;
import com.ibm.browna.grit3_android.Views.Goals.GoalPagerActivity;


/**
 * Created by browna on 2/3/2017.
 */

public class SquadCongratsFragment extends Fragment {

    Button mPath;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_squad_outstanding,container,false);
        mPath = (Button)v.findViewById(R.id.path_Button);
        mPath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ValueActivity)getActivity()).swapFragments(new ValuesFragment());
            }
        });


        return v;
    }
}
