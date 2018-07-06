package com.demo.dao;

import com.demo.entity.UserRedPacket;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author chenfeixiang
 * @since 2018-07-05
 */
public interface UserRedPacketDao extends BaseMapper<UserRedPacket> {

    /**
     * 插入抢红包信息
     * @param userRedPacket
     * @return 影响的行数
     */
    int grepRedPacket(UserRedPacket userRedPacket);
}
