package com.example.projectapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabseHelper extends SQLiteOpenHelper {
            public static final String DBNAME="mediacontent";
            public static final String playlist="playlist";
            public static final String slides="slide";
            public static final String component="component";
            public static final String animations="animations";


    public DatabseHelper(@Nullable Context context) {
        super(context, DBNAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //playlist table
        db.execSQL("create table "+playlist+"(pid integer primary key ,name text,height  real, width real)");
        //slides table
        db.execSQL("create table "+slides+"(sid integer,pid integer,name text,bgcolor text,bgimage integer,duration integer,next integer,animate integer,animation integer,audio integer)");
        //component table
        db.execSQL("create table "+component+"(cid integer,sid integer,type text,left_pos integer,right_pos integer,top_pos integer,bottom_pos integer,width real,height real,uri text,shadow integer,scalex integer,scaley integer,z_index integer,angle integer,onclick text,uri text,html text,animate integer,enteranim integer,exitanim integer)");
        //animation table
        db.execSQL("create table "+animations+"(animid integer,type text,duration integer,delay integer)");
        //shadow table
        db.execSQL("");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
