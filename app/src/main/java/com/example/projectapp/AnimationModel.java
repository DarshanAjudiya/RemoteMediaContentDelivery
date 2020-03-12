package com.example.projectapp;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

//AniamationModel class used to store animation data and create Android.view.Animation object
public class AnimationModel {
    private String type;        //animation type
    private Integer delay;      //delay before starting aniamation
    private Integer duration;   //Duration of animation


    //constructor to initialize instance variables
    public AnimationModel(String type, Integer delay, Integer duration) {
        this.type = type;
        this.delay = delay;
        this.duration = duration;
    }

    //getter and setter methods of instance variables
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getDelay() {
        return delay;
    }

    public void setDelay(Integer delay) {
        this.delay = delay;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public void printall() {
        System.out.println("Animation");

        System.out.println(type);
        System.out.println(duration);
        System.out.println(delay);
    }


    //getAnimation method create object of Android.view.Animation
    public Animation getAnimation(Context context) {
        Animation animation = null;
        //check type and create Instance of Animation relatively
        switch (type) {
            case "slide-in":
            case "slide-in-left":   animation = AnimationUtils.loadAnimation(context,R.anim.slide_in_left);
                                    break;
            case "slide-in-right":animation = AnimationUtils.loadAnimation(context,R.anim.slide_in_right);
                                    break;
            case "slide-in-top":animation = AnimationUtils.loadAnimation(context,R.anim.slide_in_top);
                                    break;
            case "slide-in-bottom":animation = AnimationUtils.loadAnimation(context,R.anim.slide_in_bottom);
                                    break;
            case "slide-in-left-rotate":animation = AnimationUtils.loadAnimation(context,R.anim.slide_in_left);
                                    break;
            case "slide-in-top-left":animation = AnimationUtils.loadAnimation(context,R.anim.slide_in_top_left);
                                    break;
            case "zoom-in":animation = AnimationUtils.loadAnimation(context,R.anim.zoom_in);
                                    break;
            case "zoom-in-left":animation = AnimationUtils.loadAnimation(context,R.anim.zoom_in_left);
                                    break;
            case "slide-out":
            case "slide-out-right":animation = AnimationUtils.loadAnimation(context,R.anim.slide_out_right);
                                    break;
            case "slide-out-left":animation = AnimationUtils.loadAnimation(context,R.anim.slide_out_left);
                                    break;
            case "slide-out-top":animation = AnimationUtils.loadAnimation(context,R.anim.slide_out_top);
                                    break;
            case "slide-out-bottom":animation = AnimationUtils.loadAnimation(context,R.anim.slide_out_bottom);
                                    break;
            case "slide-out-top-right":animation = AnimationUtils.loadAnimation(context,R.anim.slide_out_top_right);
                                    break;
            case "zoom-out":animation = AnimationUtils.loadAnimation(context,R.anim.zoom_out);
                                    break;
            case "zoom-out-right":animation = AnimationUtils.loadAnimation(context,R.anim.zoom_out_right);
                                    break;
        }
        //if there will be no animation relatd to type animation willbe null
        if (animation!=null) {

            //set Duration and Delay of animation
            if (duration != null)
                animation.setDuration(duration * 1000);
            if (delay != null)
                animation.setStartOffset(delay * 1000);
        }

        //Return Animation object
        return animation;
    }
}
