package com.example.eventlist.adapter;

import android.app.Dialog;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eventlist.FragmentCommunication.FragmentCommunication;
import com.example.eventlist.R;
import com.example.eventlist.data.EventData;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;



public class MyEventsRecyclerViewAdapter extends RecyclerView.Adapter<MyEventsRecyclerViewAdapter.ViewHolder>{


   private ArrayList<EventData> mEvents;
   private FragmentCommunication mCommunicator;
   Dialog mDialog;
   File imgFile;

    public MyEventsRecyclerViewAdapter(ArrayList<EventData> events, FragmentCommunication Communicator) {
        this.mEvents = events;
        this.mCommunicator = Communicator;
    }

    @NonNull
    @Override
    public MyEventsRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_events, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);

        if(view.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){

        }

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyEventsRecyclerViewAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.eventName.setText(mEvents.get(i).getTitle());
        if(!mEvents.get(i).getImageResource().equals(""))
        {
            imgFile = new File(mEvents.get(i).getImageResource());
            viewHolder.picture.setImageURI(Uri.fromFile(imgFile));
            Log.d("patrz", mEvents.get(i).getImageResource());


        }else
            viewHolder.picture.setImageResource(R.drawable.colorsofautumn);

        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    File path = Environment.getExternalStorageDirectory();
                    File dir  = new File(path + "/Event/");

                    String filename = "Event_" + mEvents.get(i).getTitle() + ".txt";
                    String picFile = "Event_" + mEvents.get(i).getTitle() + ".jpeg";
                    File file = new File(dir, filename);
                    File pic = new File(dir, picFile);
                    file.delete();
                    if(pic.exists())
                        pic.delete();

                }catch (Exception e){
                    Log.d("error", e.getMessage());
                }
                mEvents.remove(i);
                notifyItemRemoved(i);
                notifyItemRangeChanged(i, mEvents.size());
            }
        });


        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mCommunicator.respond(mEvents.get(i).getTitle(), mEvents.get(i).getData(), mEvents.get(i).getDescription(), R.drawable.colorsofautumn );
                mCommunicator.respond(mEvents.get(i).getTitle(), mEvents.get(i).getData(), mEvents.get(i).getDescription(), mEvents.get(i).getImageResource() );
                if(v.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    mDialog = new Dialog(v.getContext());
                    mDialog.setContentView(R.layout.fragment_event);

                    if(!mEvents.get(i).getImageResource().equals(""))
                    {
                        imgFile = new File(mEvents.get(i).getImageResource());
                        viewHolder.picture.setImageURI(Uri.fromFile(imgFile));


                    }else
                        viewHolder.picture.setImageResource(R.drawable.colorsofautumn);

                    TextView mTitle = mDialog.findViewById(R.id.title);
                    ImageView mPicture = mDialog.findViewById(R.id.picture);
                    TextView mfildOne = mDialog.findViewById(R.id.fieldOne);
                    TextView mfieldTwo = mDialog.findViewById(R.id.fieldTwo);
                    TextView mfieldThree = mDialog.findViewById(R.id.fieldThree);

                    mTitle.setText(mEvents.get(i).getTitle());
                    if(!mEvents.get(i).getImageResource().equals(""))
                        mPicture.setImageURI(Uri.fromFile(imgFile));
                    else
                        mPicture.setImageResource(R.drawable.colorsofautumn);

                    mfildOne.setText(mEvents.get(i).getTitle());
                    mfieldTwo.setText(mEvents.get(i).getData());
                    mfieldThree.setText(mEvents.get(i).getDescription());
                    mDialog.show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mEvents.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public final View mView;
        ImageView picture;
        TextView eventName;
        ImageButton delete;
        ConstraintLayout constraintLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            picture = itemView.findViewById(R.id.item_image);
            eventName = itemView.findViewById(R.id.content);
            delete = itemView.findViewById(R.id.delete);
            constraintLayout = itemView.findViewById(R.id.constraintLayout);
        }
    }
}