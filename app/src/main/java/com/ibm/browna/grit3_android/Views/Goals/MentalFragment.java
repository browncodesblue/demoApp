package com.ibm.browna.grit3_android.Views.Goals;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ibm.browna.grit3_android.R;

import org.w3c.dom.Text;

/**
 * Created by browna on 2/8/2017.
 */

public class MentalFragment extends Fragment {

    TextView mPrompt, mBoxText1, mBoxText2, mBoxText3;
    RelativeLayout mBox1, mBox2, mBox3;
    Button mSave, mFinish;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_goal_path,container,false);
        mPrompt = (TextView) v.findViewById(R.id.draggable_prompt_text);
        mBoxText1 = (TextView) v.findViewById(R.id.drag_text_1);
        mBoxText2 = (TextView) v.findViewById(R.id.drag_text_2);
        mBoxText3 = (TextView) v.findViewById(R.id.drag_text_3);
        mBox1 =(RelativeLayout) v.findViewById(R.id.draggable_box1);
        mBox2 =(RelativeLayout) v.findViewById(R.id.draggable_box2);
        mBox3 =(RelativeLayout) v.findViewById(R.id.draggable_box3);
        mSave = (Button) v.findViewById(R.id.goal_path_save);
        mFinish = (Button) v.findViewById(R.id.goal_path_finish);

        mPrompt.setText("Choose a Mental Path");
        mBoxText1.setText("Mindfulness");
        mBoxText2.setText("Journal");
        mBoxText3.setText("Add Recording");



        mBox1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBox1.setBackground(getActivity().getResources().getDrawable(R.drawable.goal_green_box));
                mBoxText1.setTextColor(getResources().getColor(R.color.white));
                mBox2.setBackground(getActivity().getResources().getDrawable(R.drawable.sign_up));
                mBoxText2.setTextColor(getResources().getColor(R.color.black));
                mBox3.setBackground(getActivity().getResources().getDrawable(R.drawable.sign_up));
                mBoxText3.setTextColor(getResources().getColor(R.color.black));
            }
        });
        mBox2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBox1.setBackground(getActivity().getResources().getDrawable(R.drawable.sign_up));
                mBoxText1.setTextColor(getResources().getColor(R.color.black));
                mBox2.setBackground(getActivity().getResources().getDrawable(R.drawable.goal_green_box));
                mBoxText2.setTextColor(getResources().getColor(R.color.white));
                mBox3.setBackground(getActivity().getResources().getDrawable(R.drawable.sign_up));
                mBoxText3.setTextColor(getResources().getColor(R.color.black));
            }
        });
        mBox3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBox1.setBackground(getActivity().getResources().getDrawable(R.drawable.sign_up));
                mBoxText1.setTextColor(getResources().getColor(R.color.black));
                mBox2.setBackground(getActivity().getResources().getDrawable(R.drawable.sign_up));
                mBoxText2.setTextColor(getResources().getColor(R.color.black));
                mBox3.setBackground(getActivity().getResources().getDrawable(R.drawable.goal_green_box));
                mBoxText3.setTextColor(getResources().getColor(R.color.white));
            }
        });

        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFinish.setVisibility(View.VISIBLE);
            }
        });
        mFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(),SummaryActivity.class);
                startActivity(i);
                getActivity().finish();
            }
        });

        return v;
    }
}
