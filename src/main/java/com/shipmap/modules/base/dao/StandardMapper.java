package com.shipmap.modules.base.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.shipmap.modules.base.model.NameVO;
import com.shipmap.modules.base.model.Standard;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2018-12-21
 */
public interface StandardMapper extends BaseMapper<Standard> {

    Integer selectIdSeq();

    List<NameVO> selectStandardByShip(String id);

    List<NameVO> slelectRemainStandard(String id);

    List<NameVO> selectAll();
}
