package com.example.projectapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
            public static final String DBNAME="mediacontent";
            public static final String playlist="playlist";
            public static final String slides="slide";
            public static final String component="component";
            public static final String animations="animations";


    public DatabaseHelper(@Nullable Context context) {
        super(context, DBNAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //playlist table
        db.execSQL("create table "+playlist+"(pid integer primary key ,name text,height  real, width real)");
        //slides table
        db.execSQL("create table "+slides+"(uid integer primary key autoincrement,sid integer,pid integer,name text,bgcolor text,bgimage integer,duration integer,next integer,animate integer,animationid integer,audio integer)");
        //component table
        db.execSQL("create table "+component+"(cid integer,sid integer,type text,left_pos integer,right_pos integer,top_pos integer,bottom_pos integer,width real,height real,uri text,shadow text,scalex integer,scaley integer,z_index integer,opacity real,angle integer,onclick text,animate integer,enteranim integer,exitanim integer)");
        //animation table
        db.execSQL("create table "+animations+"(animid integer primary key autoincrement,type text,duration integer,delay integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("drop table IF  exists "+playlist);
    db.execSQL("drop table if exists "+slides);
    db.execSQL("drop table if exists "+component);
    db.execSQL("drop table if exists "+animations);
    this.onCreate(db);
    }
    public int insert_playlist(Playlist list)
    {

        ContentValues values=new ContentValues();
        values.put("pid",list.getId());
        values.put("name",list.getName());
        values.put("height",list.getHeight());
        values.put("width",list.getWidth());
        for(Slide s:list.getSlides())
            insert_slide(s,list.getId());
        SQLiteDatabase db=this.getWritableDatabase();
        int x= (int) db.insert(playlist,null,values);
        db.close();
        return x;
    }

    public int insert_slide(Slide slide,int pid)
    {

        ContentValues values=new ContentValues();
        values.put("pid",pid);
        values.put("sid",slide.getId());
        values.put("name",slide.getName());
        values.put("bgcolor",slide.getBgcolor());
        values.put("bgimage",slide.getBgimage());
        //values.put("bgImageUri",slide.getBgImageUri());
        values.put("duration",slide.getDuration());
        values.put("next",slide.getNext());
        values.put("animate",slide.getAnimate());
        values.put("audio",slide.getAudio());
        if(slide.getAnimate())
        {
            ContentValues val=new ContentValues();
            val.put("duration",slide.getAniduration());
            val.put("type",slide.getAnimation());
            SQLiteDatabase db=this.getWritableDatabase();
            long row=db.insert(animations,null,val);

            Cursor c=db.rawQuery("select animid from "+animations+" where rowid="+row+"",null);
            if(c.moveToFirst()) {
                int aid = c.getInt(c.getColumnIndex("animid"));
                values.put("animationid", aid);
            }
            db.close();
        }
        SQLiteDatabase db=this.getWritableDatabase();
        long x= db.insert(slides,null,values);
        Cursor c=db.rawQuery("select uid from "+slides+" where rowid="+x,null);
        if(c.moveToFirst())
        {
            int uid=c.getInt(c.getColumnIndex("uid"));
            for(Component comp:slide.getComponents())
                insert_component(comp,uid);
        }
        db.close();
        return 0;
    }
    public int insert_component(Component component,int uid)
    {

        ContentValues values=new ContentValues();
        values.put("cid",component.getId());
        values.put("sid",uid);
        values.put("type",component.getType());
        values.put("left_pos", component.getLeft());
        values.put("right_pos",component.getRight());
        values.put("top_pos", component.getTop());
        values.put("bottom_pos",component.getBottom());
        values.put("width",component.getWidth());
        values.put("height",component.getHeight());
        values.put("uri", component.getUri());
        values.put("shadow",component.getShadow());
        values.put("scalex",component.getScaleX());
        values.put("scaley",component.getScaleY());
        values.put("z_index",component.getZ_index());
        values.put("opacity",component.getOpacity());
        values.put("angle",component.getAngle());
        values.put("onclick",component.getOnClick());
        values.put("animate",component.getIs_animate());
        if(component.getIs_animate()!=null && component.getIs_animate() )
        {
            int enter=insert_anim(component.getEnter_animation());
            int exit=insert_anim(component.getExit_animation());
            if(enter!=-1)
                values.put("enteranim",enter);
            if(exit!=-1)
                values.put("exitanim",exit);
        }
        SQLiteDatabase db=getWritableDatabase();
        long row=db.insert(this.component,null,values);
        db.close();
        return (int) row;
    }

    public int insert_anim(Animate animate)
    {
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("type",animate.getType());
        values.put("delay",animate.getDelay());
        values.put("duration",animate.getDuration());
        long row=db.insert(animations,null,values);
        Cursor c=db.rawQuery("select animid from "+animations+" where rowid="+row+"",null);
        int x=-1;

        if(c.moveToFirst())
            x=c.getInt(c.getColumnIndex("animid"));
        db.close();
        return x;
    }
}
