package com.shipmap.modules.observer.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.shipmap.framework.exception.BusinessException;
import com.shipmap.framework.model.ActiveUser;
import com.shipmap.modules.observer.model.Resume;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author ${author}
 * @since 2019-04-09
 */

public interface ResumeService extends IService<Resume> {
    /**
     * 分页查询
     *
     * @param resumePage 分页对象
     * @param column     表的列名
     * @param value      匹配的值
     */
    Page<Resume> list(Page<Resume> resumePage, String column, String value);

    /**
     * 根据id查询
     *
     * @param resumeId
     */
    Resume getById(String resumeId);

    /**
     * 添加
     *
     * @param resume     添加对象
     * @param activeUser 当前登录用户
     */
    boolean add(Resume resume, ActiveUser activeUser) throws BusinessException;

    /**
     * 更新
     *
     * @param resume     修改对象
     * @param activeUser 当前登录用户
     */
    boolean update(Resume resume, ActiveUser activeUser);

    /**
     * 根据id删除
     *
     * @param resumeId
     */
    boolean delete(String resumeId);


}
