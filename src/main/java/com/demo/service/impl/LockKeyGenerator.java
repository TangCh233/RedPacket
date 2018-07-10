package com.demo.service.impl;

import com.demo.service.CacheKeyGenerator;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * @Description: Key 生成策略
 * @Author: chenfeixiang
 * @Date: Created in 11:56 2018/7/10
 */
public class LockKeyGenerator implements CacheKeyGenerator {
    /**
     * 获取AOP参数,生成指定缓存Key
     *
     * @param pjp PJP
     * @return 缓存KEY
     */
    @Override
    public String getLockKey(ProceedingJoinPoint pjp) {
        return null;
    }
}
