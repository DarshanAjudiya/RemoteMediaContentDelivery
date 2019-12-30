package com.example.projectapp;

public class Slide {
    private String type;
    private Integer id,bgimage,duration,next,aniduration,audio;
    private Boolean animate;
    private String name,bgimageuri,bgcolor,animation;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Slide(String type, Integer id, Integer bgimage, Integer duration, Integer next, Integer aniduration, Integer audio, Boolean animate, String name, String bgimageuri, String bgcolor, String animation) {
        this.type = type;
        this.id = id;
        this.bgimage = bgimage;
        this.duration = duration;
        this.next = next;
        this.aniduration = aniduration;
        this.audio = audio;
        this.animate = animate;
        this.name = name;
        this.bgimageuri = bgimageuri;
        this.bgcolor = bgcolor;
        this.animation = animation;
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

    public Integer getAniduration() {
        return aniduration;
    }

    public void setAniduration(Integer aniduration) {
        this.aniduration = aniduration;
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
        this.animate = animate;
    }

    public String getBgimageuri() {
        return bgimageuri;
    }

    public void setBgimageuri(String bgimageuri) {
        this.bgimageuri = bgimageuri;
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
    public void printall()
    {
        System.out.println(type);
        System.out.println(id);
        System.out.println(aniduration);
        System.out.println(animate);
        System.out.println(animation);
        System.out.println(audio);
        System.out.println(bgcolor);
        System.out.println(bgimage);
        System.out.println(bgimageuri);
        System.out.println(duration);
        System.out.println(next);

    }

}