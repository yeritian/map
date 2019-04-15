package com.shipmap.modules.base.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.shipmap.framework.exception.BusinessException;
import com.shipmap.framework.model.ActiveUser;
import com.shipmap.modules.base.model.PortDetail;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author ${author}
 * @since 2019-03-30
 */

public interface PortDetailService extends IService<PortDetail> {
    /**
     * 分页查询
     *
     * @param portDetailPage 分页对象
     * @param column         表的列名
     * @param value          匹配的值
     */
    Page<PortDetail> list(Page<PortDetail> portDetailPage, String column, String value, String id);

    /**
     * 根据id查询
     *
     * @param portDetailId
     */
    PortDetail getById(String portDetailId);

    /**
     * 添加
     *
     * @param portDetail 添加对象
     * @param activeUser 当前登录用户
     */
    boolean add(PortDetail portDetail, ActiveUser activeUser) throws BusinessException;

    /**
     * 更新
     *
     * @param portDetail 修改对象
     * @param activeUser 当前登录用户
     */
    boolean update(PortDetail portDetail, ActiveUser activeUser);

    /**
     * 根据id删除
     *
     * @param portDetailId
     */
    boolean delete(String portDetailId);

    int insertEntity(PortDetail portDetail);

    boolean deleteByPortId(String id);
}
