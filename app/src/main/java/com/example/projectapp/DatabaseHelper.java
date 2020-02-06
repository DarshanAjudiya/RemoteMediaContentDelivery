package com.example.projectapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DBNAME = "mediacontent";
    public static final String playlist = "playlist";
    public static final String slides = "slide";
    public static final String component = "component";
    public static final String animations = "animations";


    public DatabaseHelper(@Nullable Context context) {
        super(context, DBNAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //playlist table
        db.execSQL("create table " + playlist + "(pid integer primary key ,name text,height  real, width real)");
        //slides table
        db.execSQL("create table " + slides + "(uid integer primary key autoincrement,sid integer,pid integer,name text,bgcolor text,bgimage integer,duration integer,next integer,animate numeric,animationid integer,audio integer)");
        //component table
        db.execSQL("create table " + component + "(cid integer,sid integer,type text,left_pos integer, top_pos integer, width real,height real,uri text,shadow text,scalex integer,scaley integer,z_index integer,opacity real,angle integer,onclick text,animate integer,enteranim integer,exitanim integer)");
        //animation table
        db.execSQL("create table " + animations + "(animid integer primary key autoincrement,type text,duration integer,delay integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table IF  exists " + playlist);
        db.execSQL("drop table if exists " + slides);
        db.execSQL("drop table if exists " + component);
        db.execSQL("drop table if exists " + animations);
        this.onCreate(db);
    }

    public int insert_playlist(PlaylistModel list) {

        ContentValues values = new ContentValues();
        values.put("pid", list.getId());
        values.put("name", list.getName());
        values.put("height", list.getHeight());
        values.put("width", list.getWidth());
        for (SlideModel s : list.getSlides())
            insert_slide(s, list.getId());
        SQLiteDatabase db = this.getWritableDatabase();
        int x = (int) db.insert(playlist, null, values);
        db.close();
        return x;
    }

    public int insert_slide(SlideModel slide, int pid) {

        ContentValues values = new ContentValues();
        values.put("pid", pid);
        values.put("sid", slide.getId());
        values.put("name", slide.getName());
        values.put("bgcolor", slide.getBgcolor());
        values.put("bgimage", slide.getBgimage());
        //values.put("bgImageUri",slide.getBgImageUri());
        values.put("duration", slide.getDuration());
        values.put("next", slide.getNext());
        values.put("animate", slide.getAnimate());
        values.put("audio", slide.getAudio());
        if (slide.getAnimate() != null && slide.getAnimate()) {
            ContentValues val = new ContentValues();
            val.put("duration", slide.getAnimDuration());
            val.put("type", slide.getAnimation());
            SQLiteDatabase db = this.getWritableDatabase();
            long row = db.insert(animations, null, val);

            Cursor c = db.rawQuery("select animid from " + animations + " where rowid=" + row + "", null);
            if (c.moveToFirst()) {
                int aid = c.getInt(c.getColumnIndex("animid"));
                values.put("animationid", aid);
            }
            db.close();
        }
        SQLiteDatabase db = this.getWritableDatabase();
        long x = db.insert(slides, null, values);
        Cursor c = db.rawQuery("select uid from " + slides + " where rowid=" + x, null);
        if (c.moveToFirst()) {
            int uid = c.getInt(c.getColumnIndex("uid"));
            for (ComponentModel comp : slide.getComponents())
                insert_component(comp, uid);
        }
        db.close();
        return 0;
    }

    public int insert_component(ComponentModel component, int uid) {

        ContentValues values = new ContentValues();
        values.put("cid", component.getId());
        values.put("sid", uid);
        values.put("type", component.getType());
        values.put("left_pos", component.getLeft());
        values.put("top_pos", component.getTop());
        values.put("width", component.getWidth());
        values.put("height", component.getHeight());
        values.put("uri", component.getUri());
        values.put("shadow", component.getShadow());
        values.put("scalex", component.getScaleX());
        values.put("scaley", component.getScaleY());
        values.put("z_index", component.getZ_index());
        if (component.getOpacity() == null)
            values.put("opacity", 1);
        else
            values.put("opacity", component.getOpacity());

        values.put("angle", component.getAngle());
        values.put("onclick", component.getOnClick());
        values.put("animate", component.getIs_animate());
        if (component.getIs_animate() != null && component.getIs_animate()) {
            int enter = insert_anim(component.getEnter_animation());
            int exit = insert_anim(component.getExit_animation());
            if (enter != -1)
                values.put("enteranim", enter);
            if (exit != -1)
                values.put("exitanim", exit);
        }
        SQLiteDatabase db = getWritableDatabase();
        long row = db.insert(this.component, null, values);
        db.close();
        return (int) row;
    }

    public int insert_anim(AnimationModel animate) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("type", animate.getType());
        values.put("delay", animate.getDelay());
        values.put("duration", animate.getDuration());
        long row = db.insert(animations, null, values);
        Cursor c = db.rawQuery("select animid from " + animations + " where rowid=" + row + "", null);
        int x = -1;

        if (c.moveToFirst())
            x = c.getInt(c.getColumnIndex("animid"));
        db.close();
        return x;
    }

    public PlaylistModel getplaylist(@Nullable Integer id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c;
        if (id != null)
            c = db.query(playlist, null, "pid=?", new String[]{"" + id}, null, null, null);
        else
            c = db.query(playlist, null, null, null, null, null, "pid");

        PlaylistModel list = null;
        if (c.moveToFirst()) {
            System.out.println();
            list = new PlaylistModel(c.getInt(0), c.getString(1), c.getInt(2), c.getInt(3), getSlide(c.getInt(0)));
            return list;
        }
        return list;
    }

    public List<SlideModel> getSlide(Integer pid) {
        SQLiteDatabase db = getReadableDatabase();
        List<SlideModel> allslides = new ArrayList<SlideModel>();
        Cursor c;

        c = db.query(slides, null, "pid=?", new String[]{"" + pid}, null, null, "sid");
        SlideModel slide = null;
        if (c.moveToFirst()) {
            do {


                Integer sid = c.getInt(c.getColumnIndex("sid"));
                Boolean anim = (c.getInt(c.getColumnIndex("animate")) == 1);
                String name = c.getString(c.getColumnIndex("name"));
                String bgcolor = c.getString(c.getColumnIndex("bgcolor"));
                Integer bgimage = c.getInt(c.getColumnIndex("bgimage"));
                Integer duration = c.getInt(c.getColumnIndex("duration"));
                Integer next = c.getInt(c.getColumnIndex("next"));
                Integer audio = c.getInt(c.getColumnIndex("audio"));
                slide = new SlideModel(sid, bgimage, duration, next, null, audio, anim, name, bgcolor, null);

                if (anim) {
                    Integer aid = c.getInt(c.getColumnIndex("animationid"));
                    Cursor c2 = db.query(animations, null, "animid=?", new String[]{"" + aid}, null, null, null);
                    if (c2.moveToFirst()) {
                        slide.setAnimDuration(c2.getInt(c2.getColumnIndex("duration")));
                        slide.setAnimation(c2.getString(c2.getColumnIndex("type")));
                    }
                }

                List<ComponentModel> components = new ArrayList<ComponentModel>();
                Cursor comp = db.query(component, null, "sid=?", new String[]{"" + sid}, null, null, "cid");
                if (comp.moveToFirst()) {
                    do {
                        Integer id = comp.getInt(comp.getColumnIndex("cid"));
                        String type = comp.getString(comp.getColumnIndex("type"));
                        Integer left = comp.getInt(comp.getColumnIndex("left_pos"));
                        Integer top = comp.getInt(comp.getColumnIndex("top_pos"));
                        Double width = comp.getDouble(comp.getColumnIndex("width"));
                        Double height = comp.getDouble(comp.getColumnIndex("height"));
                        String uri = comp.getString(comp.getColumnIndex("uri"));
                        String shadow = comp.getString(comp.getColumnIndex("shadow"));
                        Integer scalex = comp.getInt(comp.getColumnIndex("scalex"));
                        Integer scaley = comp.getInt(comp.getColumnIndex("scaley"));
                        Integer zindex = comp.getInt(comp.getColumnIndex("z_index"));
                        Integer angle = comp.getInt(comp.getColumnIndex("angle"));
                        Double opacity = comp.getDouble(comp.getColumnIndex("opacity"));

                        System.out.println("<--------------------------------------------\n\n\n " + opacity + "---------------------" + comp.getDouble(comp.getColumnIndex("opacity")) + "\n--------------------->\n\n\n\n");
                        String onclick = comp.getString(comp.getColumnIndex("onclick"));
                        Boolean animate = (comp.getInt(comp.getColumnIndex("animate")) == 1);
                        AnimationModel enter = null, exit = null;

                        if (animate) {
                            Cursor c2 = db.query(animations, null, "animid=?", new String[]{"" + comp.getInt(comp.getColumnIndex("enteranim"))}, null, null, null);
                            if (c2.moveToFirst()) {
                                enter = new AnimationModel(c2.getString(c2.getColumnIndex("type")), c2.getInt(c2.getColumnIndex("delay")), c2.getInt(c2.getColumnIndex("duration")));
                            }

                            c2 = db.query(animations, null, "animid=?", new String[]{"" + comp.getInt(comp.getColumnIndex("exitanim"))}, null, null, null);
                            if (c2.moveToFirst()) {
                                exit = new AnimationModel(c2.getString(c2.getColumnIndex("type")), c2.getInt(c2.getColumnIndex("delay")), c2.getInt(c2.getColumnIndex("duration")));
                            }

                        }

                        components.add(new ComponentModel(id, type, left, top, width, height, uri, shadow, scalex, scaley, zindex, angle, opacity, onclick, animate, enter, exit));
                    } while (comp.moveToNext());
                    slide.setComponents(components);

                }
                allslides.add(slide);
            } while (c.moveToNext());
            return allslides;
        }
        return null;
    }

}
