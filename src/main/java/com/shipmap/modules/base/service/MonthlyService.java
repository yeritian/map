package com.shipmap.modules.base.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.shipmap.framework.exception.BusinessException;
import com.shipmap.framework.model.ActiveUser;
import com.shipmap.modules.base.model.Monthly;
import com.shipmap.modules.base.model.MonthlyVO;
import com.shipmap.modules.base.model.Specification;

import java.util.ArrayList;

/**
 * <p>
 * 月报表 服务类
 * </p>
 *
 * @author ${author}
 * @since 2019-04-02
 */

public interface MonthlyService extends IService<Monthly> {
    /**
     * 分页查询
     *
     * @param monthlyPage 分页对象
     * @param column      表的列名
     * @param value       匹配的值
     */
    Page<Monthly> list(Page<Monthly> monthlyPage, String column, String value);

    /**
     * 根据id查询
     *
     * @param monthlyId
     */
    Monthly getById(String monthlyId);

    /**
     * 添加
     *
     * @param monthly    添加对象
     * @param activeUser 当前登录用户
     */
    boolean add(Monthly monthly, ActiveUser activeUser) throws BusinessException;

    /**
     * 更新
     *
     * @param monthly    修改对象
     * @param activeUser 当前登录用户
     */
    boolean update(Monthly monthly, ActiveUser activeUser);

    /**
     * 根据id删除
     *
     * @param monthlyId
     */
    boolean delete(String monthlyId);

    /**
     * 根据条件查询出所有数据
     *
     * @return
     */
    Page<Monthly> getMonthly(Page<Monthly> monthlyPage, String companyName, String jobType, String jobTheArea, String province, String jobTime, String shipName);

    /**
     * 添加月报
     *
     * @param monthly
     * @param arrayList
     * @param activeUser
     * @return
     */
    boolean addMonthly(Monthly monthly, ArrayList<Specification> arrayList, ActiveUser activeUser);

    /**
     * monthlyVO
     *
     * @param monthlyId
     * @return
     */
    MonthlyVO getMonthlyVO(String monthlyId);


    boolean updateMonthly(Monthly monthly, String fishData, ActiveUser activeUser);
}
