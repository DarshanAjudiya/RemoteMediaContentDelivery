package com.example.projectapp;

import android.os.AsyncTask;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class getData extends AsyncTask<Void, Void, Void> {
    @Override
    protected Void doInBackground(Void... voids) {

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
                myplaylist.printall();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
