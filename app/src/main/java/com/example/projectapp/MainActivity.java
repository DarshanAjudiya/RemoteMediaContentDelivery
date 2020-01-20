package com.example.projectapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {
    public String[] EXTERNAL_PERMISSIONS={Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE};

    DatabaseHelper helper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helper = new DatabaseHelper(this);

        checkpermision();

        getData data = new getData(this);
        data.execute();

        FrameLayout layout= findViewById(R.id.fragmentContainer);

        PlaylistModel list1=helper.getplaylist(null);
        list1.setHeight(0);
        list1.setWidth(0);

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
        layout.addView(imageView,0);
        VideoView videoView=new VideoView(this);
        videoView.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.animvideo));
        FrameLayout.LayoutParams param=new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        videoView.setLayoutParams(param);
        videoView.setX(0);
        System.out.println("Height:"+imageView.getHeight());
        videoView.setY(500);
        layout.addView(videoView,1);

        videoView.start();
        videoView.setAnimation(anims);
*/

       /* View view = getview(type);
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




    public void checkpermision()
    {
        int permission= ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if(permission!=PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,EXTERNAL_PERMISSIONS,1);
        }
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.INSTALL_PACKAGES)!=PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.INSTALL_PACKAGES},1);
        }
    }

}
