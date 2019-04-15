package com.shipmap.modules.base.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.shipmap.modules.base.model.Port;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2019-03-27
 */
public interface PortMapper extends BaseMapper<Port> {

    /**
     * 根据id查看
     *
     * @param id
     * @return
     */
    List<Port> selectAll(String id);


    List<Port> selectAll1(Page<Port> portDetailPage, @Param("ew") Wrapper<Port> wrapper, String id);

    boolean insertEntity(Port port);

//    boolean deleteByPortId(String id);

    int deleteByPortId(String id);
}
