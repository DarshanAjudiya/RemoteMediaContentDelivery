package com.example.projectapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {
    String type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getData data=new getData();
        data.execute();
        FrameLayout layout=(FrameLayout)findViewById(R.id.fragmentContainer);


        ImageView imageView=new ImageView(this);
        imageView.setImageResource(R.drawable.b);
        imageView.setScaleType(ImageView.ScaleType.FIT_START);
        imageView.setAdjustViewBounds(false);
        imageView.setBackgroundColor(Color.parseColor("#ffffff"));
        Animation anims= AnimationUtils.loadAnimation(this,android.R.anim.slide_in_left);
        anims.setDuration(2000);
        imageView.setAnimation(anims);
        imageView.setLayoutParams(new FrameLayout.LayoutParams(1000,500));
        layout.addView(imageView);
       /* View view = getview(type);
        if (view!=null)
        {

        }*/
    }
    public View getview(String type)
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

    }



}
