package com.example.projectapp;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class getData extends AsyncTask<Void, Void, Void> {
    DatabaseHelper helper;
    MainActivity activity;
    public getData(MainActivity activity) {
        this.activity=activity;
    }

    @Override
    protected Void doInBackground(Void... voids) {
                helper=new DatabaseHelper(activity);
        try {
            URL url=new URL("https://api.myjson.com/bins/ad4o8");

            HttpURLConnection connection=(HttpURLConnection)url.openConnection();
            InputStream iostream=connection.getInputStream();
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(iostream));
            String line=bufferedReader.readLine();
            String data="";
            while(line!=null)
            {
                data=data+line;
                line=bufferedReader.readLine();
            }
            System.out.println(data);
            Gson gson=new Gson();
            Playlist myplaylist =gson.fromJson(data,Playlist.class);

            if(myplaylist!=null)
            {

                helper.insert_playlist(myplaylist);
                myplaylist.printall();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
