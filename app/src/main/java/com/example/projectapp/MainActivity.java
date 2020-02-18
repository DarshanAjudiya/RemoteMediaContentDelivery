package com.example.projectapp;

import android.Manifest;
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

public class MainActivity extends AppCompatActivity {
    public String[] EXTERNAL_PERMISSIONS = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    DatabaseHelper helper;
    PlaylistModel list1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null)
        {
            helper = new DatabaseHelper(this);

        checkpermision();
        FrameLayout layout = findViewById(R.id.fragmentContainer);
        list1 = helper.getplaylist(null);
        list1.printall();

        DisplayMetrics displayMetrics=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        list1.parent_width= Float.valueOf(displayMetrics.widthPixels);
        list1.parent_height= Float.valueOf(displayMetrics.heightPixels);
        list1.init(this);
        list1.printall();
        System.out.println("\n\n\n\nadding Fragment\n\n\n\n");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Componentfragment componentfragment = Componentfragment.getInstance(list1.getSlide());
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.fragmentContainer, componentfragment);

                transaction.commit();

            }
        }, 5000);

    }
/*
        ImageView imageView=new ImageView(this);
        imageView.setImageResource(R.drawable.b);
        imageView.setScaleType(ImageView.ScaleType.FIT_START);
        imageView.setAdjustViewBounds(false);
        imageView.setBackgroundColor(Color.parseColor("#ffffff"));
        Animation anims= AnimationUtils.loadAnimation(this,android.R.anim.slide_in_left);
        anims.setDuration(2000);
        imageView.setAnimation(anims);
        imageView.setLayoutParams(new FrameLayout.LayoutParams(1000,500));
        VideoView videoView=new VideoView(this);
        videoView.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.animvideo));
        FrameLayout.LayoutParams param=new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        videoView.setLayoutParams(param);
        videoView.setX(0);
        videoView.setZ(2);
        System.out.println("Height:"+imageView.getHeight());
        videoView.setY(200);


        WebView webView =new WebView(this);
        webView.loadUrl("https://stackoverflow.com/questions/35669413/why-framelayout-isnt-setting-z-order-correctly-in-my-layout");
        webView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        webView.setZ(1);

        layout.addView(videoView);
        layout.addView(webView);
        layout.addView(imageView);
        videoView.start();
        videoView.setAnimation(anims);


        View view = getview(type);
        if (view!=null)
        {

        }*/
    }

   /* public View getview(String type)
    {
        switch(type)
        {
            case "image":   ImageView imgview=new ImageView(this);
                            imgview.setImageResource(R.drawable.b);
                            return imgview;

            case "video":   VideoView videov=new VideoView(this);
                            videov.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.animvideo));
                            return videov;
            default:        return null;
        }

    }*/

    public void checkpermision() {
        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, EXTERNAL_PERMISSIONS, 1);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.INSTALL_PACKAGES) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INSTALL_PACKAGES}, 1);
        }
    }


}
