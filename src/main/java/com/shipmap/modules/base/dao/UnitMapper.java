package com.shipmap.modules.base.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.shipmap.modules.base.model.NameVO;
import com.shipmap.modules.base.model.Unit;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2018-12-21
 */
public interface UnitMapper extends BaseMapper<Unit> {

    Integer selectIdSeq();

    List<NameVO> selectUnitByShip(String id);

    List<NameVO> selectAll();

    List<NameVO> selectRemainUnit(String id);
}
