package com.shipmap.modules.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.shipmap.framework.service.exception.RoleNotFoundException;
import com.shipmap.modules.system.model.Role;

import java.util.List;

public interface RoleService extends IService<Role> {
    /**
     * 根据角色id批量查询角色信息
     *
     * @param roleIds 角色信息列表
     **/
    List<Role> listByRoleIds(String[] roleIds);

    /**
     * 根据用户id删除角色功能信息
     *
     * @param roleId 角色信息
     **/
    boolean delRoleAndAuthorityByRoleId(String roleId) throws RoleNotFoundException;

    /**
     * 设置角色功能
     *
     * @param roleId       角色id
     * @param authorityIds 功能列表
     **/
    void setRoleAndAuthority(String roleId, String... authorityIds) throws RoleNotFoundException;
}
