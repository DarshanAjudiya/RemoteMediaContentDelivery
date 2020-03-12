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


//SlideModel Contains raw information about slide
//slideModel creates container that have various components
//slideModel is implementing Serializable because in ComponentFragment we are passing slidemodel as serializable
public class SlideModel implements Serializable {

    private Integer id, bgimage, duration, next, audio;     //slide id,background image id , total duration , id of next slide , background audio id
    private Boolean animate = false;        //is slide have animations
    private String name, bgcolor;   //slide name , background color of container
    private AnimationModel enter_animation; //AnimationModel object for enter animation of slide
    private AnimationModel exit_animation;  //AnimationModel object for exit animation of slide
    private List<ComponentModel> components;    //List of components to execute on slide

    private Animation exitanimation;
    private Animation enteranimation;

    PlaylistModel playlist;
    FrameLayout layout;
    MediaPlayer player = null;
    Context context;

    //constructor and getter/setter methods
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


    //init method creates container and initialize all components view
    public void init(Context context, PlaylistModel playlist) {
        //set PlaylistModel object
        this.playlist = playlist;
        this.context = context;

        //initialize Layout which will contain components
        layout = new FrameLayout(context);

        //set Layout Parameter
        //get height and width from playlist
        FrameLayout.LayoutParams layoutParams;
        layoutParams = new FrameLayout.LayoutParams(playlist.getWidth(), playlist.getHeight(), Gravity.CENTER);
        layout.setLayoutParams(layoutParams);

        //set background color
        layout.setBackgroundColor(Color.parseColor(bgcolor));

        //set Background image
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

        //initialize Animation object
        if (animate) {
          if (enter_animation!=null) {
              //check if animation duration is less then half of total duration of slide
              if (enter_animation.getDuration() > duration / 2)
                  enter_animation.setDuration(duration / 2);
              enteranimation=enter_animation.getAnimation(context);
          }
          if (exit_animation!=null)
          {
              //check if animation duration is less then half of total duration of slide
              if (exit_animation.getDuration()>duration/2)
                  exit_animation.setDuration(duration/2);
              exitanimation=exit_animation.getAnimation(context);
          }

        }

        //initialize  component and create components view
        for (ComponentModel componentModel : components) {
            componentModel.init(context, this);
        }


        //fit slide layout container with parent layout container
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

        //remove all child views
        layout.removeAllViews();
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
        //get views from all component and add them as child
        for (ComponentModel component : components) {
            View child = component.getView();
            if (child != null) {
            //    System.out.println("child added:" + child);
                layout.addView(child);
            }

        }


        //start enter animation on layout
        if (animate && enter_animation!=null)
            layout.startAnimation(enteranimation);

        //return layout
        return layout;

    }

    //this method returns next slide's slideModel object
    public SlideModel getNextSlide() {
        //if there is next slide's id
        if (next!=null) {
            //if id is not same as current slide id and is greater than 0
            if (next > 0 && next != id) {
                //get slide from playlist and return that slidemodel object
                return playlist.getNextSlide(next);
            }
        }
        //if there is no next id return null
        return null;
    }


    //thismethod is used to start exit animation of slide
    public void setexitanimations() {

        //first start exit animation on components
        for (ComponentModel component : components) {
          component.startExitAnimation();
        }
        //if there is exit animation to set
        //start exit animation
        if (exit_animation!=null)
            layout.startAnimation(exitanimation );
    }


    //this method will start background audio of slide
    public void start_audio() {
      //  System.out.println("slide Id in start audio"+id);

        //get audio file from storage where it's located
        File audiofile = new File(context.getExternalFilesDir(Environment.DIRECTORY_MUSIC), audio + ".mp3");
        if (audiofile.exists()) {   //if audiofile is available
            //initialize mediaplayer
            player = new MediaPlayer();
            try {
                //give absolute path of audiofile as datasource to media player
                player.setDataSource(audiofile.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }

            //load media . prepare mediaplayer
            player.prepareAsync();
            //set looping false because we don't want to repeat audio
            player.setLooping(false);
            //when player is prepared start player
            //start to play audio
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

    //this method will stop working of all components and stop all processes running in current slide
    public void stop() {

        //first if any components contains inner playlist stop it
        for (ComponentModel component : components) {
            component.endinnerplaylist();
        }

        //if player is running stop mediaplayer
        if (player != null && player.isPlaying())
            player.stop();

    }


}
