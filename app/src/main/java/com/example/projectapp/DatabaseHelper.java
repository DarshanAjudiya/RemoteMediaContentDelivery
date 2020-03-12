package com.example.projectapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;


//sqliteopenhelper class to interact with and handle SQLite database
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
        db.execSQL("create table " + slides + "(uid integer primary key autoincrement,sid integer,pid integer,name text,bgcolor text,bgimage integer,duration integer,next integer,animate numeric,enteranim integer,exitanim integer,audio integer)");
        //component table
        db.execSQL("create table " + component + "(cid integer,sid integer,type text,left_pos integer, top_pos integer, width real,height real,uri text,shadow text,scalex integer,scaley integer,z_index integer,opacity real,angle integer,onclick text,animate integer,enteranim integer,exitanim integer)");
        //animation table
        db.execSQL("create table " + animations + "(animid integer primary key autoincrement,type text,duration integer,delay integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //Drop existing tables and create new table
        db.execSQL("drop table IF  exists " + playlist);
        db.execSQL("drop table if exists " + slides);
        db.execSQL("drop table if exists " + component);
        db.execSQL("drop table if exists " + animations);
        this.onCreate(db);
    }

    //insert_playlist method insert info of playlist into playlist table and call insert_slide method to insert slide available in playlist
    public int insert_playlist(PlaylistModel list) {
        //read from PlaylistModel and put values in ContentValues to upload in database
        ContentValues values = new ContentValues();
        values.put("pid", list.getId());
        values.put("name", list.getName());
        values.put("height", list.getHeight());
        values.put("width", list.getWidth());
        //Insert all slide placed in PlaylistModel class into slide table one by one
        for (SlideModel s : list.getSlides())
            insert_slide(s, list.getId());

        //get Writable instance of SQLitedatabase
        SQLiteDatabase db = this.getWritableDatabase();
        //call insert method of SQLiteDatabase to to insert data into playlist table
        int rowcount = (int) db.insert(playlist, null, values);

        //close the database
        db.close();

        //return number of row inserted in playlist table
        return rowcount;
    }


    //insert_slide inserts info of slide and calls insert_component method for components it contains
    public int insert_slide(SlideModel slide, int pid) {

        //get data from SlideModel and put in ContentValues's instance
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

        //If slide have animation put that animation info into animation table
        if (slide.getAnimate() != null && slide.getAnimate()) {

            //Insert enter and exit animation of slide into animation table
            int enter = insert_anim(slide.getEnter_animation());
            int exit = insert_anim(slide.getExit_animation());
            if (enter != -1) {
                values.put("enteranim", enter);
            }
            if (exit != -1) {
                values.put("exitanim", exit);
            }
        }
        //get SQLitedatabase instance in writable mode
        SQLiteDatabase db = this.getWritableDatabase();

        //call insert method of SQLiteDatabase to to insert data into slide table
        int rowcount = (int) db.insert(slides, null, values);
        Cursor dataCursor = db.rawQuery("select uid from " + slides + " where rowid=" + rowcount, null);
        if (dataCursor.moveToFirst()) {
            int uid = dataCursor.getInt(dataCursor.getColumnIndex("uid"));

            //call insert_component method for every components lies in slide
            for (ComponentModel comp : slide.getComponents())
                insert_component(comp, uid);
        }
        db.close();

        //return  number of rows inserted in slide table
        return rowcount;
    }

    //insert_component inserts data of componentModel into component table
    public int insert_component(ComponentModel component, int uid) {

        //get Values from componentModel object and put into contentValues object values
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
        if (component.getOpacity() == null) {
            values.put("opacity", 1);
        }
        else {
            values.put("opacity", component.getOpacity());
        }

        values.put("angle", component.getAngle());
        values.put("onclick", component.getOnClick());
        values.put("animate", component.getIs_animate());

        //insert animation info of component onto animation table
        if (component.getIs_animate() != null && component.getIs_animate()) {
            //call insert_anim method to insert enter and exit animation data into database
            int enter = insert_anim(component.getEnter_animation());
            int exit = insert_anim(component.getExit_animation());
            if (enter != -1) {
                values.put("enteranim", enter);
            }
            if (exit != -1) {
                values.put("exitanim", exit);
            }
        }
        SQLiteDatabase db = getWritableDatabase();
        //call insert method of SQLiteDatabase to to insert data into component table
        long row = db.insert(this.component, null, values);
        db.close();

        //return row id
        return (int) row;
    }


    //insert_anim method is used to insert animation info to animation table
    public int insert_anim(AnimationModel animate) {
        SQLiteDatabase db = getWritableDatabase();

        //get data from AnimationModel an put into ContentValues instance
        ContentValues values = new ContentValues();
        values.put("type", animate.getType());
        values.put("delay", animate.getDelay());
        values.put("duration", animate.getDuration());

        //call insert method of SQLiteDatabase to to insert data into animation table
        long row = db.insert(animations, null, values);

        //execute select query to get animid  of currently inserted row
        Cursor dataCursor = db.rawQuery("select animid from " + animations + " where rowid=" + row + "", null);
        int animid = -1;

        if (dataCursor.moveToFirst())
            animid = dataCursor.getInt(dataCursor.getColumnIndex("animid"));
        db.close();

        //return animid
        return animid;
    }


    //getPlaylist method retrieve data from database and return playlistModel Object
    public PlaylistModel getplaylist(@Nullable Integer id) {

        //Create SQLiteDatabase object in readable mode
        SQLiteDatabase db = getReadableDatabase();
        Cursor dataCursor;

        //execute select query on playlist table and get playlist data
        if (id != null)
            dataCursor = db.query(playlist, null, "pid=?", new String[]{"" + id}, null, null, null);
        else
            dataCursor = db.query(playlist, null, null, null, null, null, "pid");

        //create instance of PlaylistModel from data retried from DB
        PlaylistModel list = null;
        if (dataCursor.moveToFirst()) {
            //call getSlide Method to get SlideModels belongs to this playlist by using playlist id
            list = new PlaylistModel(dataCursor.getInt(0), dataCursor.getString(1), dataCursor.getInt(2), dataCursor.getInt(3), getSlide(dataCursor.getInt(0)));

        }
        //return playlistModel object
        return list;
    }

    //getSlide method returns List of slideModel (slides) which have playlist_id(pid) as passed parameter
    public List<SlideModel> getSlide(Integer pid) {
        SQLiteDatabase db = getReadableDatabase();

        //Initialize List<SlideModel> to store slides retrieved from database
        List<SlideModel> allslides = new ArrayList<SlideModel>();
        Cursor dataCursor;

        //execute select query which returns records with pid as passed pid
        dataCursor = db.query(slides, null, "pid=?", new String[]{"" + pid}, null, null, "uid");

        SlideModel slide = null;

        //Create instance of slideModel  for all retrieved records and add them into allslides
        //read data from cursor
        if (dataCursor.moveToFirst()) {
            do {

                Integer uid=dataCursor.getInt(dataCursor.getColumnIndex("uid"));
                Integer sid = dataCursor.getInt(dataCursor.getColumnIndex("sid"));
                Boolean anim = (dataCursor.getInt(dataCursor.getColumnIndex("animate")) == 1);
                String name = dataCursor.getString(dataCursor.getColumnIndex("name"));
                String bgcolor = dataCursor.getString(dataCursor.getColumnIndex("bgcolor"));
                Integer bgimage = dataCursor.getInt(dataCursor.getColumnIndex("bgimage"));
                Integer duration = dataCursor.getInt(dataCursor.getColumnIndex("duration"));
                Integer next = dataCursor.getInt(dataCursor.getColumnIndex("next"));
                Integer audio = dataCursor.getInt(dataCursor.getColumnIndex("audio"));


                AnimationModel enterAnimation = null, exitAnimation = null;
                if (anim) {

                    //Retrieve animation data from animation table using enteranim and exitanim as animid and create AnimationModel instances enterAnimation ad exitAnimation
                    Cursor c2 = db.query(animations, null, "animid=?", new String[]{"" + dataCursor.getInt(dataCursor.getColumnIndex("enteranim"))}, null, null, null);
                    if (c2.moveToFirst()) {
                        enterAnimation = new AnimationModel(c2.getString(c2.getColumnIndex("type")), c2.getInt(c2.getColumnIndex("delay")), c2.getInt(c2.getColumnIndex("duration")));
                    }

                    c2 = db.query(animations, null, "animid=?", new String[]{"" + dataCursor.getInt(dataCursor.getColumnIndex("exitanim"))}, null, null, null);
                    if (c2.moveToFirst()) {
                        exitAnimation = new AnimationModel(c2.getString(c2.getColumnIndex("type")), c2.getInt(c2.getColumnIndex("delay")), c2.getInt(c2.getColumnIndex("duration")));
                    }
                }
                //create instance of SlideModel by using data retrieved from table
                slide = new SlideModel(sid, bgimage, duration, next, audio, anim, name, bgcolor, enterAnimation,exitAnimation);

                //initialize  List<ComponentModel> to store ComponentModel object which are part of current slide
                List<ComponentModel> components = new ArrayList<ComponentModel>();

                //execute select query in component table for components where sid(slideid) is uid(unique id of current slide)
                Cursor comp = db.query(component, null, "sid=?", new String[]{"" + uid}, null, null, "cid");
                //read data from cursor
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

                   //     System.out.println("<--------------------------------------------\n\n\n " + opacity + "---------------------" + comp.getDouble(comp.getColumnIndex("opacity")) + "\n--------------------->\n\n\n\n");
                        String onclick = comp.getString(comp.getColumnIndex("onclick"));
                        Boolean animate = (comp.getInt(comp.getColumnIndex("animate")) == 1);
                        AnimationModel enter = null, exit = null;


                        if (animate) {

                            //get enter and exitanimation of component using enteranim and exitanim of component and create objects of AnimationModel enter and exit
                            Cursor c2 = db.query(animations, null, "animid=?", new String[]{"" + comp.getInt(comp.getColumnIndex("enteranim"))}, null, null, null);
                            if (c2.moveToFirst()) {
                                enter = new AnimationModel(c2.getString(c2.getColumnIndex("type")), c2.getInt(c2.getColumnIndex("delay")), c2.getInt(c2.getColumnIndex("duration")));
                            }

                            c2 = db.query(animations, null, "animid=?", new String[]{"" + comp.getInt(comp.getColumnIndex("exitanim"))}, null, null, null);
                            if (c2.moveToFirst()) {
                                exit = new AnimationModel(c2.getString(c2.getColumnIndex("type")), c2.getInt(c2.getColumnIndex("delay")), c2.getInt(c2.getColumnIndex("duration")));
                            }

                        }
                        //create new componentModel from retrieve data and add that component object to List
                        components.add(new ComponentModel(id, type, left, top, width, height, uri, shadow, scalex, scaley, zindex, angle, opacity, onclick, animate, enter, exit));
                    } while (comp.moveToNext());
                    //set components of slide by calling setComponents and pass List of componentModel as parameter
                    slide.setComponents(components);

                }
                //add slide to list of slideModel
                allslides.add(slide);
            } while (dataCursor.moveToNext());

            //return list of SlideModel
            return allslides;
        }
        return null;
    }

}
