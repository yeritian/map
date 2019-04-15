package com.shipmap.modules.base.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.shipmap.modules.base.model.Monthly;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 月报表 Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2019-04-02
 */
public interface MonthlyMapper extends BaseMapper<Monthly> {

    List<Monthly> getMonthly(Page<Monthly> monthlyPage, @Param("ew") Wrapper<Monthly> wrapper);

    //获取id
    Integer getMonthlyId();

}
