package com.shipmap.modules.base.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.shipmap.modules.base.model.PortDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2019-03-30
 */
public interface PortDetailMapper extends BaseMapper<PortDetail> {


    int insertEntity(PortDetail portDetail);

    List<PortDetail> selectAll(Page<PortDetail> portDetailPage, @Param("ew") Wrapper<PortDetail> wrapper, String id);


    int deleteByPortId(String id);
}
