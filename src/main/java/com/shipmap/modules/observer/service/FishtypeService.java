package com.shipmap.modules.observer.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.shipmap.framework.exception.BusinessException;
import com.shipmap.framework.model.ActiveUser;
import com.shipmap.modules.observer.model.Fishtype;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author ${author}
 * @since 2019-03-28
 */

public interface FishtypeService extends IService<Fishtype> {
    /**
     * 分页查询
     *
     * @param fishtypePage 分页对象
     * @param column       表的列名
     * @param value        匹配的值
     */
    Page<Fishtype> list(Page<Fishtype> fishtypePage, String column, String value);

    /**
     * 根据id查询
     *
     * @param fishtypeId
     */
    Fishtype getById(String fishtypeId);

    /**
     * 添加
     *
     * @param fishtype   添加对象
     * @param activeUser 当前登录用户
     */
    boolean add(Fishtype fishtype, ActiveUser activeUser) throws BusinessException;

    /**
     * 更新
     *
     * @param fishtype   修改对象
     * @param activeUser 当前登录用户
     */
    boolean update(Fishtype fishtype, ActiveUser activeUser);

    /**
     * 根据id删除
     *
     * @param fishtypeId
     */
    boolean delete(String fishtypeId);

    /*文件导出*/
    List<Fishtype> getfishtype(String id);

}
