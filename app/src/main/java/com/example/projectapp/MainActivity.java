package com.example.projectapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getData data=new getData();
        data.execute();
        FrameLayout layout=(FrameLayout)findViewById(R.id.framelayout);
        FrameLayout.LayoutParams params= (FrameLayout.LayoutParams) layout.getLayoutParams();
        params.height=200;
        params.width=200;

    }
}
