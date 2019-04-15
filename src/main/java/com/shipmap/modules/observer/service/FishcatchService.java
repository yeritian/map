package com.shipmap.modules.observer.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.shipmap.framework.exception.BusinessException;
import com.shipmap.framework.model.ActiveUser;
import com.shipmap.modules.observer.model.Fishcatch;
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

public interface FishcatchService extends IService<Fishcatch> {
    /**
     * 分页查询
     *
     * @param fishcatchPage 分页对象
     * @param column        表的列名
     * @param value         匹配的值
     */
    Page<Fishcatch> list(Page<Fishcatch> fishcatchPage, String column, String value, String id);

    /**
     * 根据id查询
     *
     * @param fishcatchId
     */
    Fishcatch getById(String fishcatchId);

    /**
     * 添加
     *
     * @param fishcatch 添加对象
     */
    boolean add(Fishcatch fishcatch) throws BusinessException;

    /**
     * 更新
     */
    boolean update(String obfishcatchid, String obhks, String datehook, String obnum, String obtotsum, String remark, String addstr, ActiveUser activeUser);

    /**
     * 根据id删除
     *
     * @param fishcatchId
     */
    boolean delete(String fishcatchId);

    /**
     * 根据id删除 -- 批量
     *
     * @param obfishcatchid
     */
    boolean deletAllObfishcatchid(String obfishcatchid);

    /*详情ById
     * @param id
     * */
    List<Fishtype> selectFishCatchById(String id);

    /*添加fishcatch*/
    boolean addFishCatch(String observerinfoid, String obhks, String datehook, String obnum, String obtotsum, String remark, String addstr, ActiveUser activeUser);


    /*文件导入*/
    boolean InsertLayer(Fishcatch layer);


}
