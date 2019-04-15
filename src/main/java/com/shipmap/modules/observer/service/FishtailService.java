package com.shipmap.modules.observer.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.shipmap.framework.exception.BusinessException;
import com.shipmap.framework.model.ActiveUser;
import com.shipmap.modules.observer.model.Fishtail;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author ${author}
 * @since 2019-03-28
 */

public interface FishtailService extends IService<Fishtail> {
    /**
     * 分页查询
     *
     * @param fishtailPage 分页对象
     * @param column       表的列名
     * @param value        匹配的值
     */
    Page<Fishtail> list(Page<Fishtail> fishtailPage, String column, String value, String id);

    /**
     * 根据id查询
     *
     * @param fishtailId
     */
    Fishtail getById(String fishtailId);

    /**
     * 添加
     *
     * @param fishtail 添加对象
     */
    boolean add(Fishtail fishtail, ActiveUser activeUser) throws BusinessException;

    /**
     * 更新
     *
     * @param fishtail 修改对象
     */
    boolean update(Fishtail fishtail, ActiveUser activeUser);

    /**
     * 根据id删除
     *
     * @param fishtailId
     */
    boolean delete(String fishtailId);

    /**
     * 根据id删除 -- 批量
     *
     * @param fishtailid
     */
    boolean deletAllfishtailid(String fishtailid);

    /*根据id 查询*/
    Fishtail selectByidFishtail(String id);

    /*文件导入*/
    int InsertExcel(Fishtail fishtail);

}
