package com.shipmap.modules.fishing.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.shipmap.modules.fishing.model.Discard;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2018-12-25
 */
public interface DiscardMapper extends BaseMapper<Discard> {

    List<Discard> selectListByLogId(String id);

    int deleteByLogId(String id);
}
