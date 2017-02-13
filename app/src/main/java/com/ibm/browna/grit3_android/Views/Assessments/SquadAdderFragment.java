package com.ibm.browna.grit3_android.Views.Assessments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.ibm.browna.grit3_android.Views.Goals.GoalPagerActivity;
import com.ibm.browna.grit3_android.Views.Values.SquadCongratsFragment;
import com.ibm.browna.grit3_android.Views.Values.ValueActivity;
import com.ibm.browna.grit3_android.R;import com.ibm.browna.grit3_android.R;


/**
 * Created by browna on 2/3/2017.
 */

public class SquadAdderFragment extends Fragment {
    Button mInviteButton;
    ImageView mAdder1, mAdder2, mAdder3, mAdder4, mAdder5;
    Boolean one, two, three, four, five;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_squad_adding_contacts,null,false);
        initViews(v);
        mInviteButton =(Button) v.findViewById(R.id.send_squad_invites);
        mInviteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), ValueActivity.class);
                startActivity(i);
                getActivity().finish();
            }
        });
        one =false;
        two = false;
        three = false;
        four = false;
        five = false;
        mAdder1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!one){
                    mAdder1.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.contact_added));
                    one = true;
                    return;
                }
                if (one){
                    mAdder1.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.plus_contact_button));
                    one = false;
                    return;
                }


            }
        });
        mAdder2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!two){
                    mAdder2.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.contact_added));
                    two = true;
                    return;
                }
                if (two){
                    mAdder2.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.plus_contact_button));
                    two = false;
                    return;
                }

            }
        });
        mAdder3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!three){
                    mAdder3.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.contact_added));
                    three = true;
                    return;
                }
                if (three){
                    mAdder3.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.plus_contact_button));
                    three = false;
                    return;
                }
            }
        });
        mAdder4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!four){
                    mAdder4.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.contact_added));
                    four = true;
                    return;
                }
                if (four){
                    mAdder4.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.plus_contact_button));
                    four = false;
                    return;
                }
            }
        });
        mAdder5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!five){
                    mAdder5.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.contact_added));
                    five = true;
                    return;
                }
                if (five){
                    mAdder5.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.plus_contact_button));
                    five = false;
                    return;
                }
            }
        });

        return v;
    }
    private void initViews(View v){
        mAdder1 = (ImageView) v.findViewById(R.id.plus1) ;
        mAdder2 = (ImageView) v.findViewById(R.id.plus2);
        mAdder3 = (ImageView) v.findViewById(R.id.plus3);
        mAdder4 = (ImageView) v.findViewById(R.id.plus4);
        mAdder5 = (ImageView) v.findViewById(R.id.plus5);
        one = false;
        two = false;
        three = false;
        four = false;
        five = false;
    }

}
