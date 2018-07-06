package com.demo.service;

import com.demo.entity.UserRedPacket;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chenfeixiang
 * @since 2018-07-05
 */
public interface UserRedPacketService extends IService<UserRedPacket> {
    /**
     * 保存抢红包信息
     * @param redPacketId 红包编号
     * @param userId 抢红包用户编号
     * @return 影响的记录数
     */
    int grepRedPacket(Integer redPacketId, Integer userId);

    /**
     * 保存抢红包信息(乐观锁)
     * @param redPacketId 红包编号
     * @param userId 抢红包用户编号
     * @return 影响的记录数
     */
    int grepRedPacketForVersion(Integer redPacketId, Integer userId);

}
