package com.example.projectapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.os.Environment;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.widget.FrameLayout;

import com.example.projectapp.extras.ResourceMissing;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

public class SlideModel implements Serializable {

    private Integer id, bgimage, duration, next, audio;
    private Boolean animate = false;
    private String name, bgcolor;
    private List<ComponentModel> components;
    private AnimationModel enter_animation;
    private AnimationModel exit_animation;
    PlaylistModel playlist;
    FrameLayout layout;
    private Animation exitanimation;
    private Animation enteranimation;
    MediaPlayer player = null;
    Context context;

    public SlideModel(Integer id, Integer bgimage, Integer duration, Integer next, Integer audio, Boolean animate, String name, String bgcolor,AnimationModel enter_animation,AnimationModel exit_animation) {

        this.id = id;
        this.bgimage = bgimage;
        this.duration = duration;
        this.next = next;
        this.audio = audio;
        if (animate != null)
            this.animate = animate;
        this.name = name;
        this.bgcolor = bgcolor;
        this.enter_animation=enter_animation;
        this.exit_animation=exit_animation;
    }

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


    public AnimationModel getEnter_animation() {
        return enter_animation;
    }

    public void setEnter_animation(AnimationModel enter_animation) {
        this.enter_animation = enter_animation;
    }

    public AnimationModel getExit_animation() {
        return exit_animation;
    }

    public void setExit_animation(AnimationModel exit_animation) {
        this.exit_animation = exit_animation;
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
        System.out.println(animate);
        System.out.println(audio);
        System.out.println(bgcolor);
        System.out.println(bgimage);
        //     System.out.println(bgImageUri);
        System.out.println(duration);
        System.out.println(next);
        if(animate)
        {
            if (enter_animation!=null)
                enter_animation.printall();
            if (exit_animation!=null)
                exit_animation.printall();
        }
        for (ComponentModel c : components)
            c.printall();
    }

    public void init(Context context, PlaylistModel playlist) {
        this.playlist = playlist;
        this.context = context;
        FrameLayout.LayoutParams layoutParams;

        layoutParams = new FrameLayout.LayoutParams(playlist.getWidth(), playlist.getHeight(), Gravity.CENTER);
        layout = new FrameLayout(context);
        layout.setLayoutParams(layoutParams);
        layout.setBackgroundColor(Color.parseColor(bgcolor));

        /*File bg_image = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), bgimage+".jpg");
        if (bg_image.exists())
        {
            Bitmap bitmap=BitmapFactory.decodeFile(bg_image.getAbsolutePath());
            BitmapDrawable bg_drawable=new BitmapDrawable(context,bitmap);
            layout.setBackground(bg_drawable);
        }
        else
        {
           *//* try {
                bg_image.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }*//*
            ResourceMissing.downloadResource(bgimage);
        }*/

        //layout.setBackground(ContextCompat.getDrawable(context, ));
        layout.setBackgroundResource(R.drawable.a);

        if (animate) {
          if (enter_animation!=null) {
              if (enter_animation.getDuration() > duration / 2)
                  enter_animation.setDuration(duration / 2);
              enteranimation=enter_animation.getAnimation(context);
          }
          if (exit_animation!=null)
          {
              if (exit_animation.getDuration()>duration/2)
                  exit_animation.setDuration(duration/2);
              exitanimation=exit_animation.getAnimation(context);
          }

        }
        for (ComponentModel componentModel : components) {
            componentModel.init(context, this);
        }
        int w=playlist.getWidth();
        int h=playlist.getHeight();

        float wr=playlist.parent_width/w;
        float hr=playlist.parent_height/h;
        if (wr<hr) {
            layout.setScaleX(wr);
            layout.setScaleY(wr);
        }

        else
        {
            layout.setScaleX(hr);
            layout.setScaleY(hr);
        }
    }

    public View getView() {
        /*File bg_image = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), bgimage+".jpg");

        if (bg_image.exists())
        {
            Bitmap bitmap=BitmapFactory.decodeFile(bg_image.getAbsolutePath());
            BitmapDrawable bg_drawable=new BitmapDrawable(context.getResources(),bitmap);
            layout.setBackground(bg_drawable);
        }
        else
        {
            ResourceMissing.downloadResource(bgimage);
        }*/
        layout.removeAllViews();
        for (ComponentModel component : components) {
            View child = component.getView();
            if (child != null) {
            //    System.out.println("child added:" + child);
                layout.addView(child);
            }

        }

        if (animate && enter_animation!=null)
            layout.startAnimation(enteranimation);
        return layout;

    }

    public SlideModel getNextSlide() {
        if (next!=null) {
            if (next > 0 && next != id) {
                return playlist.getNextSlide(next);
            }
        }

        return null;
    }

    public void setexitanimations() {

        for (ComponentModel component : components) {
          component.startExitAnimation();
        }
        if (exit_animation!=null)
            layout.startAnimation(exitanimation );
    }

    public void start_audio() {
      //  System.out.println("slide Id in start audio"+id);
        File audiofile = new File(context.getExternalFilesDir(Environment.DIRECTORY_MUSIC), audio + ".mp3");
        if (audiofile.exists()) {
            player = new MediaPlayer();
            try {
                player.setDataSource(audiofile.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
            player.prepareAsync();
            player.setLooping(false);
            player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                }
            });

        } else {
            ResourceMissing.downloadResource(audio);
        }
    }

    public void stop() {

        for (ComponentModel component : components) {
            component.endinnerplaylist();
        }
        if (player != null && player.isPlaying())
            player.stop();

    }


}
