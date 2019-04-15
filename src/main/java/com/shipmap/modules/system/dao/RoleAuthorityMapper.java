package com.shipmap.modules.system.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.shipmap.modules.system.model.RoleAuthority;

import java.util.List;

public interface RoleAuthorityMapper extends BaseMapper<RoleAuthority> {
    /**
     * 根据角色id删除角色功能信息
     *
     * @param roleId 角色id
     **/
    Integer deleteByRoleId(String roleId);

    /**
     * 根据角色id删除角色功能信息
     *
     * @param authorityId 角色id
     **/
    Integer deleteByAuthorityId(String authorityId);

    /**
     * 根据角色id查询角色功能信息
     *
     * @param roleIds 角色id列表
     **/
    List<RoleAuthority> listByRoleIds(String[] roleIds);
}
