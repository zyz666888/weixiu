package com.idisfkj.hightcopywx.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by dell on 2018/6/26.
 */

public  class Islogined {
    public static final String ISLOGINED = "islogined";
    public static final String COOKIE = "cookie";

    public static  void saveCookiePreference(Context context, String value) {
        SharedPreferences preference = context.getSharedPreferences(ISLOGINED, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();
        editor.putString(COOKIE, value);
        editor.apply();
    }
    public static String getCookiePreference(Context context) {
        SharedPreferences preference = context.getSharedPreferences(ISLOGINED, Context.MODE_PRIVATE);
        String s = preference.getString(COOKIE, "");
        return s;
    }
}
