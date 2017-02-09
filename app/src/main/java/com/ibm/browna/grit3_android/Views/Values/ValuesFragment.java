package com.ibm.browna.grit3_android.Views.Values;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.ibm.browna.grit3_android.R;
import com.ibm.browna.grit3_android.Views.Goals.GoalPagerActivity;

/**
 * Created by browna on 2/8/2017.
 */

public class ValuesFragment extends Fragment {

    Button mSave;
    RelativeLayout mDragBox3, mDragBox2,mDragBox1;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_drag_rank_boxes,container,false);
        mSave =(Button) v.findViewById(R.id.drag_rank_save);
        mDragBox3 = (RelativeLayout)v.findViewById(R.id.draggable_box3);

        mDragBox3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ValueActivity)getActivity()).swapFragments(new TextFeedbackFragment());

            }
        });
        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ValueActivity)getActivity()).swapFragments(new GrowthFragment());
            }
        });
        return v;
    }
}
