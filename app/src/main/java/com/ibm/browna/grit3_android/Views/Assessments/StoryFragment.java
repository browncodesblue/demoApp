package com.ibm.browna.grit3_android.Views.Assessments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.ibm.browna.grit3_android.R;


/**
 * Created by browna on 2/2/2017.
 */

public class StoryFragment extends Fragment {

    ImageView mStoryRecord;
    Button mStorySave;
    int count;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_story_record, null, false);
        count = 1;
        mStoryRecord =(ImageView) v.findViewById(R.id.story_record_button);
        mStorySave = (Button) v.findViewById(R.id.story_save_button);
        mStorySave.setFocusable(false);
        mStoryRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               switch (count){
                   case 1:
                       mStoryRecord.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.story_pause));
                       count++;
                       break;
                   case 2:
                        mStoryRecord.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.story_re_record));
                        mStorySave.setFocusable(true);
                        count=3;
                        break;
                   case 3:
                        mStoryRecord.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.story_record));
                        mStorySave.setFocusable(false);
                        count = 1;
                }
            }
        });

        mStorySave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AssessmentActivity)getActivity()).lifeButton.callOnClick();
            }
        });

        return v;
    }
}
