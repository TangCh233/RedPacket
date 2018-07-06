package com.demo.config;

import java.text.ParseException;
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
    public Date getCurrentTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentDate = new Date();
        //格式化方法，返回值为date类型
        Date date = null;
        try {
            date = sdf.parse(sdf.format(currentDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
