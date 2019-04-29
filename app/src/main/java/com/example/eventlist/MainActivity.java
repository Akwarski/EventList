package com.example.eventlist;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.eventlist.adapter.MyEventsRecyclerViewAdapter;
import com.example.eventlist.add.Add;
import com.example.eventlist.details.fragment.Event;
import com.example.eventlist.list.EventsFragment;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton fab1, fab2;
    int orientation;
    FragmentManager fragmentManagerEvent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setFirstFragment();

        //Check orientation
        orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            landspace();
        }


        fab1 = findViewById(R.id.fab1);
        fab2 = findViewById(R.id.fab2);

        //with random picture
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Add.class);
                startActivityForResult(intent,1);
            }
        });

        //with photo
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 2);
            }
        });
    }

    private void setFirstFragment(){
        EventsFragment eventsFragment = new EventsFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.parent, eventsFragment);
        fragmentTransaction.commit();
    }

    private void landspace() {
        Event event = new Event();
        fragmentManagerEvent = getSupportFragmentManager();
        fragmentManagerEvent.beginTransaction()
                .replace(R.id.constraintLayout_event, event)
                .commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK)
            setFirstFragment();
        if(requestCode == 2){
            setFirstFragment();
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");

            ByteArrayOutputStream bStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, bStream);
            byte[] byteArray = bStream.toByteArray();

            Intent intent = new Intent(getApplicationContext(), Add.class);
            intent.putExtra("picture", byteArray);
            startActivity(intent);
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}