package com.shipmap.modules.base.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.shipmap.modules.base.model.Company;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2019-01-08
 */
public interface CompanyMapper extends BaseMapper<Company> {

    Integer getCompanyId();

    List<Company> selectCompanyName();
}
