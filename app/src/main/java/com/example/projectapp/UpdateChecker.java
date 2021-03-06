package com.example.projectapp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;



//UpdateChecker class used to check for update of application and install updates
public class UpdateChecker extends AsyncTask<Void, Void, Void> {
    Context context;
    Handler handler;

    public UpdateChecker(Context context) {
        this.context = context;
        handler = new Handler(context.getMainLooper());
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {

            //Url from where it will check if there is new update of application
            URL url = new URL(context.getString(R.string.checkupdateurl));

            //Read (version info of application)Json data from specified url
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream stream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            String line = reader.readLine();
            String data = "";

            while (line != null) {
                data += line;
                line = reader.readLine();
            }

            //System.out.println(data);
            //read version code of application placed of server
            JSONArray array = new JSONArray(data);
            JSONObject main = array.getJSONObject(0);
            JSONObject object = main.getJSONObject("apkData");
            long versioncode = object.getLong("versionCode");

            //Get Version code of current application
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            long appversioncode = -1;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                appversioncode = info.getLongVersionCode();
            } else {
                appversioncode = info.versionCode;
            }

            //System.out.println("Current version:" + appversioncode + "\nserverappversion:" + versioncode);

            //Compare Version codes
            if (appversioncode < versioncode) {
                //If update is available download new application and install that application

                //Url to download updated application
                url = new URL(context.getString(R.string.applicationurl));
                connection = (HttpURLConnection) url.openConnection();

                //Download Application
                File dest = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "app-release.apk");
                //     System.out.println(dest.getAbsolutePath());
                FileOutputStream outputStream = new FileOutputStream(dest, false);
                stream = connection.getInputStream();
                byte[] buffer = new byte[1024];
                int length = 0;
                while ((length = stream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, length);
                }
                outputStream.close();

               /* handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context.getApplicationContext(), "" + Environment.getRootDirectory().canWrite(), Toast.LENGTH_SHORT).show();
                    }
                });*/
                //System.out.println(Environment.getRootDirectory().canWrite());


                //check if device is rooted and appplication have root access
                if (checkRootMethod1() || checkRootMethod2() || checkRootMethod3()) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "in if", Toast.LENGTH_SHORT).show();
                        }
                    });
                    //Install downloaded application silently
                    installapk(dest);
                } else {

                    //get Uri of downloaded applcation
                    Uri fileuri;
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                        fileuri = getURI(context.getApplicationContext(), dest);
                    //    System.out.println(fileuri.toString());
                    } else
                        fileuri = Uri.fromFile(dest);

                    //create intent to send request to install application
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(fileuri, "application/vnd.android.package-archive");
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE, true);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    context.startActivity(intent);
                }
            }
        } catch (IOException | JSONException | PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void installapk(File dest) { //install new updated application using abd command

        //check if file exists
        if (dest.exists()) {
            String command;
            command = "adb install -r " + dest.getAbsolutePath();
            Process proc = null;
            /*handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context.getApplicationContext(), "In installapk", Toast.LENGTH_SHORT).show();
                }
            });*/
            try {

                //execute command
                proc = Runtime.getRuntime().exec(new String[]{"su", "-c", command});
                proc.waitFor();

            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    //getUri() returns content uri of specific file passed as parameter
    private Uri getURI(Context context, File dest) {
        //Get Content URI of file like content:// instead of file://
        return FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", dest);
    }

    //checkrootMethod 1 2 and 3 check for either device is rooted or not(does device have provides root access)

    public boolean checkRootMethod1() {
        String buildTags = android.os.Build.TAGS;
        return buildTags != null && buildTags.contains("test-keys");
    }

    public boolean checkRootMethod2() {
        String[] paths = {"/system/app/Superuser.apk", "/sbin/su", "/system/bin/su", "/system/xbin/su", "/data/local/xbin/su", "/data/local/bin/su", "/system/sd/xbin/su",
                "/system/bin/failsafe/su", "/data/local/su", "/su/bin/su"};
        for (String path : paths) {
            if (new File(path).exists()) return true;
        }
        return false;
    }

    public boolean checkRootMethod3() {
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(new String[]{"/system/xbin/which", "su"});
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            if (in.readLine() != null) return true;
            return false;
        } catch (Throwable t) {
            return false;
        } finally {
            if (process != null) process.destroy();
        }
    }
}

