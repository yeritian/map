package com.shipmap.modules.observer.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.shipmap.framework.exception.BusinessException;
import com.shipmap.framework.model.ActiveUser;
import com.shipmap.modules.observer.model.Resume;
import com.shipmap.modules.observer.model.Talentsinfo;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author ${author}
 * @since 2019-04-09
 */

public interface TalentsinfoService extends IService<Talentsinfo> {
    /**
     * 分页查询
     *
     * @param talentsinfoPage 分页对象
     * @param column          表的列名
     * @param value           匹配的值
     */
    Page<Talentsinfo> list(Page<Talentsinfo> talentsinfoPage, String column, String value);

    /**
     * 根据id查询
     *
     * @param talentsinfoId
     */
    Talentsinfo getById(String talentsinfoId);

    /**
     * 添加
     *
     * @param talentsinfo 添加对象
     * @param activeUser  当前登录用户
     */
    boolean add(Talentsinfo talentsinfo, ActiveUser activeUser) throws BusinessException;

    /**
     * 更新
     *
     * @param talentsinfo 修改对象
     * @param activeUser  当前登录用户
     */
    boolean update(Talentsinfo talentsinfo, ActiveUser activeUser);

    /**
     * 根据id删除
     *
     * @param talentsinfoId
     */
    boolean delete(String talentsinfoId);

    /*根据 talents  id 查询 resume(履历)*/
    List<Resume> selectTalenDetail(String talentsinfoId);

    /*修改*/
    boolean updateTalen(String id, String name, String sex, String seamanbookno, String idnumber, String healthbookno, String dateupdate, ActiveUser activeUser);

}
