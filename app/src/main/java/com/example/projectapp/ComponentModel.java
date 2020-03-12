package com.example.projectapp;

import android.content.Context;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.webkit.MimeTypeMap;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.caverock.androidsvg.SVGImageView;
import com.example.projectapp.extras.ResourceMissing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class ComponentModel {
    private SlideModel slideModel;  //parent SlideModel instance
    private Integer id = null;      //componentid
    private String type = null; //component type
    private Integer left = 0;   //X co-ordinate
    private Integer top = 0;    //Y co-ordinate

    private Double width = null;
    private Double height = null;
    private String uri = null;      //contains file name
    private String shadow = null;
    private Integer scaleX = null;
    private Integer scaleY = null;
    private Integer z_index = null;
    private Integer angle = null;   //rotation angle
    private Double opacity = null;  //transparency
    private String onClick = null;  //onclick event
    private Boolean is_animate = false; //does component have animation
    private AnimationModel enter_animation;
    private AnimationModel exit_animation;

    private Boolean is_videoview = false;
    private View view = null;
    private Context context;
    private Animation exitanimation = null;
    private Animation enteranimation = null;
    PlaylistModel playlistModel = null;
    SlideModel slide = null;
    FrameLayout playlistlayout;

    Handler myHandler;

    //this runnable is used to set axitanimation of currentslide running on inner playlist
    Runnable exitanimrunnable = new Runnable() {
        @Override
        public void run() {
            slide.setexitanimations();
        }
    };

    //this runnable is used to stop current slide and run next slide into inner playlist
    Runnable nextsliderunnable = new Runnable() {
        @Override
        public void run() {
            //stop current slide
            slide.stop();
            //get next slide
            slide = slide.getNextSlide();
            if (slide != null) {
                //remove all current child views
                playlistlayout.removeAllViews();
                //0add new slide as child
                playlistlayout.addView(slide.getView());

                //start background audio of slide
                slide.start_audio();
                //ifslide have exit animation sethandler that will start exit animation after specified time
                if (slide.getExit_animation() != null)
                    myHandler.postDelayed(exitanimrunnable, (slide.getDuration() - slide.getExit_animation().getDuration()) * 1000);

                //set Handlerthat will stop current slide after current slide's duration time will over
                myHandler.postDelayed(nextsliderunnable, slide.getDuration() * 1000);
            }
        }
    };

    //getter and setter methods

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getLeft() {
        return left;
    }

    public void setLeft(Integer left) {
        this.left = left;
    }


    public Integer getTop() {
        return top;
    }

    public void setTop(Integer top) {
        this.top = top;
    }


    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getShadow() {
        return shadow;
    }

    public void setShadow(String shadow) {
        this.shadow = shadow;
    }

    public Integer getScaleX() {
        return scaleX;
    }

    public void setScaleX(Integer scaleX) {
        this.scaleX = scaleX;
    }

    public Integer getScaleY() {
        return scaleY;
    }

    public void setScaleY(Integer scaleY) {
        this.scaleY = scaleY;
    }

    public Integer getZ_index() {
        return z_index;
    }

    public void setZ_index(Integer z_index) {
        this.z_index = z_index;
    }

    public Integer getAngle() {
        return angle;
    }

    public void setAngle(Integer angle) {
        this.angle = angle;
    }

    public Double getOpacity() {
        return opacity;
    }

    public void setOpacity(Double opacity) {
        this.opacity = opacity;
    }

    public String getOnClick() {
        return onClick;
    }

    public void setOnClick(String onClick) {
        this.onClick = onClick;
    }

    public Boolean getIs_animate() {
        return is_animate;
    }

    public void setIs_animate(Boolean is_animate) {
        if (is_animate != null)
            this.is_animate = is_animate;

    }

    public AnimationModel getEnter_animation() {
        return enter_animation;
    }

    public void setEnter_animation(AnimationModel enter_animation) {
        this.enter_animation = enter_animation;
        //System.out.println("Enter animation set");
    }

    public AnimationModel getExit_animation() {
        return exit_animation;
    }

    public void setExit_animation(AnimationModel exit_animation) {
        this.exit_animation = exit_animation;
        //System.out.println("Exit animation set");
    }

    //constructor to initialize basic instance variables
    public ComponentModel(Integer id, String type, Integer left, Integer top, Double width, Double height, String uri, String shadow, Integer scaleX, Integer scaleY, Integer z_index, Integer angle, Double opacity, String onClick, Boolean is_animate, AnimationModel enter_animation, AnimationModel exit_animation) {
        this.id = id;
        this.type = type;
        this.left = left;
        this.top = top;
        this.width = width;
        this.height = height;
        this.uri = uri;
        this.shadow = shadow;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.z_index = z_index;
        this.angle = angle;
        this.opacity = opacity;
        this.onClick = onClick;
        if (is_animate != null)
            this.is_animate = is_animate;
        this.enter_animation = enter_animation;
        this.exit_animation = exit_animation;
    }

    public void printall() {
        System.out.println("ComponentModel");
        System.out.println(type);
        System.out.println(id);
        System.out.println(left);
        System.out.println(top);
        System.out.println(height);
        System.out.println(width);
        System.out.println(scaleX);
        System.out.println(scaleY);
        System.out.println(z_index);
        System.out.println(angle);
        System.out.println(onClick);
        System.out.println(opacity);
        System.out.println(uri);
        System.out.println(shadow);
        System.out.println("animate:" + is_animate);
        if (exit_animation != null)
            enter_animation.printall();
        if (exit_animation != null)
            exit_animation.printall();
    }


    //init method will create view mentioned in type
    public void init(Context context, SlideModel slideModel) {

        this.slideModel = slideModel;
        this.context = context;

        //check type and create appropriate view
        if (type.equals("Image")) {
            //if type is Image createimageview
            view = createImageview();

        } else if (type.equals("video")) {

            view = createVideoview();
        } else if (type.equals("playlist")) {
            //if type is playlist create inner playlist
            createPlaylist();
        } else {
            view = createWebview();
        }

        //initialize Layout parameter
        FrameLayout.LayoutParams comonentlayoutparam = new FrameLayout.LayoutParams(width.intValue(), height.intValue());
        if (is_animate) {
            //initialize enter and exit animation (Android.view.Animation objects)
            if (enter_animation != null) {
                enteranimation = enter_animation.getAnimation(context);

            }
            if (exit_animation != null) {
                exitanimation = exit_animation.getAnimation(context);
            }
        }

        if (view != null) {
            //setlayout parameterto view
            view.setLayoutParams(comonentlayoutparam);

            //set opacity
            if (opacity != null) {
                view.setAlpha(opacity.floatValue());
            }
            //set Z index
            if (z_index != null) {
                view.setZ(z_index);
            }

            //set X-Yco-ordinates
            view.setX(left);
            view.setY(top);
        }
        else if (playlistlayout != null) {
            //setlayout parameter to playlist layout
            playlistlayout.setLayoutParams(comonentlayoutparam);

            //setX-Y co-ordinates
            playlistlayout.setX(left);
            playlistlayout.setY(top);
        }



    }

    //create playlist method creates inner playlist (playlist as component)
    private void createPlaylist() {
        //create instance of DatabaseHelper
        DatabaseHelper helper = new DatabaseHelper(context);

        //create container (FrameLayout)
        playlistlayout = new FrameLayout(context);
        //get playlist data from database
        playlistModel = helper.getplaylist(id);
        if (playlistModel != null) {
            //set component's width and height as parent_height and parent_width of playlist
            playlistModel.parent_width = width.floatValue();
            playlistModel.parent_height = height.floatValue();

            //init inner playlist
            playlistModel.init(context);
        }

    }


    //this method creates SVGImageview andsets imageview parameters (SVGImageview supports all type of images)
    private View createImageview() {
        SVGImageView imageview = null;

        //get FIle from external storage by uri
        File imagefile = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), uri);
        if (imagefile.exists()) {
            //if image exist initialize SVGImageview
            imageview = new SVGImageView(context);

            //if image type is svg set image uri
            if (MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(imagefile).toString()).equals("svg")) {
                imageview.setImageURI(Uri.fromFile(imagefile));
            } else {
                //if image is other than uri (.gif ,.jpg,.png) load image into SVGImageview with help of Glide
                //Glide is mainly needed for gif images

                Glide.with(context).load(imagefile).into(imageview);
            }

            //scale image horizontally
            if (scaleX != null)
                imageview.setX(scaleX);

            //scale image vertically
            if (scaleY != null)
                imageview.setY(scaleY);

            //rotate image
            if (angle != null)
                imageview.setRotation(angle);

           // System.out.println("imageview initialised");
        } else {
            /*try {
                imagefile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }*/

            //if imageis not available in storage call downloadResource() of ResourceMissing class and pass id of component
            ResourceMissing.downloadResource(id);
        }

        return imageview;
    }

    //this method create videoview and load video into videoview
    private View createVideoview() {
        VideoView videoView = null;

        //get file from storage
        File videofile = new File(context.getExternalFilesDir(Environment.DIRECTORY_MOVIES), uri);

        if (videofile.exists()) {
            //if file exists initialize videoview
            videoView = new VideoView(context);

            //set videopath
            videoView.setVideoPath(videofile.getAbsolutePath());
//            videoView.setVideoURI(Uri.parse("android.resdource://" + context.getApplicationContext().getPackageName() + "/" + id));

            //set is_videoview true
            is_videoview = true;

            //find bitrate of video
            FileInputStream inputStream;
            try {
                inputStream = new FileInputStream(videofile.getAbsoluteFile());
                MediaMetadataRetriever retriever=new MediaMetadataRetriever();
                retriever.setDataSource(inputStream.getFD());
                System.out.println("bitrate bits per second: "+retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_BITRATE));
               // System.out.println("bitrate bits per second: "+retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_BITRATE));
            } catch (IOException | RuntimeException e) {
                e.printStackTrace();
            }

        } else {
           /* try {
                videofile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }*/
           //if file is not available at location call downloadResource method of ResourceMissing class ad pass component id as parameter
            ResourceMissing.downloadResource(id);
        }
        return videoView;
    }


    //this method creates webview and load html content into webview
    private View createWebview() {
        WebView webview = null;
        String data = "";

        //get html file from storage
        File htmlfile = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), uri);

        if (htmlfile.exists()) {
            //if file exists ,initialize webview
            webview = new WebView(context);
            try {
                //readwhole html file
                FileReader reader = new FileReader(htmlfile);
                BufferedReader bufferedReader = new BufferedReader(reader);
                String temp;
                while ((temp = bufferedReader.readLine()) != null) {
                    data += temp;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            //load content of html file into webview
            webview.loadDataWithBaseURL(null, data, "text/html", "UTF-8", null);
            //System.out.println("Webview initialized:" + webview);
        } else {
            /*try {
                htmlfile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }*/

            //if file is  not available call downloadResource Method of class ResourceMissing and pass resource id
            ResourceMissing.downloadResource(id);
        }
        return webview;
    }

    public View getView() {

       // System.out.println("component returned to slide:");

        if (type.equals("playlist") && playlistModel != null) {
            // System.out.println("Returning playlist model");

            //if component is playlist andnot null
            //get first slide of playlist and add that toplaylistlayout
            slide = playlistModel.getSlide();
            playlistlayout.removeAllViews();
            if (slide!=null) {
                playlistlayout.addView(slide.getView());

                //start background audio of slide
                slide.start_audio();
                //start enteranimation
                if (enteranimation != null)
                    playlistlayout.startAnimation(enteranimation);

                //create handler
                myHandler = new Handler();

                //call postdelayed method with exitrunnable to set exitanimation
                if (slide.getExit_animation() != null)
                    myHandler.postDelayed(exitanimrunnable, (slide.getDuration() - slide.getExit_animation().getDuration()) * 1000);

                //call postdelayed method with nextsliderunnable to call nextslide after duration time completes
                myHandler.postDelayed(nextsliderunnable, slide.getDuration() * 1000);
            }
            return playlistlayout;

        } else if (view != null) {
            //if component is not playlist and view is notnull
            //start enter animation
            if (enteranimation != null)
                view.startAnimation(enteranimation);

            //if view is videoview start video
            if (is_videoview)
                ((VideoView) view).start();
        } else {

            ResourceMissing.downloadResource(id);
        }
        return view;
    }

    //this method setexit animation to component
    public void startExitAnimation() {

        if (exitanimation != null) {
            if (type.equals("playlist")) {
                playlistlayout.startAnimation(exitanimation);
            } else {
                view.startAnimation(exitanimation);
            }
        }
    }

    //thismethod will stop innerplaylist's all process
    public void endinnerplaylist() {

        if (type.equals("playlist") && slide != null) {
            //stop current runningslide
            if (slide!=null)
                slide.stop();

            //remove all callbacks of handler
            myHandler.removeCallbacks(exitanimrunnable);
            myHandler.removeCallbacks(nextsliderunnable);
        }

    }
}
