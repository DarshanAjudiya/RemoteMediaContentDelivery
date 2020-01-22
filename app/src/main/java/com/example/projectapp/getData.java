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
            InputStream inputStream=connection.getInputStream();
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
            String line=bufferedReader.readLine();
            String data="";
            while(line!=null)
            {
                data=data+line;
                line=bufferedReader.readLine();
            }
            System.out.println(data);
            Gson gson=new Gson();
            PlaylistModel myplaylist =gson.fromJson(data, PlaylistModel.class);

            if(myplaylist!=null)
            {
                myplaylist.printall();
                int count= helper.insert_playlist(myplaylist);
                System.out.println("playlist added :"+count);
            }
            else
            {
                System.out.println("playlist is null");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
