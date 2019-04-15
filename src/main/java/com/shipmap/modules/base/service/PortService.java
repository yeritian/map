package com.shipmap.modules.base.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.shipmap.framework.exception.BusinessException;
import com.shipmap.framework.model.ActiveUser;
import com.shipmap.modules.base.model.Port;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author ${author}
 * @since 2019-03-27
 */

public interface PortService extends IService<Port> {
    /**
     * 分页查询
     *
     * @param portPage 分页对象
     * @param column   表的列名
     * @param value    匹配的值
     */
    Page<Port> list(Page<Port> portPage, String column, String value);

    /**
     * 根据id查询
     *
     * @param portId
     */
    Port getById(String portId);

    /**
     * 添加
     *
     * @param port 添加对象
     */
    boolean add(Port port, ActiveUser user) throws BusinessException;

    /**
     * 更新
     *
     * @param port       修改对象
     * @param activeUser 当前登录用户
     */
    boolean update(Port port, ActiveUser activeUser);

    /**
     * 根据id删除
     *
     * @param portId
     */
    boolean delete(String portId);


    /**
     * 根据id查看
     *
     * @param id
     * @return
     */
    List<Port> selectAll(String id);

    boolean insertEntity(Port port);

    boolean deleteByPortId(String id);

    /**
     * 批量删除
     *
     * @param id
     * @return
     */
    boolean batchDelete(String id);
}
