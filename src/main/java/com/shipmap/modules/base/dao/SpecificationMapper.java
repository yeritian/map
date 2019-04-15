package com.shipmap.modules.base.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.shipmap.modules.base.model.Specification;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 鱼的重量和尾数表 Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2019-04-02
 */
public interface SpecificationMapper extends BaseMapper<Specification> {

    //查询所有符合条件的Specification
    List<Specification> getSpecification(@Param("ew") Wrapper<Specification> wrapper);

    //获取id
    Integer getSpecificationId();

}
