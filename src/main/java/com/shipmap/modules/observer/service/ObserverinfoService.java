package com.shipmap.modules.observer.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.shipmap.framework.exception.BusinessException;
import com.shipmap.framework.model.ActiveUser;
import com.shipmap.modules.observer.model.Observerinfo;
import com.shipmap.modules.observer.model.VVO;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author ${author}
 * @since 2019-03-28
 */

public interface ObserverinfoService extends IService<Observerinfo> {
    /**
     * 分页查询
     *
     * @param observerinfoPage 分页对象
     * @param column           表的列名
     * @param value            匹配的值
     */
    Page<Observerinfo> list(Page<Observerinfo> observerinfoPage, String column, String value);

    Page<VVO> selectbufen(Page<VVO> voopage, String column, String value);


    /**
     * 根据id查询
     *
     * @param observerinfoId
     */
    Observerinfo getById(String observerinfoId);

    /**
     * 添加
     *
     * @param observerinfo 添加对象
     * @param activeUser   当前登录用户
     */
    boolean add(Observerinfo observerinfo, ActiveUser activeUser) throws BusinessException;

    /**
     * 更新
     *
     * @param observerinfo 修改对象
     * @param activeUser   当前登录用户
     */
    boolean update(Observerinfo observerinfo, ActiveUser activeUser);

    /**
     * 根据id删除
     *
     * @param observerinfoId
     */
    boolean delete(String observerinfoId);

    /*根据id 查询*/
    Observerinfo selectByidObserve(String id);
}
