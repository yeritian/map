package com.shipmap.modules.observer.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.shipmap.framework.exception.BusinessException;
import com.shipmap.framework.model.ActiveUser;
import com.shipmap.modules.observer.model.Observerhooktime;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author ${author}
 * @since 2019-03-28
 */

public interface ObserverhooktimeService extends IService<Observerhooktime> {
    /**
     * 分页查询
     *
     * @param observerhooktimePage 分页对象
     * @param column               表的列名
     * @param value                匹配的值
     */
    Page<Observerhooktime> list(Page<Observerhooktime> observerhooktimePage, String column, String value, String id);

    /**
     * 根据id查询
     *
     * @param observerhooktimeId
     */
    Observerhooktime getById(String observerhooktimeId);

    /**
     * 添加
     *
     * @param observerhooktime 添加对象
     */
    boolean add(Observerhooktime observerhooktime, ActiveUser activeUser) throws BusinessException;

    /**
     * 更新
     *
     * @param observerhooktime 修改对象
     */
    boolean update(Observerhooktime observerhooktime, ActiveUser activeUser);

    /**
     * 根据id删除
     *
     * @param observerhooktimeId
     */
    boolean delete(String observerhooktimeId);

    /**
     * 根据id删除 ---  批量
     *
     * @param observerhooktimeId
     */
    boolean deletAllobhooklogid(String observerhooktimeId);


    /*根据id 查询*/
    Observerhooktime selectByidhooktime(String id);

    /*文件导入*/
    int InsertExcel(Observerhooktime obtime);

}
