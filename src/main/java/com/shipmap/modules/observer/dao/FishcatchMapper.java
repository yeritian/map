package com.shipmap.modules.observer.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.shipmap.modules.observer.model.Fishcatch;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2019-03-28
 */
public interface FishcatchMapper extends BaseMapper<Fishcatch> {

    int selectFishcatchSeq();

    List<Fishcatch> selectFishcatch(Page<Fishcatch> page, @Param("ew") Wrapper<Fishcatch> wrapper, String id);

    /*删除*/
    int deleteByFishcatchId(String id);

    /*详情ById*/
    List<Fishcatch> selectFishCatchById(String id);


    /*统计*/
    List<Fishcatch> selectFishcatchCount(String id);


}
