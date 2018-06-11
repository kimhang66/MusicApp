package com.example.kimha.musicapp.Api;

import android.os.AsyncTask;
import android.util.Log;

import com.example.kimha.musicapp.CallBack;
import com.example.kimha.musicapp.Const;

import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by kimha on 5/27/2018.
 */

public class DoPost extends AsyncTask<String, Void, String> {

    CallBack<String> callBack;
    JSONObject data;

    public DoPost(CallBack<String> callBack, JSONObject data) {
        this.callBack = callBack;
        this.data = data;
    }
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Log.i("GET_STATUS", s);
        callBack.success(s);
    }

    @Override
    protected String doInBackground(String... strings) {
        String urlString = Const.URL + strings[0];
        URL url = null;
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        OutputStream outStream;
        int c;
        String result = "";
        try {
            url = new URL(urlString);
            httpURLConnection = (HttpURLConnection) url.openConnection();//truyen vao method
            httpURLConnection.setRequestMethod("POST");

            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);

            httpURLConnection.setRequestProperty("Content-Type","application/json");
            httpURLConnection.setRequestProperty("Accept","application/json");

            outStream = new BufferedOutputStream(httpURLConnection.getOutputStream());
            Log.i("AAA", data.toString());
            outStream.write(data.toString().getBytes(Charset.forName("UTF-8")));
            outStream.flush();
            outStream.close();

            inputStream = httpURLConnection.getInputStream();
            //khác -1 là vẫn còn
            while ((c=inputStream.read()) != -1){
                result+=(char)c;
            }
            return result;
        } catch (Exception e) {
            //that bai
            e.printStackTrace();
            return "400";
        }
    }
}
