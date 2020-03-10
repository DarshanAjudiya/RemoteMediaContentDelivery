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

        //System.out.println("into oncreate view");
        ConstraintLayout layout=v.findViewById(R.id.ParentLayout);
     //   layout.addView(slide.getView());\
        View x=slide.getView();



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
                if (next!=null) {
                    Componentfragment nextfragment = Componentfragment.getInstance(next);
                    Toast.makeText(getContext(), "after duration", Toast.LENGTH_SHORT).show();

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
        if (slide.getExit_animation()!=null) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    slide.setexitanimations();
                }
            }, (slide.getDuration() - slide.getExit_animation().getDuration()) * 1000);
        }

    }

    @Override
    public void onStop() {
        super.onStop();

        slide.stop();
    }


}
