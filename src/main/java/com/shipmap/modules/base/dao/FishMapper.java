package com.shipmap.modules.base.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.shipmap.modules.base.model.Fish;
import com.shipmap.modules.base.model.NameVO;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2018-12-19
 */
public interface FishMapper extends BaseMapper<Fish> {

    Integer getFishId();

    List<NameVO> selectFishByShip(String id);

    List<NameVO> selectAll();

    List<NameVO> selectRemaining(String id);

}
