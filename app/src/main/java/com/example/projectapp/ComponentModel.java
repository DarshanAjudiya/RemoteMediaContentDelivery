package com.example.projectapp;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;

public class ComponentModel {
    private Integer id;
    private String type;
    private Integer left;
    private Integer right;
    private Integer top;
    private Integer bottom;
    private Double width;
    private Double height;
    private String uri;
    private String shadow;
    private Integer scaleX;
    private Integer scaleY;
    private Integer z_index;
    private Integer angle;
    private Double opacity;
    private String onClick;
    private Boolean is_animate = false;
    private AnimationModel enter_animation;
    private AnimationModel exit_animation;

    View view;
    private Context context;

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

    public Integer getRight() {
        return right;
    }

    public void setRight(Integer right) {
        this.right = right;
    }

    public Integer getTop() {
        return top;
    }

    public void setTop(Integer top) {
        this.top = top;
    }

    public Integer getBottom() {
        return bottom;
    }

    public void setBottom(Integer bottom) {
        this.bottom = bottom;
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

    public ComponentModel(Integer id, String type, Integer left, Integer right, Integer top, Integer bottom, Double width, Double height, String uri, String shadow, Integer scaleX, Integer scaleY, Integer z_index, Integer angle, Double opacity, String onClick, Boolean is_animate, AnimationModel enter_animation, AnimationModel exit_animation) {
        this.id = id;
        this.type = type;
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;
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
        System.out.println(right);
        System.out.println(top);
        System.out.println(bottom);
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

    public void init(Context context) {
        this.context = context;
        if (type.equals("Image")) {
            MyimageView myimageView = new MyimageView(context, this);
            view = myimageView.createview();
        } else if (type.equals("VideoView")) {

        } else if (type.equals("playlist")) {

        } else {

        }

        if (is_animate) {
            if (enter_animation != null) {
                Animation enteranimation = enter_animation.getAnimation(context);

            }
            if (exit_animation != null) {
                Animation exitanimation = exit_animation.getAnimation(context);
            }
        }

    }

    View createview() {
        ImageView imageview = new ImageView(context);
        imageview.setImageResource(id);
        if (scaleX != null)
            imageview.setX(scaleX);
        if (scaleY != null)
            imageview.setY(scaleY);
        if (angle != null)
            imageview.setRotation(angle);
        if(opacity!=null)
            imageview.
        return imageview;
    }
}
