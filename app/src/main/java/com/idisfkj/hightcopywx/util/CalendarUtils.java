package com.idisfkj.hightcopywx.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间工具类
 * Created by idisfkj on 16/4/26.
 * Email : idisfkj@qq.com.
 */
public class CalendarUtils {

    public CalendarUtils() {
    }

    public static String getCurrentDate(){
        SimpleDateFormat format = new SimpleDateFormat("MM月dd日 HH:mm");
        Date date = new Date(System.currentTimeMillis());
        String time = format.format(date);
        return time;
    }
    //从数据库中拿出来是一个"13245646446456",转化成Date的String格式
    public static String StringToDatestr(String longstr){

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = dateFormat.format(new Date(Long.parseLong(longstr)*1000));
        return time;
    }
}
