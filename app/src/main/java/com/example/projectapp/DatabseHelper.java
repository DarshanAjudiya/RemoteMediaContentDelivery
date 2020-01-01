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



    public DatabseHelper(@Nullable Context context) {
        super(context, DBNAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+playlist+"(pid integer primary key ,name text,height  integer, width integer)");
        //db.execSQL("create table "+slides+"(sid integer  )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
