package com.shipmap.modules.base.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shipmap.framework.utils.NumberUtil;
import com.shipmap.framework.utils.StringUtil;
import com.shipmap.modules.base.dao.CompanyMapper;
import com.shipmap.modules.base.model.Company;
import com.shipmap.modules.base.service.CompanyService;
import com.shipmap.modules.system.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2019-01-08
 */
@Service
public class CompanyServiceImpl extends ServiceImpl<CompanyMapper, Company> implements CompanyService {

    @Autowired
    private CompanyMapper companyMapper;

    @Override
    public List<Company> list(Page<Company> page, String column, String value) {
        Wrapper<Company> wrapper = new EntityWrapper<>();
        if (StringUtil.isNotBlank(column)) {
            wrapper.like(column, value);
        }
//        Page<Company> companyPage = new Page<>(pageNum, pageSize);
        List<Company> companyList = baseMapper.selectPage(page, wrapper.orderBy("create_time", true));
        return companyList;
    }

    @Override
    public List<Company> selectCompanyName() {
        return companyMapper.selectCompanyName();
    }

    @Override
    public boolean add(Company company, User u) {
        Integer id = companyMapper.getCompanyId();
        String c = NumberUtil.transIntTo62(id);
        company.setId(c);
        company.setCreateTime(new Date());
        company.setCreator(u.getId());
        company.setUpdateTime(new Date());
        company.setUpdator(u.getId());
        return companyMapper.insert(company) > 0;
    }

    @Override
    public boolean update(Company company, User u) {
        company.setUpdateTime(new Date());
        company.setUpdator(u.getId());
        return companyMapper.updateById(company) > 0;
    }

    @Override
    public Company getById(String companyId) {
        return companyMapper.selectById(companyId);
    }

    @Override
    public boolean delete(String companyId) {
        return companyMapper.deleteById(companyId) > 0;
    }

}

