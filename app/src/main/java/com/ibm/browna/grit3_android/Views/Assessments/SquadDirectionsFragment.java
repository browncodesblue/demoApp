package com.ibm.browna.grit3_android.Views.Assessments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ibm.browna.grit3_android.R;


/**
 * Created by browna on 2/3/2017.
 */

public class SquadDirectionsFragment extends Fragment {

    Button mAddfromFB;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_squad_builder_directions,null,false);
        mAddfromFB =(Button) v.findViewById(R.id.add_from_facebook);
        mAddfromFB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AssessmentActivity)getActivity()).swapFragments(new SquadAdderFragment());
            }
        });

        return v;
    }


}
