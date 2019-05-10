package com.idisfkj.hightcopywx.find.workorder;

import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by dell on 2018/5/18.
 */

public class MyNet extends AsyncTask<String, Void, byte[]> {

    private ImageView iv;
    private String path;
    public MyNet(ImageView imageView) {
        this.iv = imageView;
    }
    @Override
    protected byte[] doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            path = params[0];
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);
            con.connect();
            if (con.getResponseCode() == HttpURLConnection.HTTP_OK && !isCancelled()) {
                InputStream inputStream = con.getInputStream();
                int len;
                byte[] buff = new byte[1024 * 4];
                ByteArrayOutputStream bao = new ByteArrayOutputStream();
                while ((len = inputStream.read(buff)) != -1) {
                    bao.write(buff, 0, len);
                }
                inputStream.close();
                return bao.toByteArray();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    };

    @Override
    protected void onPostExecute(byte[] bytes) {
        super.onPostExecute(bytes);
        if (bytes != null && iv.getTag().equals(path)) { //加载类中，回来直接判断是不是最新的tag 也 就是最新滑动的item
            iv.setImageBitmap(BitmapFactory.decodeByteArray(bytes,0,bytes.length));
        }
    }
}
