package com.example.projectapp;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.animation.Animation;
import android.widget.FrameLayout;

import java.util.List;

public class SlideModel {

    private Integer id, bgimage, duration, next, animduration, audio;
    private Boolean animate = false;
    private String name, bgcolor, animation;
    private List<ComponentModel> components;
    PlaylistModel playlist;
    FrameLayout layout;
    Animation slideanim;
    public List<ComponentModel> getComponents() {
        return components;
    }

    public void setComponents(List<ComponentModel> components) {
        this.components = components;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SlideModel(Integer id, Integer bgimage, Integer duration, Integer next, Integer animduration, Integer audio, Boolean animate, String name, String bgcolor, String animation) {

        this.id = id;
        this.bgimage = bgimage;
        this.duration = duration;
        this.next = next;
        this.animduration = animduration;
        this.audio = audio;
        if (animate != null)
            this.animate = animate;
        this.name = name;
        this.bgcolor = bgcolor;
        this.animation = animation;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBgimage() {
        return bgimage;
    }

    public void setBgimage(Integer bgimage) {
        this.bgimage = bgimage;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getNext() {
        return next;
    }

    public void setNext(Integer next) {
        this.next = next;
    }

    public Integer getAniduration() {
        return animduration;
    }

    public void setAniduration(Integer animduration) {
        this.animduration = animduration;
    }

    public Integer getAudio() {
        return audio;
    }

    public void setAudio(Integer audio) {
        this.audio = audio;
    }

    public Boolean getAnimate() {
        return animate;
    }

    public void setAnimate(Boolean animate) {
        if (animate != null)
            this.animate = animate;
    }

    public String getBgcolor() {
        return bgcolor;
    }

    public void setBgcolor(String bgcolor) {
        this.bgcolor = bgcolor;
    }

    public String getAnimation() {
        return animation;
    }

    public void setAnimation(String animation) {
        this.animation = animation;
    }

    /*  public String getBgImageUri() {
          return bgImageUri;
      }

      public void setBgImageUri(String bgImageUri) {
          this.bgImageUri = bgImageUri;
      }
  */
    public void printall() {
        System.out.println("slides");

        System.out.println(id);
        System.out.println(animduration);
        System.out.println(animate);
        System.out.println(animation);
        System.out.println(audio);
        System.out.println(bgcolor);
        System.out.println(bgimage);
        //     System.out.println(bgImageUri);
        System.out.println(duration);
        System.out.println(next);

        for (ComponentModel c : components)
            c.printall();
    }

    public void init(Context context, PlaylistModel playlist) {
        this.playlist = playlist;
        FrameLayout.LayoutParams layoutParams;
        if (playlist.getHeight() == 0)
            layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        else {

            layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        }
        layout = new FrameLayout(context);
        layout.setLayoutParams(layoutParams);
        layout.setBackgroundColor(Color.parseColor(bgcolor));
        //layout.setBackground(ContextCompat.getDrawable(context, ));
        layout.setBackgroundResource(R.drawable.a);

        if (animate)
        {
           AnimationModel animationModel=new AnimationModel(animation,null,animduration);
            slideanim=animationModel.getAnimation(context);
            slideanim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    System.out.println("...............................\n slide animation started\n............................");
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    System.out.println("...............................\n slide animation ended\n............................");
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }
        for (ComponentModel componentModel : components) {
            componentModel.init(context, this);
        }
    }

    public View getView() {
        for (ComponentModel component : components) {
            View child = component.getView();
            if (child != null) {
                System.out.println("child added:" + child);
                layout.addView(child);
            }

        }
        if (animate)
            layout.startAnimation(slideanim);
    return layout;

    }
}
