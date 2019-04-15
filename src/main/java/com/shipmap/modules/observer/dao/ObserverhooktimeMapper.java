package com.shipmap.modules.observer.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.shipmap.modules.observer.model.Observerhooktime;
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
public interface ObserverhooktimeMapper extends BaseMapper<Observerhooktime> {

    int selectObsHookTSeq();

    List<Observerhooktime> selectObserverHoooktime(Page<Observerhooktime> page, @Param("ew") Wrapper<Observerhooktime> wrapper, String id);

    /*删除*/
    int delectObserverHooktime(String id);

    /*根据id 查询*/
    Observerhooktime selectByidhooktime(String id);

    /*统计*/
    List<Observerhooktime> selectHooktimeCount(String id);

}
