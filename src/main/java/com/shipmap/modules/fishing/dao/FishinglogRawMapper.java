package com.shipmap.modules.fishing.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.shipmap.modules.fishing.model.FishinglogRaw;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2018-12-26
 */
public interface FishinglogRawMapper extends BaseMapper<FishinglogRaw> {

    void updateAttrById(FishinglogRaw ent);
}
