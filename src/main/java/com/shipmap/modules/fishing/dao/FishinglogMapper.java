package com.shipmap.modules.fishing.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.shipmap.modules.fishing.model.Catch;
import com.shipmap.modules.fishing.model.Discard;
import com.shipmap.modules.fishing.model.FishingLogTongJi;
import com.shipmap.modules.fishing.model.Fishinglog;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2018-12-25
 */
public interface FishinglogMapper extends BaseMapper<Fishinglog> {

    Fishinglog selectByShipIdAndTime(@Param("id") String id, @Param("logDate") Date logDate);

    int updateForDel(@Param("id") String id);

    List<Fishinglog> selectPages(Page<Fishinglog> page, @Param("ew") Wrapper<Fishinglog> wrapper);

    List<Fishinglog> selectList(@Param("ew") Wrapper<Fishinglog> wrapper);

    int updateForModifyLogId(@Param("oid") String oid, @Param("mid") String mid);

    void updateForIsSelect(@Param("oid") String oid, @Param("isSelect") Integer isSelect);

    Fishinglog selectOneById(@Param("id") String id);

    List<Catch> selectCatchByLogId(@Param("log_id") String log_id);

    List<Discard> selectDiscardByLogId(@Param("log_id") String log_id);

    List<FishingLogTongJi> selectTongJiPages(Page<FishingLogTongJi> page, @Param("ew") Wrapper<Fishinglog> wrapper);

}
