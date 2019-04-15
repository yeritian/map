package com.shipmap.modules.observer.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.shipmap.modules.observer.model.Fishtype;
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
public interface FishtypeMapper extends BaseMapper<Fishtype> {


    /*查询全部信息*/
    List<Fishtype> selectAllFishType(String id);

    /*删除*/
    int deleteById(String id);

    List<Fishtype> getfishtype(@Param("ew") Wrapper<Fishtype> wrapper);

}
