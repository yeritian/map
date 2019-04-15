package com.shipmap.modules.base.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shipmap.framework.model.ActiveUser;
import com.shipmap.framework.utils.StringUtil;
import com.shipmap.modules.base.dao.SpecificationMapper;
import com.shipmap.modules.base.model.Specification;
import com.shipmap.modules.base.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 鱼的重量和尾数表 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2019-04-02
 */
@Service
public class SpecificationServiceImpl extends ServiceImpl<SpecificationMapper, Specification> implements SpecificationService {

    @Autowired
    private SpecificationMapper specificationMapper;

    /**
     * 分页查询
     *
     * @param specificationPage 分页对象
     * @param column            表的列名
     * @param value             匹配的值
     */
    @Override
    public Page<Specification> list(Page<Specification> specificationPage, String column, String value) {
        Wrapper<Specification> wrapper = new EntityWrapper<>();
        if (StringUtil.isNotBlank(column)) {
            wrapper.like(column, value);
        }
        specificationPage.setRecords(baseMapper.selectPage(specificationPage, wrapper));
        return specificationPage;
    }

    /**
     * 添加
     *
     * @param specification 添加对象
     * @param activeUser    当前登录用户
     */
    @Override
    public boolean add(Specification specification, ActiveUser activeUser) {
        return specificationMapper.insert(specification) > 0;
    }

    /**
     * 更新
     *
     * @param specification 修改对象
     * @param activeUser    当前登录用户
     */
    @Override
    public boolean update(Specification specification, ActiveUser activeUser) {
        return specificationMapper.updateById(specification) > 0;
    }

    /**
     * 根据id查询
     *
     * @param specificationId
     */
    @Override
    public Specification getById(String specificationId) {
        return specificationMapper.selectById(specificationId);
    }

    /**
     * 根据id删除
     *
     * @param specificationId
     */
    @Override
    public boolean delete(String specificationId) {
        return specificationMapper.deleteById(specificationId) > 0;
    }

    /**
     * 通过月报id查询数据
     *
     * @param monthlyid
     * @return
     */
    @Override
    public List<Specification> getSpecification(String monthlyid) {

        Wrapper<Specification> wrapper = new EntityWrapper<>();
        wrapper.eq("monthly_id", monthlyid);

        return specificationMapper.getSpecification(wrapper);
    }

}

