package com.shipmap.modules.fishing.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.shipmap.modules.fishing.model.Catch;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2018-12-25
 */
public interface CatchMapper extends BaseMapper<Catch> {

    List<Catch> selectListByLogId(String id);

    void deleteByLogId(String id);
}
