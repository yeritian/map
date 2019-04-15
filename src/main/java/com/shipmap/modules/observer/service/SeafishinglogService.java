package com.shipmap.modules.observer.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.shipmap.framework.exception.BusinessException;
import com.shipmap.framework.model.ActiveUser;
import com.shipmap.modules.observer.model.Seafishinglog;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author ${author}
 * @since 2019-03-28
 */

public interface SeafishinglogService extends IService<Seafishinglog> {
    /**
     * 分页查询
     *
     * @param seafishinglogPage 分页对象
     * @param column            表的列名
     * @param value             匹配的值
     */
    Page<Seafishinglog> list(Page<Seafishinglog> seafishinglogPage, String column, String value, String id);

    /**
     * 根据id查询
     *
     * @param seafishinglogId
     */
    Seafishinglog getById(String seafishinglogId);

    /**
     * 添加
     *
     * @param seafishinglog 添加对象
     */
    boolean add(Seafishinglog seafishinglog, ActiveUser activeUser) throws BusinessException;

    /**
     * 更新
     *
     * @param seafishinglog 修改对象
     */
    boolean update(Seafishinglog seafishinglog, ActiveUser activeUser);

    /**
     * 根据id删除
     *
     * @param seafishinglogId
     */
    boolean delete(String seafishinglogId);

    /*批量删除
     * * @param String seafishinglogId
     * */
    boolean delectAllSeafishingid(String seafishinglogId);


    /**
     * 根据id查询
     * * @param id id
     *
     * @return
     */
    Seafishinglog selectByid(String id);

    /*文件导入*/
    int InsertLayer(Seafishinglog layer);

}
