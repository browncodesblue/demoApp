package com.ibm.browna.grit3_android.Views.Goals;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ibm.browna.grit3_android.R;

import org.w3c.dom.Text;

/**
 * Created by browna on 2/8/2017.
 */

public class MentalFragment extends Fragment {

    TextView mPrompt, mBox1, mBox2, mBox3;
    Button mSave, mFinish;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_goal_path,container,false);
        mPrompt = (TextView) v.findViewById(R.id.draggable_prompt_text);
        mBox1 = (TextView) v.findViewById(R.id.drag_text_1);
        mBox2 = (TextView) v.findViewById(R.id.drag_text_2);
        mBox3 = (TextView) v.findViewById(R.id.drag_text_3);
        mSave = (Button) v.findViewById(R.id.goal_path_save);
        mFinish = (Button) v.findViewById(R.id.goal_path_finish);

        mPrompt.setText("Choose a Mental Path");
        mBox1.setText("Mindfulness");
        mBox2.setText("Journal");
        mBox3.setText("Add Recording");

        mFinish.setVisibility(View.VISIBLE);


        return v;
    }
}
