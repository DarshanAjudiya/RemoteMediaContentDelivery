package com.example.projectapp;

import java.util.List;

public class PlaylistModel {
    private Integer id;
    private String name;
    private Integer height,width;
    private List<SlideModel> slides;

    public PlaylistModel(Integer id, String name, Integer height, Integer width, List<SlideModel> slides) {

        this.id = id;
        this.name = name;
        this.height = height;
        this.width = width;
        this.slides = slides;

    }

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
    public void printall()
    {
        System.out.println("playlist");
        System.out.println(id);
        System.out.println(name);
        System.out.println(height);
        System.out.println(width);
        for(SlideModel s:slides)
        {
            s.printall();
        }
    }

    void setview()
    {

    }

}
