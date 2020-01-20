package com.example.projectapp;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class AnimationModel {
    private String type;
    private Integer delay;
    private Integer duration;

    public AnimationModel(String type, Integer delay, Integer duration) {
        this.type = type;
        this.delay = delay;
        this.duration = duration;
    }

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
        ;
        System.out.println(type);
        System.out.println(duration);
        System.out.println(delay);
    }

    public Animation getAnimation(Context context) {
        Animation animation=null;
        animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
        switch(type)
        {
            case "slde-in":

                break;

        }

        if (duration != null)
            animation.setDuration(duration);
        if (delay != null)
            animation.setStartOffset(delay * 1000);

        return animation;
    }
}
