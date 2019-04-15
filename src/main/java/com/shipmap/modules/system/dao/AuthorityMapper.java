package com.shipmap.modules.system.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.shipmap.modules.system.model.Authority;

import java.util.List;

public interface AuthorityMapper extends BaseMapper<Authority> {
    /**
     * 根据用户id查询功能信息列表
     *
     * @param userId 用户id
     **/
    List<Authority> listByUserId(String userId);

    /**
     * 根据角色id查询功能信息列表
     *
     * @param roleId 角色id
     **/
    List<String> listByRoleId(String roleId);

    /**
     * 根据功能id查询功能信息列表
     *
     * @param authorityIds 角色id列表
     **/
    List<Authority> listByRoleIds(String[] authorityIds);

    /**
     * 根据父级查询功能信息列表
     *
     * @param parentId 菜单功能父级id
     **/
    List<Authority> listByParentId(String parentId);

    /**
     * 根据菜单功能对象
     *
     * @param authority 菜单功能对象
     **/
    List<Authority> findByAuthority(Authority authority);
}
