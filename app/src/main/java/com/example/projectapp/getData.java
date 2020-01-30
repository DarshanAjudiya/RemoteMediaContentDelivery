package com.example.projectapp;

import android.content.Context;
import android.os.AsyncTask;

import com.example.projectapp.extras.Parser;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class getData extends AsyncTask<Void, Void, Void> {
    DatabaseHelper helper;
    Context context;

    public getData(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        helper = new DatabaseHelper(context);
        try {
            URL url = new URL("https://api.myjson.com/bins/ad4o8");

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = bufferedReader.readLine();
            String data = "";
            while (line != null) {
                data = data + line;
                line = bufferedReader.readLine();
            }
            System.out.println(data);

            PlaylistModel myplaylist = null;
            try {
                myplaylist = Parser.parsetoobject(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (myplaylist != null) {
                myplaylist.printall();
                int count = helper.insert_playlist(myplaylist);
                System.out.println("playlist added :" + count);
            } else {
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
