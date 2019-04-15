package com.shipmap.modules.observer.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.shipmap.modules.observer.model.Fishtail;
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
public interface FishtailMapper extends BaseMapper<Fishtail> {

    int selectFishtailSeq();

    /*分页查询Fishtail信息*/
    List<Fishtail> selectFishtaillist(Page<Fishtail> page, @Param("ew") Wrapper<Fishtail> wrapper, String id);

    /*删除*/
    int deleteFishtaillist(String id);

    /*根据id 查询*/
    Fishtail selectByidFishtail(String id);

    /*统计*/
    int seletcFishtailCount(String id);

}
