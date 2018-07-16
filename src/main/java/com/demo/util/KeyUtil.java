package com.demo.util;

import java.util.Random;

/**
 * @Description: 生成唯一主键工具类
 * @Author: chenfeixiang
 * @Date: Created in 15:31 2018/7/13
 */
public class KeyUtil {
    public static synchronized String genUniqueKey() {
        Random random = new Random();
        // 在0~900000直接随机生成一个Int值
        Integer number = random.nextInt(900000) + 100000;
        return System.currentTimeMillis() + String.valueOf(number);
    }
}
