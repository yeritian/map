package com.shipmap.modules.base.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.shipmap.modules.base.model.Ship;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2018-12-12
 */
public interface ShipMapper extends BaseMapper<Ship> {

    Integer getShipId();

    String getVer(@Param("id") String shipId);

    Integer setShipVer(@Param("shipId") String shipId, @Param("ver1") String ver);

    List<Ship> selectAll(Page<Ship> page, @Param("ew") Wrapper<Ship> wrapper);

}
