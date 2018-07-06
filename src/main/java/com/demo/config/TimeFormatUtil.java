package com.demo.config;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description: 格式化时间工具类
 * @Author: chenfeixiang
 * @Date: Created in 16:23 2018/7/6
 */
public class TimeFormatUtil {
    /**
     * 获取当前时间
     */
   /* public String getCurrentTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        System.out.printf(""+ date);
        //格式化方法，返回值为String类型
        String a = sdf.format(date);
        return a;
    }*/

    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        System.out.printf(""+ date);
        //格式化方法，返回值为String类型
        String a = sdf.format(date);
        System.out.println(a);
    }
}
