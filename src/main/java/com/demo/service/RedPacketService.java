package com.demo.service;

import com.demo.entity.RedPacket;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chenfeixiang
 * @since 2018-07-05
 */
public interface RedPacketService extends IService<RedPacket> {
    /**
     * 获取红包（悲观锁）
     * @param id
     * @return
     */
    RedPacket getRedPacketForUpdate(Integer id);

    /**
     * 获取红包
     * @param id
     * @return
     */
    RedPacket getRedPacket(Integer id);

    /**
     * 扣减红包
     * @param id
     * @return
     */
    int decreaseRedPacket(Integer id);
}
