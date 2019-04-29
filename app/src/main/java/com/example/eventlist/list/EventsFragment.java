package com.example.eventlist.list;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eventlist.FragmentCommunication.FragmentCommunication;
import com.example.eventlist.adapter.MyEventsRecyclerViewAdapter;
import com.example.eventlist.R;
import com.example.eventlist.data.EventData;
import com.example.eventlist.details.fragment.Event;

import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.io.filefilter.FileFileFilter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class EventsFragment extends Fragment {

    ArrayList<EventData> events = new ArrayList<>();
    String mTitle;
    String mData;
    String mDescription;
    String imageResource;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_events_list, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.list);

        File sdcard = Environment.getExternalStorageDirectory();
        File file = new File(sdcard + "/Event/");
        File[] list = file.listFiles((FileFilter) FileFileFilter.FILE);

        Arrays.sort(list, LastModifiedFileComparator.LASTMODIFIED_COMPARATOR);

        String name = "";
        for (File f : list) {
            name = f.getName();
            int num = 0;
            imageResource = "";

            if (name.endsWith(".txt")) {
                //Get the text file
                File file1 = new File(sdcard + "/Event/" + name);
                //Read text from file
                StringBuilder text = new StringBuilder();
                try {
                    BufferedReader br = new BufferedReader(new FileReader(file1));
                    String line;

                    while ((line = br.readLine()) != null) {
                        if (num == 0)
                            mTitle = line;
                        else if (num == 1)
                            mData = line;
                        else if (num == 2)
                            mDescription = line;
                        else if(num == 3){
                            imageResource = (sdcard + "/Event/" + line);
                        }

                        num++;
                    }

                    events.add(new EventData(mTitle, mData, mDescription, imageResource));
                    MyEventsRecyclerViewAdapter adapter = new MyEventsRecyclerViewAdapter(events, communication);
                    recyclerView.setAdapter(adapter);
                    layoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(layoutManager);
                    br.close();
                } catch (IOException e) {
                    e.getMessage();
                }
            }
        }

        return view;
    }


    FragmentCommunication communication = new FragmentCommunication() {
        @Override
        public void respond(String title, String data, String description, String imageResource) {
            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                Event fragmentB = new Event();
                Bundle bundle = new Bundle();
                bundle.putString("title", title);
                bundle.putString("data", data);
                bundle.putString("description", description);
                bundle.putString("imageResource", imageResource);
                fragmentB.setArguments(bundle);
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.constraintLayout_event, fragmentB).commit();
            }
        }
    };
}