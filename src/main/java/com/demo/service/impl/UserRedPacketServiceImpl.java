package com.demo.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.demo.config.TimeFormatUtil;
import com.demo.dao.RedPacketDao;
import com.demo.dao.UserRedPacketDao;
import com.demo.entity.RedPacket;
import com.demo.entity.UserRedPacket;
import com.demo.service.UserRedPacketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author chenfeixiang
 * @since 2018-07-05
 */
@Service
public class UserRedPacketServiceImpl extends ServiceImpl<UserRedPacketDao, UserRedPacket> implements UserRedPacketService {

    private static final Logger logger = LoggerFactory.getLogger(UserRedPacketServiceImpl.class);

    @Autowired
    private RedPacketDao redPacketDao;

    @Autowired
    private UserRedPacketDao userRedPacketDao;

    @Autowired
    private RedisTemplate redisTemplate;


    //失败
    private static final int FAILED = 0;

    /**
     * 保存抢红包信息(乐观锁)
     * 重入机制--允许重试次数
     * @param redPacketId 红包编号
     * @param userId      抢红包用户编号
     * @return 影响的记录数
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public int grepRedPacketForVersion(Integer redPacketId, Integer userId) {
        // 允许用户重试抢三次红包
        for(int i = 0; i < 3; i++) {
            // 获取红包信息, 注意version信息
            RedPacket redPacket = redPacketDao.getRedPacket(redPacketId);

            // 如果当前的红包大于0
            if(redPacket.getStock() > 0) {
                // 再次传入线程保存的version旧值给SQL判断，是否有其他线程修改过数据
                int update = redPacketDao.decreaseRedPacketByVersion(redPacketId, redPacket.getVersion());
                // 如果没有数据更新，说明已经有其他线程修改过数据，则继续抢红包
                if(update == 0) {
                    continue;
                }
                // 生成抢红包信息
                UserRedPacket userRedPacket = new UserRedPacket();
                userRedPacket.setRedPacketId(redPacketId);
                userRedPacket.setUserId(userId);
                userRedPacket.setAmount(redPacket.getAmount());
                userRedPacket.setNote("抢红包：" + redPacketId);
                // 插入抢红包信息
                int result = userRedPacketDao.grepRedPacket(userRedPacket);
                return result;
            }else {
                // 一旦发现没有库存，立马返回失败
                return FAILED;
            }
        }

        return FAILED;
    }

    /**
     * 保存抢红包信息
     *
     * @param redPacketId 红包编号
     * @param userId      抢红包用户编号
     * @return 影响的记录数
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public int grepRedPacket(Integer redPacketId, Integer userId) {

        // 获取红包信息
        RedPacket redPacket = redPacketDao.getRedPacketForUpdate(redPacketId);

        if(redPacket.getStock() > 0) {
            redPacketDao.decreaseRedPacket(redPacketId);
            // 生成抢红包信息
            UserRedPacket userRedPacket = new UserRedPacket();
            userRedPacket.setRedPacketId(redPacketId);
            userRedPacket.setUserId(userId);
            userRedPacket.setAmount(redPacket.getAmount());
            userRedPacket.setNote("抢红包：" + redPacketId);
            // 插入抢红包信息
            int result = userRedPacketDao.grepRedPacket(userRedPacket);
            return result;
        }
        return FAILED;
    }

    /**
     * 保存抢红包信息(乐观锁)
     * 重入机制--时间戳
     * @param redPacketId 红包编号
     * @param userId      抢红包用户编号
     * @return 影响的记录数
     *//*
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public int grepRedPacketForVersion(Integer redPacketId, Integer userId) {
        // 记录开始的时间
        long start = System.currentTimeMillis();

        // 无限循环，当抢包时间超过100ms或者成功时退出
        while(true) {
            // 循环当前时间
            long end = System.currentTimeMillis();
            // 如果抢红包的时间已经超过了100ms,就直接返回失败
            if(end - start > 100) {
                return FAILED;
            }
            // 获取红包信息
            RedPacket redPacket = redPacketDao.getRedPacket(redPacketId);

            //如果当前的红包大于0
            if(redPacket.getStock() > 0) {
                // 再次传入线程保存的version旧值给SQL判断，是否有其他线程修改过数据
                int update = redPacketDao.decreaseRedPacketByVersion(redPacketId, redPacket.getVersion());
                // 如果没有数据更新，说明已经有其他线程修改过数据，则继续抢红包
                if(update == 0) {
                    continue;
                }
                // 生成抢红包信息
                UserRedPacket userRedPacket = new UserRedPacket();
                userRedPacket.setRedPacketId(redPacketId);
                userRedPacket.setUserId(userId);
                userRedPacket.setAmount(redPacket.getAmount());
                userRedPacket.setNote("抢红包：" + redPacketId);
                // 插入抢红包信息
                int result = userRedPacketDao.grepRedPacket(userRedPacket);
                return result;
            }else {
                // 一旦发现没有库存，立马返回失败
                return FAILED;
            }
        }
    }*/

    /**
     * 通过redis来保存红包信息
     *
     * @param redPacketId 红包编号
     * @param userId      抢红包用户编号
     * @return
     */
    @Override
    public int grepRedPacketByRedis(Integer redPacketId, Integer userId) {
        // 红包信息
        RedPacket redPacket = null;
        // 抢红包对象信息
        UserRedPacket userRedPacket = null;
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();
        // 先去缓存中查询库存信息，如果缓存中没有缓存信息，就从数据库中去查询，并存入缓存
        if(redisTemplate.hasKey("redPacket")) {
            // 获取当前红包库存
            int stock = (Integer) ops.get("stock");
            // 如果当前的红包不等于0
            if(stock != 0) {
                userRedPacket = new UserRedPacket();
                userRedPacket.setRedPacketId(redPacketId);
                userRedPacket.setUserId(userId);
                userRedPacket.setAmount((BigDecimal)ops.get("amount"));
                userRedPacket.getGrabTime();
                userRedPacket.setNote("抢红包：" + redPacketId);

                // 将抢到红包的用户信息先存入缓存队列
                BoundListOperations<String, Object> listOperations = redisTemplate.boundListOps("userRedPacket");
                listOperations.leftPush(userRedPacket);

                // 红包库存-1
                ops.set("stock", stock - 1);
            }else {
                // 一旦发现没有库存，立马返回失败
                return FAILED;
            }
        }else {
            // 获取红包信息
            redPacket = redPacketDao.getRedPacket(redPacketId);
            // 将库存数存入缓存
            ops.set("stock", redPacket.getStock());
            ops.set("amount", redPacket.getAmount());
        }

    }
}
