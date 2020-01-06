package com.example.projectapp;

public class Animate {
    private String type;
    private Integer delay;
    private Integer duration;

    public Animate(String type, Integer delay, Integer duration) {
        this.type = type;
        this.delay = delay;
        this.duration = duration;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getDelay() {
        return delay;
    }

    public void setDelay(Integer delay) {
        this.delay = delay;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }
    public void printall()
    {
        System.out.println("Animation");;
        System.out.println(type);
        System.out.println(duration);
        System.out.println(delay);
    }
}
