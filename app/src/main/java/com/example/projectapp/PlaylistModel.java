package com.example.projectapp;

import android.content.Context;
import java.util.List;


//PLaylistModelis used to store info of playlist and list of slides
public class PlaylistModel {
    private Integer id;         //playlist id
    private String name;        //playlist name
    public Float parent_width,parent_height;    //parent_view's height and width
    private Integer height, width;      //width and height of playlist
    private List<SlideModel> slides;    //List of slide of single playlist
    Context context;


    //Constructor to initialize private instance variables
    public PlaylistModel(Integer id, String name, Integer height, Integer width, List<SlideModel> slides) {

        this.id = id;
        this.name = name;
        this.height = height;
        this.width = width;
        this.slides = slides;

    }

    //Getter and Setter methods
    public List<SlideModel> getSlides() {
        return slides;
    }

    public void setSlides(List<SlideModel> slides) {
        this.slides = slides;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public void printall() {
        System.out.println("playlist");
        System.out.println(id);
        System.out.println(name);
        System.out.println(height);
        System.out.println(width);
        for (SlideModel s : slides) {
            s.printall();
        }
    }

    //init method will initialize all slide and components of all slide
    public void init(Context context) {
        this.context = context;
      //  System.out.println("into playlist init");

        //initialize all slide and create view
        //call init method of SlideModel for every slide
        for (SlideModel slide : slides) {
            slide.init(context, this);
        }
    }

    //return slide of specific index from list of SlideModel
    SlideModel getNextSlide(int id) {   //here id means index

        //if slide exist in List return that slideModel object
        if (slides.size()>=id)
        {
            return slides.get(id-1);
        }
        return null;
    }

    //return first SlideModel object from List
    SlideModel getSlide() {
        return slides.get(0);

    }

}
