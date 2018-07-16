package com.demo.service.impl;

import com.demo.service.RedisLockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @Description: RedisLockService实现类
 * @Author: chenfeixiang
 * @Date: Created in 15:04 2018/7/13
 */
@Service
public class RedisLockServiceImpl implements RedisLockService {

    private static final Logger logger = LoggerFactory.getLogger(RedisLockServiceImpl.class);

    @Autowired
    StringRedisTemplate stringRedisTemplate;



    /**
     * 加锁
     *
     * @param key
     * @param value 当前时间 + 超时时间
     * @return
     */
    @Override
    public boolean lock(String key, String value) {
        // 先判断缓存中是否存在值，没有返回true，并保存value,已经有值就不保存，返回false
        if(stringRedisTemplate.opsForValue().setIfAbsent(key, value)) {
            return true;
        }
        String curentValue = stringRedisTemplate.opsForValue().get(key);

        // 如果锁过期
        if(!StringUtils.isEmpty(curentValue) && Long.parseLong(curentValue) < System.currentTimeMillis()) {
            // getAndSet设置新值，并返回旧值
            // 获取上一个锁的时间
            String oldValue = stringRedisTemplate.opsForValue().getAndSet(key, value);
            if(!StringUtils.isEmpty(curentValue) && oldValue.equals(curentValue)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 减锁
     *
     * @param key
     * @param value
     */
    @Override
    public void unLock(String key, String value) {
        try {
            String currentValue = stringRedisTemplate.opsForValue().get(key);
            if(!StringUtils.isEmpty(currentValue) && currentValue.equals(value)) {
                stringRedisTemplate.opsForValue().getOperations().delete(key);
            }
        } catch (Exception e) {
            logger.error("RedisLock 解锁异常:" + e.getMessage());
        }
    }
}
