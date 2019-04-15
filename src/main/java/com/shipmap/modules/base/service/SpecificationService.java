package com.shipmap.modules.base.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.shipmap.framework.exception.BusinessException;
import com.shipmap.framework.model.ActiveUser;
import com.shipmap.modules.base.model.Specification;

import java.util.List;

/**
 * <p>
 * 鱼的重量和尾数表 服务类
 * </p>
 *
 * @author ${author}
 * @since 2019-04-02
 */

public interface SpecificationService extends IService<Specification> {
    /**
     * 分页查询
     *
     * @param specificationPage 分页对象
     * @param column            表的列名
     * @param value             匹配的值
     */
    Page<Specification> list(Page<Specification> specificationPage, String column, String value);

    /**
     * 根据id查询
     *
     * @param specificationId
     */
    Specification getById(String specificationId);

    /**
     * 添加
     *
     * @param specification 添加对象
     * @param activeUser    当前登录用户
     */
    boolean add(Specification specification, ActiveUser activeUser) throws BusinessException;

    /**
     * 更新
     *
     * @param specification 修改对象
     * @param activeUser    当前登录用户
     */
    boolean update(Specification specification, ActiveUser activeUser);

    /**
     * 根据id删除
     *
     * @param specificationId
     */
    boolean delete(String specificationId);

    //通过月报id获取数据
    List<Specification> getSpecification(String monthlyid);

//        Page<Specification> getSpecification();
}
