package com.example.projectapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Environment;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.widget.FrameLayout;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

public class SlideModel implements Serializable {

    private Integer id, bgimage, duration, next, animDuration, audio;
    private Boolean animate = false;
    private String name, bgcolor, animation;
    private List<ComponentModel> components;
    PlaylistModel playlist;
    FrameLayout layout;
    public Animation slideanim;
    MediaPlayer player = null;
    Context context;


    public SlideModel(Integer id, Integer bgimage, Integer duration, Integer next, Integer animDuration, Integer audio, Boolean animate, String name, String bgcolor, String animation) {

        this.id = id;
        this.bgimage = bgimage;
        this.duration = duration;
        this.next = next;
        this.animDuration = animDuration;
        this.audio = audio;
        if (animate != null)
            this.animate = animate;
        this.name = name;
        this.bgcolor = bgcolor;
        this.animation = animation;
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

    public Integer getAnimDuration() {
        return animDuration;
    }

    public void setAnimDuration(Integer animduration) {
        this.animDuration = animduration;
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
        System.out.println(animDuration);
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
        this.context = context;
        FrameLayout.LayoutParams layoutParams;

        layoutParams = new FrameLayout.LayoutParams(playlist.getWidth(), playlist.getHeight(), Gravity.CENTER);
        layout = new FrameLayout(context);
        layout.setLayoutParams(layoutParams);
        layout.setBackgroundColor(Color.parseColor(bgcolor));


        //layout.setBackground(ContextCompat.getDrawable(context, ));
        //layout.setBackgroundResource(R.drawable.a);

        if (animate) {
            AnimationModel animationModel;
            if (animDuration < duration / 2) {
                animationModel = new AnimationModel(animation, null, animDuration);
            } else
                animationModel = new AnimationModel(animation, null, duration / 20);
            slideanim = animationModel.getAnimation(context);
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
        int w=playlist.getWidth();
        int h=playlist.getHeight();

        float wr=playlist.parent_width/w;
        float hr=playlist.parent_height/h;
        if (wr<1) {
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
        layout.removeAllViews();
        for (ComponentModel component : components) {
            View child = component.getView();
            if (child != null) {
                System.out.println("child added:" + child);
                layout.addView(child);
            }

        }
        File imagefile = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "c.jpg");
        if (imagefile.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagefile.getAbsolutePath());
            //   layout.setBackground(new BitmapDrawable(context.getResources(),bitmap));
        } else {

        }
        if (animate)
            layout.startAnimation(slideanim);


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

    }

    public void start_audio() {
        System.out.println("slide Id in start audio"+id);
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
            try {
                audiofile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void stopAudio() {

        if (player != null && player.isPlaying())
            player.stop();

    }

    public void stopInnerPlaylist() {

    }
}
