package com.example.projectapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.projectapp.systemsdata.adBotService;

public class MainActivity extends AppCompatActivity {
    //Declared Permission Required
    public String[] EXTERNAL_PERMISSIONS = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    //DatabaseHelper class object declaration to manage  SQlite DB connection
    DatabaseHelper helper;
    PlaylistModel list1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Check for  available Updates


        //Initilize and start background service that monitor Battery Status and Memory Status
        Intent service = new Intent(getApplicationContext(), adBotService.class);
        startService(service);

        //Initialise databasehelper class instance
        if (savedInstanceState == null) {
            helper = new DatabaseHelper(this);

            //Check for Permission
            checkpermision();
            FrameLayout layout = findViewById(R.id.fragmentContainer);

            //get First Playlist from database
            list1 = helper.getplaylist(null);
            // list1.printall();

            if (list1 != null) {

                //Get Display Width and height using DisplayMetrics
                DisplayMetrics displayMetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

                //set Display width/height as parent_height/width of playlist
                list1.parent_width = Float.valueOf(displayMetrics.widthPixels);
                list1.parent_height = Float.valueOf(displayMetrics.heightPixels);

                //call init method pf playlist to initialize playlist's slides and components
                list1.init(this);

                //get ComponentFragment instance
                // pass playlistModel's slideModel instance as parameter
                Componentfragment componentfragment = Componentfragment.getInstance(list1.getSlide());
                //Initialize fragment manager to begin transaction of fragment
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();

                //Replace fragment with fragment container using fragment transaction
                transaction.replace(R.id.fragmentContainer, componentfragment);

                //commit transaction to show fragment on display in main activity
                transaction.commit();


            }
            else
            {
                //Show Dialog Box that shows there is no data in database
            }

        }

    }


    public void checkpermision() {
        //First check if Read/write_external_storage permission is already granted or not
        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            //If permission is not granted request for permission
            ActivityCompat.requestPermissions(this, EXTERNAL_PERMISSIONS, 1);
        }
        //Check if Install Package permission is granted or not
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.INSTALL_PACKAGES) != PackageManager.PERMISSION_GRANTED) {
            //request for permission
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INSTALL_PACKAGES}, 1);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
