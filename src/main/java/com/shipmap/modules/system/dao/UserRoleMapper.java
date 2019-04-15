package com.shipmap.modules.system.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.shipmap.modules.system.model.UserRole;

import java.util.List;

public interface UserRoleMapper extends BaseMapper<UserRole> {
    /**
     * 根据用户id删除用户角色信息
     **/
    Integer deleteByUserId(String userId);

    /**
     * 根据用户id查询用户角色信息
     **/
    List<UserRole> listByUserId(String userId);
}
