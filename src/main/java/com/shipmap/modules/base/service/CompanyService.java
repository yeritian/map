package com.shipmap.modules.base.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.shipmap.framework.exception.BusinessException;
import com.shipmap.modules.base.model.Company;
import com.shipmap.modules.system.model.User;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author ${author}
 * @since 2019-01-08
 */

public interface CompanyService extends IService<Company> {

    List<Company> list(Page<Company> page, String searchKey, String searchValue);

    List<Company> selectCompanyName();

    Company getById(String companyId);

    boolean add(Company company, User u) throws BusinessException;

    boolean update(Company company, User u);

    boolean delete(String companyId);
}
