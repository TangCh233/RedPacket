package com.demo.service;

/**
 * @Description: Redis锁
 * @Author: chenfeixiang
 * @Date: Created in 15:02 2018/7/13
 */
public interface RedisLockService {
    /**
     * 加锁
     * @param key
     * @param value
     * @return
     */
    public boolean lock(String key, String value);

    /**
     * 减锁
     * @param key
     * @param value
     */
    public void unLock(String key, String value);
}
