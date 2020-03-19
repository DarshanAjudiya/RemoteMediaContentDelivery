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
import java.util.List;


//getData class is used to download data from server and store that data in database
public class getData extends AsyncTask<Void, Void, Void> {

    DatabaseHelper helper;
    Context context;

    public getData(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        //Get data from Provided URL and store that data into SQLite Database
        helper = new DatabaseHelper(context);
        try {
            //Get URL specified URL
           // URL url = new URL("https://jsonblob.com/api/212f0a09-5319-11ea-8e7b-552f2d86fbba");
            URL url = new URL("https://jsonblob.com/api/8de1b244-6a83-11ea-92f8-efcc68f65654");

            //Establish HttpUrlConnection and get InputStream of created connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = connection.getInputStream();
            //Create BufferedReader to read data from InputStream of Connection*
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            //Read data from BufferedReader
            String line = bufferedReader.readLine();
            String data = "";
            while (line != null) {
                data = data + line;
                line = bufferedReader.readLine();
            }
            System.out.println(data);

            //parse Json data retrieved from server to java object
            PlaylistModel[] myplaylist = null;
            try {
                //parses using Parser.parsetoobject()
                myplaylist = Parser.parsetoobject(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //Store data retrieved from server  into Database
            if (myplaylist!=null)
            for (PlaylistModel list : myplaylist) {
                list.printall();
                int count = helper.insert_playlist(list);
                // System.out.println("playlist added :" + count);
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
