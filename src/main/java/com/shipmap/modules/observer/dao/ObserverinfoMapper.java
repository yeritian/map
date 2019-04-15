package com.shipmap.modules.observer.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.shipmap.modules.observer.model.Observerinfo;
import com.shipmap.modules.observer.model.VVO;
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
public interface ObserverinfoMapper extends BaseMapper<Observerinfo> {

    int selectObserverInfoSeq();

    List<Observerinfo> selectObserverInfo(Page<Observerinfo> page, @Param("ew") Wrapper<Observerinfo> wrapper);

    /*删除*/
    int deleteObserverInfoByID(String id);

    /*根据id 查询*/
    Observerinfo selectByidObserve(String id);

    /*观察员数据查询*/
    List<Observerinfo> selectbufen(Page<VVO> page, @Param("ew") Wrapper<Observerinfo> wrapper);


}
