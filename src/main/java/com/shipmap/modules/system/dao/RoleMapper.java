package com.shipmap.modules.system.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.shipmap.modules.system.model.Role;

import java.util.List;

public interface RoleMapper extends BaseMapper<Role> {
    /**
     * 根据角色id批量查询角色信息
     **/
    List<Role> listByRoleIds(String[] roleIds);
}
