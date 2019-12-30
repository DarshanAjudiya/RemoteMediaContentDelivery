package com.example.projectapp;

import java.util.ArrayList;
import java.util.List;

public class Playlist {
    private String type;
    private Integer id;
    private String name;
    private Integer height,width;
    private List<Slide> slides=new ArrayList<Slide>();

    public Playlist(String type, Integer id, String name, Integer height, Integer width, List<Slide> slides) {
        this.type = type;
        this.id = id;
        this.name = name;
        this.height = height;
        this.width = width;
        this.slides = slides;

    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
        System.out.println(type);
        System.out.println(id);
        System.out.println(name);
        System.out.println(height);
        System.out.println(width);
        for(Slide s:slides)
        {
            s.printall();
        }
    }
}
