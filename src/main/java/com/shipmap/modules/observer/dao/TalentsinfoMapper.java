package com.shipmap.modules.observer.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.shipmap.modules.observer.model.Talentsinfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2019-04-09
 */
public interface TalentsinfoMapper extends BaseMapper<Talentsinfo> {


    /*查询Talentsinfo*/
    List<Talentsinfo> selectTalentsinfo(Page<Talentsinfo> page, @Param("ew") Wrapper<Talentsinfo> wrapper);


}
