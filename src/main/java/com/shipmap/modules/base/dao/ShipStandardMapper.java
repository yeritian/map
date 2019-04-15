package com.shipmap.modules.base.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.shipmap.modules.base.model.ShipStandard;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2018-12-21
 */
public interface ShipStandardMapper extends BaseMapper<ShipStandard> {

    Integer deleteByIds(@Param("shipId") String shipId, @Param("standardId") String standardId);
}
