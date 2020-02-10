package com.example.projectapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.VideoView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ComponentModel {
    private SlideModel slideModel;
    private Integer id = null;
    private String type = null;
    private Integer left = 0;
    private Integer top = 0;

    private Double width = null;
    private Double height = null;
    private String uri = null;
    private String shadow = null;
    private Integer scaleX = null;
    private Integer scaleY = null;
    private Integer z_index = null;
    private Integer angle = null;
    private Double opacity = null;
    private String onClick = null;
    private Boolean is_animate = false;
    private AnimationModel enter_animation;
    private AnimationModel exit_animation;
    private Boolean is_videoview = false;
    private View view = null;
    private Context context;
    private Animation exitanimation;
    private Animation enteranimation;
    FrameLayout playlistlayout;
    PlaylistModel playlistModel=null;
    SlideModel slide;

    Handler myHandler;
    Runnable myRunnable=new Runnable() {
        @Override
        public void run() {
            slide.stopAudio();
            slide.setexitanimations();
            slide=slide.getNextSlide();
            if (slide!=null) {
                playlistlayout.removeAllViews();
                playlistlayout.addView(slide.getView());
                slide.start_audio();
                myHandler.postDelayed(myRunnable, slide.getDuration());
            }
        }
    };

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
        System.out.println("Enter animation set");
    }

    public AnimationModel getExit_animation() {
        return exit_animation;
    }

    public void setExit_animation(AnimationModel exit_animation) {
        this.exit_animation = exit_animation;
        System.out.println("Exit animation set");
    }

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

    public void init(Context context, SlideModel slideModel) {

        this.slideModel = slideModel;
        this.context = context;
        if (type.equals("Image")) {
            view = createImageview();

        } else if (type.equals("Video")) {
            view = createVideoview();
        } else if (type.equals("playlist")) {

            view = createPlaylist();
        } else {
            view = createWebview();
        }

        if (view != null) {
            if (is_animate) {
                if (enter_animation != null) {
                    enteranimation = enter_animation.getAnimation(context);

                }
                if (exit_animation != null) {
                    exitanimation = exit_animation.getAnimation(context);
                }
            }
            if (opacity != null)
                view.setAlpha(opacity.floatValue());
            if (z_index != null)
                view.setZ(z_index);


            FrameLayout.LayoutParams comonentlayoutparam = new FrameLayout.LayoutParams(width.intValue(), height.intValue());
            view.setLayoutParams(comonentlayoutparam);
            view.setX(top);
            view.setY(left);
        } else {

        }


    }

    private View createPlaylist() {
        DatabaseHelper helper=new DatabaseHelper(context);
        playlistlayout = new FrameLayout(context);
        playlistModel=helper.getplaylist(id);
        if (playlistModel!=null)
        {
            playlistModel.parent_width=width.floatValue();
            playlistModel.parent_height=height.floatValue();
            playlistModel.init(context);
        }
        return null;
    }

    private View createImageview() {
        ImageView imageview = null;
        File imagefile = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "c.jpg");
        if (imagefile.exists()) {
            Bitmap bitmapinstnce = BitmapFactory.decodeFile(imagefile.getAbsolutePath());
            imageview = new ImageView(context);
            //imageview.setImageResource(context.getApplicationContext().getResources().getIdentifier("" + id, "drawable", context.getPackageName()));
            imageview.setImageBitmap(bitmapinstnce);
            if (scaleX != null)
                imageview.setX(scaleX);
            if (scaleY != null)
                imageview.setY(scaleY);
            if (angle != null)
                imageview.setRotation(angle);
            System.out.println("imageview initialised");
        } else {
            try {
                 imagefile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return imageview;
    }
    private View createVideoview() {
        VideoView videoView = null;
        File videofile = new File(context.getExternalFilesDir(Environment.DIRECTORY_MOVIES), uri);
        if (videofile.exists()) {
            videoView = new VideoView(context);
            videoView.setVideoPath(videofile.getAbsolutePath());

//            videoView.setVideoURI(Uri.parse("android.resdource://" + context.getApplicationContext().getPackageName() + "/" + id));
            is_videoview = true;

        } else {
            try {
                videofile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return videoView;
    }


    private View createWebview() {
        WebView webview = new WebView(context);
        String data = "";
        File htmlfile = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), uri);
        if (htmlfile.exists()) {
            try {

                FileReader reader = new FileReader(htmlfile);
                BufferedReader bufferedReader = new BufferedReader(reader);
                String temp;

                while ((temp = bufferedReader.readLine()) != null) {
                    data += temp;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            webview.loadDataWithBaseURL(null, data, "text/html", "UTF-8", null);
            System.out.println("Webview initialized:" + webview);
        } else {
            try {
                htmlfile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return webview;
    }

    public View getView() {

        System.out.println("component returned to slide:");
        if (view != null) {
            if (enteranimation != null)

                view.startAnimation(enteranimation);
            if (is_videoview)
                ((VideoView) view).start();

            if (type=="playlist" && playlistModel!=null)
            {
                slide = playlistModel.getSlide();
                playlistlayout.addView(slide.getView());
                slide.start_audio();
                myHandler=new Handler();
                myHandler.postDelayed(myRunnable,slide.getDuration());


            }
        }
        return view;
    }
}
