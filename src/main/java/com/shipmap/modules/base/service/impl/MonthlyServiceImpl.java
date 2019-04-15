package com.shipmap.modules.base.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shipmap.framework.model.ActiveUser;
import com.shipmap.framework.utils.NumberUtil;
import com.shipmap.framework.utils.StringUtil;
import com.shipmap.modules.base.dao.MonthlyMapper;
import com.shipmap.modules.base.dao.SpecificationMapper;
import com.shipmap.modules.base.model.Monthly;
import com.shipmap.modules.base.model.MonthlyVO;
import com.shipmap.modules.base.model.Specification;
import com.shipmap.modules.base.service.MonthlyService;
import com.shipmap.modules.base.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 月报表 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2019-04-02
 */
@Service
public class MonthlyServiceImpl extends ServiceImpl<MonthlyMapper, Monthly> implements MonthlyService {

    @Autowired
    private MonthlyMapper monthlyMapper;

    @Autowired
    private SpecificationMapper specificationMapper;

    @Autowired
    private SpecificationService specificationService;

    /**
     * 分页查询
     *
     * @param monthlyPage 分页对象
     * @param column      表的列名
     * @param value       匹配的值
     */
    @Override
    public Page<Monthly> list(Page<Monthly> monthlyPage, String column, String value) {
        Wrapper<Monthly> wrapper = new EntityWrapper<>();
        if (StringUtil.isNotBlank(column)) {
            wrapper.like(column, value);
        }
        monthlyPage.setRecords(baseMapper.selectPage(monthlyPage, wrapper));
        return monthlyPage;
    }

    /**
     * 添加
     *
     * @param monthly    添加对象
     * @param activeUser 当前登录用户
     */
    @Override
    public boolean add(Monthly monthly, ActiveUser activeUser) {
        return monthlyMapper.insert(monthly) > 0;
    }

    /**
     * 更新
     *
     * @param monthly    修改对象
     * @param activeUser 当前登录用户
     */
    @Override
    public boolean update(Monthly monthly, ActiveUser activeUser) {
        return monthlyMapper.updateById(monthly) > 0;
    }

    /**
     * 根据id查询
     *
     * @param monthlyId
     */
    @Override
    public Monthly getById(String monthlyId) {
        return monthlyMapper.selectById(monthlyId);
    }

    /**
     * 根据id删除
     *
     * @param monthlyId
     */
    @Override
    public boolean delete(String monthlyId) {
        return monthlyMapper.deleteById(monthlyId) > 0;
    }

    @Override
    public Page<Monthly> getMonthly(Page<Monthly> monthlyPage, String companyName, String jobType, String jobTheArea, String province, String jobTime, String shipName) {
        Wrapper<Monthly> wrapper = new EntityWrapper<>();
        wrapper.eq("del", 0);
        if (companyName != null) {
            wrapper.like("company_name", companyName);
        }
        if (jobType != null) {
            wrapper.like("job_type", jobType);
        }
        if (jobTheArea != null) {
            wrapper.like("job_the_area", jobTheArea);
        }
        if (province != null) {
            wrapper.like("province", province);
        }
        if (jobTime != null) {
            wrapper.like("job_time", jobTime);
        }
        if (shipName != null) {
            wrapper.like("ship_name", shipName);
        }
        List<Monthly> monthly = monthlyMapper.getMonthly(monthlyPage, wrapper);
        return monthlyPage.setRecords(monthly);
    }

    @Override
    public boolean addMonthly(Monthly monthly, ArrayList<Specification> arrayList, ActiveUser activeUser) {
        Integer monthlyId = monthlyMapper.getMonthlyId();
        monthly.setId(NumberUtil.transIntTo62(monthlyId));


        for (Specification specification : arrayList) {
            Integer specificationId = specificationMapper.getSpecificationId();
            specification.setId(NumberUtil.transIntTo62(specificationId));
            specification.setMonthlyId(monthly.getId());
            if (specificationService.add(specification, activeUser)) {

            } else {
                return false;
            }
        }
        return monthlyMapper.insert(monthly) > 0;
    }

    /**
     * 通过monthlyid获取MonthlyVO
     *
     * @param monthlyId
     * @return
     */
    @Override
    public MonthlyVO getMonthlyVO(String monthlyId) {

        MonthlyVO monthlyVO = new MonthlyVO();

        Monthly monthly = monthlyMapper.selectById(monthlyId);
        monthlyVO.setMonthly(monthly);

        EntityWrapper<Specification> wrapper = new EntityWrapper<>();
        wrapper.eq("monthly_id", monthlyId);
        List<Specification> specifications = specificationMapper.getSpecification(wrapper);

        monthlyVO.setSpecificationList(specifications);

        return monthlyVO;
    }

    @Override
    public boolean updateMonthly(Monthly monthly, String fishData, ActiveUser activeUser) {


        String replace = fishData.replace("\\", "");
        String substring = replace.substring(1, replace.length() - 1);

        JSONArray objects = JSON.parseArray(substring);

        for (Object mapType : objects) {
            Specification specification = new Specification();
            String weight = JSONObject.parseObject(mapType.toString()).getString("weight");
            String mantissa = JSONObject.parseObject(mapType.toString()).getString("mantissa");
            specification.setWeight(weight);
            specification.setMantissa(mantissa);
            specification.setId(JSONObject.parseObject(mapType.toString()).getString("id"));
            System.out.println(specification);
            if (specificationService.updateById(specification)) {

            } else {
                return false;
            }
        }
        return monthlyMapper.updateById(monthly) > 0;
    }

}

