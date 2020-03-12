package com.example.projectapp;

import android.os.Bundle;
import android.os.Handler;
import android.transition.Slide;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;



//This Component Fragment will show single slide and apply animation
public class Componentfragment extends Fragment {


    SlideModel slide;   //declaration of SlideModel to be displayed on Fragment

    //Static method that return new Object of ComponentFragment
    public static Componentfragment getInstance( SlideModel slide)
    {
        Componentfragment componentfragment=new Componentfragment();

        //Create Bundle To pass as argument to componentFragment
        Bundle b=new Bundle();

        //put slide to Bundle as serializable
        b.putSerializable("slide",slide);

        //set Bundle as argument of componentfragment
        componentfragment.setArguments(b);

        //return instance of CompomentFragment
        return componentfragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //get SlideModel from argument
        //getArgument( )returns Bundle and getSerializable() is method of Bundle passed as argument
        slide= (SlideModel) getArguments().getSerializable("slide");



    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //call getView method of slide and return that view
        return slide.getView();


    }

    @Override
    public void onStart() {
        super.onStart();

        //start background audio of slide by calling start_audio
        slide.start_audio();

        //create Handler and call postdelayed with Duration same as Duration of currentslide

        if (slide.getExit_animation()!=null)    //is there exitanimation on current slide
        {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    //start exit animation of current slide
                    slide.setexitanimations();
                }
            }, (slide.getDuration() - slide.getExit_animation().getDuration()) * 1000); //time is set as entry and exit animation
            // must be considered under whole durationof slide
        }


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                //get object of next slide
                SlideModel next=slide.getNextSlide();
                if (next!=null)//Is there is next slide
                {

                    //get Instance of ComponentFragment and send next slideModel instance asparameter
                    Componentfragment nextfragment = Componentfragment.getInstance(next);
                    Toast.makeText(getContext(), "after duration", Toast.LENGTH_SHORT).show();

                    //begin transaction .replace current fragment with new Fragment
                    FragmentManager manager = getFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.replace(R.id.fragmentContainer, nextfragment);
                    transaction.commit();
                }
                else
                {
                    onStop();
                    getActivity().finish();
                }

            }
        },slide.getDuration()*1000);


    }

    @Override
    public void onStop() {
        super.onStop();

        //stop current slide
        slide.stop();
    }


}
