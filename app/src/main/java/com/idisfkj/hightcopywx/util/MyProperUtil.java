package com.idisfkj.hightcopywx.util;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class MyProperUtil {
    private static Properties urlProps;
    public static Properties getProperties(Context c){
        Properties props = new Properties();
        try {
            //方法一：通过activity中的context攻取setting.properties的FileInputStream
            InputStream in = c.getAssets().open("settingConfig.properties");
            props.load(in);
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        urlProps = props;
        return urlProps;
    }

    public static String getProperties(Context c,String key,String proName){
        Properties props = new Properties();
        try {
            //方法一：通过activity中的context攻取setting.properties的FileInputStream
            InputStream in = c.getAssets().open(proName);
            props.load(in);
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        return  props.getProperty(key);
    }

    public static String getPropertiesUrl(Context c,String key){
        Properties props = new Properties();
        try {
            //方法一：通过activity中的context攻取setting.properties的FileInputStream
            InputStream in = c.getAssets().open("settingConfig.properties");
            props.load(in);
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        return  props.getProperty("IP")+props.getProperty(key);
    }

    /**
     * 修改properties文件
     * @param c
     * @param key
     * @param value
     */
    public static void setProperties(Context c,String key,String value){
        Properties props = new Properties();
        try {
            InputStream in = c.getAssets().open("settingConfig.properties");
            props.load(in);

           /* FileOutputStream fos =
                    new FileOutputStream( c.getAssets().openFd("settingConfig.properties").getFileDescriptor());
            props.setProperty(key, value);
            props.store(fos, null);
            fos.close();*/

            in.close();
            File file = new File("file:///android_asset/settingConfig.properties");
            OutputStream fos = new FileOutputStream(file);
            props.store(fos, "Update '" + key + "' value");
            fos.flush();
            System.out.println("setProperty success: " + getProperties(c).getProperty(key));
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

}

