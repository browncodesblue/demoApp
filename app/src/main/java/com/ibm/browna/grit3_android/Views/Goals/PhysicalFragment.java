package com.ibm.browna.grit3_android.Views.Goals;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ibm.browna.grit3_android.R;

/**
 * Created by browna on 2/8/2017.
 */

public class PhysicalFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_goal_path,container,false);


        return v;
    }
}
