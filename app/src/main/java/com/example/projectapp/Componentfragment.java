package com.example.projectapp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.transition.Slide;
import android.transition.Transition;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class Componentfragment extends Fragment {


    SlideModel slide;

    public static Componentfragment getInstance( SlideModel slide)
    {
        Componentfragment componentfragment=new Componentfragment();
        Bundle b=new Bundle();
        b.putSerializable("slide",slide);
        componentfragment.setArguments(b);
        return componentfragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        slide= (SlideModel) getArguments().getSerializable("slide");
        Slide slidetrans=new Slide(Gravity.LEFT);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_component,container,false);

        System.out.println("into oncreate view");
        ConstraintLayout layout=v.findViewById(R.id.ParentLayout);
     //   layout.addView(slide.getView());\
        View x=slide.getView();
        DisplayMetrics displayMetrics=new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        Float width= Float.valueOf(displayMetrics.widthPixels);
        Float height= Float.valueOf(displayMetrics.heightPixels);

        int w=slide.playlist.getWidth();
        int h=slide.playlist.getHeight();

        float wr=width/w;
        float hr=height/h;
        if (wr<1) {
            x.setScaleX(wr);
            x.setScaleY(wr);
        }
        else
        {
            x.setScaleX(hr);
            x.setScaleY(hr);
        }
        return x;
    }

    @Override
    public void onStart() {
        super.onStart();
        slide.start_audio();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                slide.setexitanimations();
                SlideModel next=slide.getNextSlide();
                Componentfragment nextfragment=Componentfragment.getInstance(next);
                Toast.makeText(getContext(), "after duration", Toast.LENGTH_SHORT).show();

                FragmentManager manager= getFragmentManager();
                FragmentTransaction transaction=manager.beginTransaction();
                transaction.replace(R.id.fragmentContainer,nextfragment);
                transaction.commit();

            }
        },slide.getDuration()*1000);

    }

    @Override
    public void onStop() {
        super.onStop();
        slide.stopAudio();
    }

    public Transition setTransition()
    {
        return null;
    }
}
