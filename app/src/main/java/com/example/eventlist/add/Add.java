package com.example.eventlist.add;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.eventlist.R;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class Add extends AppCompatActivity {

    EditText et1, et2, et3;
    Button bt;
    private static final int WRITE_EXTERNAL_STORAGE_CODE = 1;
    String t1, t2, t3;
    Bitmap bitmap;
    String picPath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        try {
            byte[] byteArray = getIntent().getByteArrayExtra("picture");
            bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            picPath = "exist";
            Log.d("przeslane", "" + bitmap);
        }catch (NullPointerException e){
            Log.d("error", e.getMessage());
        }


        et1 = findViewById(R.id.editText);
        et2 = findViewById(R.id.editText2);
        et3 = findViewById(R.id.editText3);
        bt = findViewById(R.id.button);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t1 = et1.getText().toString().trim();
                t2 = et2.getText().toString().trim();
                t3 = et3.getText().toString().trim();

                if(t1.isEmpty() && t2.isEmpty() && t3.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Enter Event Name and Event Date", Toast.LENGTH_LONG).show();
                }else{
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                        if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                            String[] permision = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                            requestPermissions(permision, WRITE_EXTERNAL_STORAGE_CODE);
                        }else{
                            if(picPath.equals("exist"))
                                saveTxt(t1, t2, t3);
                            else
                                saveTxt2(t1, t2, t3);
                        }
                    }else{
                        if(picPath.equals("exist"))
                            saveTxt(t1, t2, t3);
                        else
                            saveTxt2(t1, t2, t3);
                    }
                    setResult(RESULT_OK);
                    finish();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case WRITE_EXTERNAL_STORAGE_CODE:{
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    saveTxt(t1, t2, t3);
                }else{
                    Toast.makeText(getApplicationContext(), "Storage permission is required to store data", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private void saveTxt(String t1, String t2, String t3) {
        //String time = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(System.currentTimeMillis());

        try{
            File path = Environment.getExternalStorageDirectory();
            File dir  = new File(path + "/Event/");

            File[] files = dir.listFiles();

            //dir.mkdirs();
            //String filename = "Event_" + (files.length+1) + ".txt";
            String filename = "Event_" + t1 + ".txt";

            //String picFilename = "Event_" + (files.length+1);
            String picFilename = "Event_" + t1;

            File file = new File(dir, filename);

            //save picture
            picPath = picFilename + ".jpeg";
            File pic = new File(dir, picPath);
            FileOutputStream fOut = new FileOutputStream(pic);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
            fOut.flush();
            fOut.close();

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(t1 + "\n" + t2 + "\n" + t3 + "\n" + picPath);

            bw.close();
            Toast.makeText(getApplicationContext(), "ok", Toast.LENGTH_LONG).show();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void saveTxt2(String t1, String t2, String t3) {
        try{
            File path = Environment.getExternalStorageDirectory();
            File dir  = new File(path + "/Event/");

            File[] files = dir.listFiles();

            //dir.mkdirs();
            //String filename = "Event_" + (files.length+1) + ".txt";
            String filename = "Event_" + t1 + ".txt";
            File file = new File(dir, filename);

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(t1 + "\n" + t2 + "\n" + t3);

            bw.close();
            Toast.makeText(getApplicationContext(), "ok", Toast.LENGTH_LONG).show();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
