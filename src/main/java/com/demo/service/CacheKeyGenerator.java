package com.demo.service;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * @Description: key生成器
 * @Author: chenfeixiang
 * @Date: Created in 11:55 2018/7/10
 */
public interface CacheKeyGenerator {
    /**
     * 获取AOP参数,生成指定缓存Key
     *
     * @param pjp PJP
     * @return 缓存KEY
     */
    String getLockKey(ProceedingJoinPoint pjp);
}
