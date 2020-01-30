package com.example.projectapp;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

public class MyimageView {
    ComponentModel model;
    Context context;

    public MyimageView(Context context, ComponentModel model) {
        this.context = context;
        this.model = model;
    }

    View createview() {
        ImageView imageview = new ImageView(context);
        imageview.setImageResource(model.getId());
        if (model.getScaleX() != null)
            imageview.setX(model.getScaleX());
        if (model.getScaleY() != null)
            imageview.setY(model.getScaleY());

        imageview.setRotation(model.getAngle());

        return imageview;
    }
}
