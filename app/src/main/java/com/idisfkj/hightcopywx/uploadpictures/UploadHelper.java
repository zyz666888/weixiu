package com.idisfkj.hightcopywx.uploadpictures;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.idisfkj.hightcopywx.util.Islogined;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class UploadHelper {

	private Context mContext;
	public UploadHelper(Context context)
	{
		this.mContext = context;
	}
	static String imageurl;
	private static JSONObject parseObject;
	
	/** 
     * 网络连接是否可用 
     */  
    public static boolean isConnnected(Context context) {  
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE); 
        if (null != connectivityManager) {  
            NetworkInfo networkInfo[] = connectivityManager.getAllNetworkInfo();  
            if (null != networkInfo) {  
                for (NetworkInfo info : networkInfo) {  
                    if (info.getState() == NetworkInfo.State.CONNECTED) {  
                        return true;  
                    }  
                }  
            }  
        }  
        return false;  
    }  
	
	public  String  post(Context context,String actionUrl, Map<String, Drawable> files,String flag,String aftersaleid) {

		String result = null;
		if (isConnnected(context)) {

			try
			{
				 StringBuilder sb2 = new StringBuilder(); 
				  String BOUNDARY = java.util.UUID.randomUUID().toString();
				  String PREFIX = "--" , LINEND = "\r\n";
				  String MULTIPART_FROM_DATA = "multipart/form-data"; 
				  String CHARSET = "UTF-8";
				  URL uri = new URL(actionUrl+"?afterSaleId="+aftersaleid+"&flag="+flag);
				  HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
				  conn.setReadTimeout(15 * 1000); 
				  conn.setDoInput(true);
				  conn.setDoOutput(true);
				  conn.setUseCaches(false); 
				  conn.setRequestMethod("POST");
				  conn.setRequestProperty("Connection", "Keep-Alive");
				  conn.setRequestProperty("Charsert", "UTF-8");
				  conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA + ";boundary=" + BOUNDARY);
				  conn.setRequestProperty("action", "upload");
				  conn.setRequestProperty("Cookie", Islogined.getCookiePreference(this.mContext));// 有网站需要将当前的session id一并上传
				  conn.connect();
				  DataOutputStream outStream = new DataOutputStream(conn.getOutputStream());

				  if(files!=null ){
				    for (Map.Entry<String, Drawable> file: files.entrySet()) {
				      StringBuilder sb1 = new StringBuilder(); 
				      sb1.append(PREFIX); 
				      sb1.append(BOUNDARY); 
				      sb1.append(LINEND);
					  sb1.append("Content-Disposition: form-data; name=files; filename=" +LINEND);
				      sb1.append("Content-Type: application/octet-stream; charset="+CHARSET+LINEND);
				      sb1.append(LINEND);
				      outStream.write(sb1.toString().getBytes()); 

				      InputStream is = FormatTools.getInstance().Drawable2InputStream(file.getValue());
				      byte[] buffer = new byte[1024]; 
				      int len = 0; 
				      while ((len = is.read(buffer)) != -1) { 
				        outStream.write(buffer, 0, len); 
				      }

				      is.close(); 
				      outStream.write(LINEND.getBytes()); 
				    }
				  }
				  byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
				  outStream.write(end_data); 
				  outStream.flush();
				/**
				 * 获取响应码  200=成功
				 * 当响应成功，获取响应的流
				 */

				  int res = conn.getResponseCode(); 
				  
				  Log.d("AA",  "code:" + res);
				  
				  InputStream in = null;
				  if (res == 200) {
				    in = conn.getInputStream(); 
				    int ch; 
				    
				    while ((ch = in.read()) != -1) { 
				      sb2.append((char) ch); 
				    }
				    Log.d("AA",  "data:" + sb2.toString());
				  }

				result=sb2.toString();
				return result;
			}
			catch ( Exception e)
			{
				Log.d("AA", "error post: " + e.getMessage());
				e.printStackTrace();
			}
			return null;
		}else {
			
			return null;
		}
		
		}
	public  String  friendcirclepost(Context context,String actionUrl, Map<String, Drawable> files) {

		String result = null;
		if (isConnnected(context)) {

			try
			{
				StringBuilder sb2 = new StringBuilder();
				String BOUNDARY = java.util.UUID.randomUUID().toString();
				String PREFIX = "--" , LINEND = "\r\n";
				String MULTIPART_FROM_DATA = "multipart/form-data";
				String CHARSET = "UTF-8";
				URL uri = new URL(actionUrl);
				HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
				conn.setReadTimeout(15 * 1000);
				conn.setDoInput(true);
				conn.setDoOutput(true);
				conn.setUseCaches(false);
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Connection", "Keep-Alive");
				conn.setRequestProperty("Charsert", "UTF-8");
				conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA + ";boundary=" + BOUNDARY);
				conn.setRequestProperty("action", "upload");
				conn.setRequestProperty("Cookie", Islogined.getCookiePreference(this.mContext));// 有网站需要将当前的session id一并上传
				conn.connect();
				DataOutputStream outStream = new DataOutputStream(conn.getOutputStream());

				if(files!=null ){
					for (Map.Entry<String, Drawable> file: files.entrySet()) {
						StringBuilder sb1 = new StringBuilder();
						sb1.append(PREFIX);
						sb1.append(BOUNDARY);
						sb1.append(LINEND);
						sb1.append("Content-Disposition: form-data; name=files; filename=" +LINEND);
						sb1.append("Content-Type: application/octet-stream; charset="+CHARSET+LINEND);
						sb1.append(LINEND);
						outStream.write(sb1.toString().getBytes());

						InputStream is = FormatTools.getInstance().Drawable2InputStream(file.getValue());
						byte[] buffer = new byte[1024];
						int len = 0;
						while ((len = is.read(buffer)) != -1) {
							outStream.write(buffer, 0, len);
						}

						is.close();
						outStream.write(LINEND.getBytes());
					}
				}
				byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
				outStream.write(end_data);
				outStream.flush();
				/**
				 * 获取响应码  200=成功
				 * 当响应成功，获取响应的流
				 */

				int res = conn.getResponseCode();

				Log.d("AA",  "code:" + res);

				InputStream in = null;
				if (res == 200) {
					in = conn.getInputStream();
					int ch;

					while ((ch = in.read()) != -1) {
						sb2.append((char) ch);
					}
					Log.d("AA",  "data:" + sb2.toString());
				}

				result=sb2.toString();
				return result;
			}
			catch ( Exception e)
			{
				Log.d("AA", "error post: " + e.getMessage());
				e.printStackTrace();
			}
			return null;
		}else {

			return null;
		}

	}
	public  String  postQuality(Context context,String actionUrl, Map<String, Drawable> files,String flag,String qualityid) {

		String result = null;
		if (isConnnected(context)) {

			try
			{
				StringBuilder sb2 = new StringBuilder();
				String BOUNDARY = java.util.UUID.randomUUID().toString();
				String PREFIX = "--" , LINEND = "\r\n";
				String MULTIPART_FROM_DATA = "multipart/form-data";
				String CHARSET = "UTF-8";
				URL uri = new URL(actionUrl+"?qualityid="+qualityid+"&flag="+flag);
				HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
				conn.setReadTimeout(15 * 1000);
				conn.setDoInput(true);
				conn.setDoOutput(true);
				conn.setUseCaches(false);
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Connection", "Keep-Alive");
				conn.setRequestProperty("Charsert", "UTF-8");
				conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA + ";boundary=" + BOUNDARY);
				conn.setRequestProperty("action", "upload");
				conn.setRequestProperty("Cookie", Islogined.getCookiePreference(this.mContext));// 有网站需要将当前的session id一并上传
				conn.connect();
				DataOutputStream outStream = new DataOutputStream(conn.getOutputStream());

				if(files!=null ){
					for (Map.Entry<String, Drawable> file: files.entrySet()) {
						StringBuilder sb1 = new StringBuilder();
						sb1.append(PREFIX);
						sb1.append(BOUNDARY);
						sb1.append(LINEND);
						sb1.append("Content-Disposition: form-data; name=files; filename=" +LINEND);
						sb1.append("Content-Type: application/octet-stream; charset="+CHARSET+LINEND);
						sb1.append(LINEND);
						outStream.write(sb1.toString().getBytes());

						InputStream is = FormatTools.getInstance().Drawable2InputStream(file.getValue());
						byte[] buffer = new byte[1024];
						int len = 0;
						while ((len = is.read(buffer)) != -1) {
							outStream.write(buffer, 0, len);
						}

						is.close();
						outStream.write(LINEND.getBytes());
					}
				}
				byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
				outStream.write(end_data);
				outStream.flush();
				/**
				 * 获取响应码  200=成功
				 * 当响应成功，获取响应的流
				 */
				int res = conn.getResponseCode();

				InputStream in = null;
				if (res == 200) {
					in = conn.getInputStream();
					int ch;

					while ((ch = in.read()) != -1) {
						sb2.append((char) ch);
					}
					Log.d("AA",  "data:" + sb2.toString());
				}

				result=sb2.toString();
				return result;
			}
			catch ( Exception e)
			{
				Log.d("AA", "error post: " + e.getMessage());
				e.printStackTrace();
			}
			return null;
		}else {

			return null;
		}

	}

}
