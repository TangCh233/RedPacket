package com.demo.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.demo.entity.RedPacket;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author chenfeixiang
 * @since 2018-07-05
 */
public interface RedPacketDao extends BaseMapper<RedPacket> {

    /**
     * 获取红包信息(悲观锁)
     * @param id
     * @return
     */
    RedPacket getRedPacketForUpdate(Integer id);

    /**
     * 获取红包信息
     * @param id
     * @return
     */
    RedPacket getRedPacket(Integer id);

    /**
     * 扣减红包个数
     * @param id
     * @return
     */
    int decreaseRedPacket(Integer id);

    /**
     * 保存红包库存到数据库
     * @param id
     * @param stock
     * @return
     */
    int saveRedPacketStock(@Param("id") Integer id, @Param("stock") Integer stock);

    /**
     * 扣减红包个数-乐观锁
     * @param id
     * @param version
     * @return
     */
    int decreaseRedPacketByVersion(@Param("id") Integer id, @Param("version") Integer version);
}
