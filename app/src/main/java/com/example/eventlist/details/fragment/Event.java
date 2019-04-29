package com.example.eventlist.details.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eventlist.R;


public class Event extends Fragment {

    TextView title;
    ImageView iv;
    TextView fieldOne;
    TextView fieldTwo;
    TextView fieldThree;

    String mTitle = "";
    String mData = "";
    String mDescription = "";
    String imageResource = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event, container, false);

        try{
            mData = getArguments().getString("data");
            mDescription = getArguments().getString("description");
            mTitle = getArguments().getString("title");
            imageResource = getArguments().getString("imageResource");
        }catch (NullPointerException e){
            Log.d("error", e.getMessage());
        }

        title = view.findViewById(R.id.title);
        iv = view.findViewById(R.id.picture);
        fieldOne = view.findViewById(R.id.fieldOne);
        fieldTwo = view.findViewById(R.id.fieldTwo);
        fieldThree = view.findViewById(R.id.fieldThree);

        Log.d("patrz", ""+imageResource);

        if(!imageResource.equals("")) {
            Uri uriData = Uri.parse(imageResource);
            iv.setImageURI(uriData);
        } else {
            iv.setImageResource(R.drawable.colorsofautumn);
        }
        title.setText(mTitle);
        fieldOne.setText(mTitle);
        fieldTwo.setText(mData);
        fieldThree.setText(mDescription);

        return view;
    }

}