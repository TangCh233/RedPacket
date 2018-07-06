package com.demo.service.impl;

import com.demo.entity.RedPacket;
import com.demo.dao.RedPacketDao;
import com.demo.service.RedPacketService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author chenfeixiang
 * @since 2018-07-05
 */
@Service
public class RedPacketServiceImpl extends ServiceImpl<RedPacketDao, RedPacket> implements RedPacketService {

    @Autowired
    private RedPacketDao redPacketDao;
    /**
     * 获取红包
     * 事务的隔离级别和传播行为
     * @param id
     * @return
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public RedPacket getRedPacket(Integer id) {
        return redPacketDao.getRedPacket(id);
    }

    /**
     * 获取红包（悲观锁）
     *
     * @param id
     * @return
     */
    @Override
    public RedPacket getRedPacketForUpdate(Integer id) {
        return redPacketDao.getRedPacketForUpdate(id);
    }

    /**
     * 扣减红包
     *
     * @param id
     * @return
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public int decreaseRedPacket(Integer id) {
        return redPacketDao.decreaseRedPacket(id);
    }
}
