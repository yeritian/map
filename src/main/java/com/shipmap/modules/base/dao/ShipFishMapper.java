package com.shipmap.modules.base.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.shipmap.modules.base.model.ShipFish;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 船舶关联的鱼种 Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2018-12-21
 */
public interface ShipFishMapper extends BaseMapper<ShipFish> {

    Integer deleteByIds(@Param("shipId") String shipId, @Param("fishId") String fishId);
}
