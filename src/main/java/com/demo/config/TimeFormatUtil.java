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
     * 格式化当前时间，返回Date类型
     */
    public Date getCurrentTime(Date currentDate){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(sdf.format(currentDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
