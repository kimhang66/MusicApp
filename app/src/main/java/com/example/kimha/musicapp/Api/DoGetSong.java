package com.example.kimha.musicapp.Api;

import android.os.AsyncTask;
import android.util.Log;

import com.example.kimha.musicapp.CallBack;
import com.example.kimha.musicapp.Const;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by kimha on 5/20/2018.
 */

public class DoGetSong extends AsyncTask<String, Void, String> { //kiểu tham số đầu vào pt doinBG, kiểu dl đầu vào pt onprogressupdate, kiểu dl đầu vào pt postexecute
    CallBack<String> callBack;

    public DoGetSong(CallBack callBack) {
        this.callBack = callBack;
    }
    @Override
    protected void onPostExecute(String o) { //thông báo kết quả sau khi doinbackground hoàn tất
        super.onPostExecute(o);
        Log.i("GET_STATUS", o);
        callBack.success(o);
    }

    @Override
    protected String doInBackground(String... strings) { //thực hiện chức năng xử lí chính, truyền 1 hoặc nhiều giá trị

        String urlString = Const.URL + strings[0];
        URL url = null;
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        int c;
        String result = "";

        try {
            url = new URL(urlString);
            httpURLConnection = (HttpURLConnection) url.openConnection();//truyen vao method
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setUseCaches(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.connect();
            inputStream = httpURLConnection.getInputStream();

            //khác -1 là vẫn còn
            while ((c = inputStream.read()) != -1) {
                result += (char) c;
            }
            if (result != null) {
                return result;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "404";
    }
}
